package com.example.android.pokeapp;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ProgressBar;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Exchanger;

/**
 * Created by Jeremy Fu on 18/09/2016.
 */
// Initial splash screen to load all the Pokemon from PokeAPI
public class LoadActivity extends AppCompatActivity {

    private RequestQueue queue;
    private int indexLoop = 1;
    private Pokemon pokemonToAdd;
    private String locationUrl;
    private String imageUrl;
    private PokemonDbHelper myDbHelper;
    private PokemonDbAccess myDbAccess;
    private List<Pokemon> returnedList;
    private List<Pokemon> storeList;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loader_activity);
        getSupportActionBar().hide();
        progressBar = (ProgressBar)findViewById(R.id.loadProgressBar);
        myDbHelper = new PokemonDbHelper(this);
        myDbAccess = new PokemonDbAccess(myDbHelper);
        returnedList = new ArrayList<>();
        storeList = new ArrayList<>();

        //If incomplete database - reset database and re-retrieve data from pokeapi
        if (myDbAccess.getAll().size() < 151 && myDbAccess.getAll().size() > 0) {
            myDbAccess.deleteAll();
            String url = "http://pokeapi.co/api/v2/pokemon-species/?&limit=151";
            volleyRequest(url, new VolleyCallback() {
                @Override
                public void onSuccess(String result) {
                    storeNameAndNumber(result);
                    initialImageCall();
                }
            });
        } else if (myDbAccess.getAll().size() == 0) {
            //Database is empty so get pokemon from pokeapi
            String url = "http://pokeapi.co/api/v2/pokemon-species/?&limit=151";
            volleyRequest(url, new VolleyCallback() {
                @Override
                public void onSuccess(String result) {
                    storeNameAndNumber(result);
                    initialImageCall();
                }
            });
        } else {
            //Database has at least 151 entries and is complete so load the recycler view activity and kill this activity
            returnedList = myDbAccess.getAll();
            Intent showMainIntent = new Intent(this, MainActivity.class);
            showMainIntent.putExtra("returnedList", (Serializable)returnedList);
            this.startActivity(showMainIntent);
            this.finish();
        }
    }

    public void volleyRequest(String url, final VolleyCallback callback) {
        queue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        callback.onSuccess(response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println(error);
            }
        });
        queue.add(stringRequest);
    }

    public void storeNameAndNumber(String result) {
        try {
            JSONObject listObj = new JSONObject(result);
            JSONArray resultArray = listObj.getJSONArray("results");
            for (int i = 0; i < resultArray.length(); i++) {
                JSONObject resultObj = (JSONObject) resultArray.get(i);
                String name = resultObj.getString("name");
                String capitalName = name.substring(0, 1).toUpperCase() + name.substring(1);
                pokemonToAdd = new Pokemon();
                pokemonToAdd.setName(capitalName);

                int number = i + 1;
                String numberString = "";
                if (number < 10) {
                    numberString = "00" + number;
                } else if (number < 100) {
                    numberString = "0" + number;
                } else if (number >= 100) {
                    numberString += number;
                }
                pokemonToAdd.setNumber(numberString);
                storeList.add(pokemonToAdd);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void initialImageCall() {
        String imageUrl = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/" + indexLoop +".png";
        volleyRequestTwo(imageUrl, new VolleyImageCallback() {
            @Override
            public void onSuccess(Bitmap result) {
                storeImage(result);
            }
        });
    }

    //Request for the image sprites of each pokemon
    public void volleyRequestTwo(String imageUrl, final VolleyImageCallback callback) {
        queue = Volley.newRequestQueue(this);
        ImageRequest imageRequest = new ImageRequest(imageUrl,
                new Response.Listener<Bitmap>() {
                    @Override
                    public void onResponse(Bitmap bitmap) {
                        callback.onSuccess(bitmap);
                    }
                }, 0, 0, null,
                new Response.ErrorListener() {
                    public void onErrorResponse(VolleyError error) {
                    }
                });
        queue.add(imageRequest);
    }

    public void storeImage(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,100,stream);
        byte[] byteArray = stream.toByteArray();
        storeList.get(indexLoop-1).setImage(byteArray);
        queue.stop();
        progressBar.setProgress(indexLoop);
        indexLoop++;
        if (indexLoop < 152) {
            initialImageCall();
        } else if (indexLoop >= 152) {
            myDbAccess.insertPokemons(storeList);
            Intent showMainIntent = new Intent(LoadActivity.this, MainActivity.class);
            showMainIntent.putExtra("returnedList", (Serializable)storeList);
            LoadActivity.this.startActivity(showMainIntent);
            LoadActivity.this.finish();
        }
    }
}
