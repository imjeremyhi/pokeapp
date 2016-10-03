package com.example.android.pokeapp;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.Image;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by Jeremy Fu on 8/09/2016.
 */
public class PokemonActivity extends AppCompatActivity {

    private TextView number;
    private TextView name;
    private TextView typeOne;
    private TextView typeTwo;
    private ImageView image;
    private TextView abilities;
    private TextView height;
    private TextView weight;
    private TextView hpData;
    private TextView attackData;
    private TextView defenseData;
    private TextView spAttackData;
    private TextView spDefenseData;
    private TextView speedData;
    private TextView totalData;
    private TextView evolution;
    private TextView location;
    //private TextView moves;
    private ProgressBar hpBar;
    private ProgressBar attackBar;
    private ProgressBar defenseBar;
    private ProgressBar spAttackBar;
    private ProgressBar spDefenseBar;
    private ProgressBar speedBar;
    private String locationUrl;
    RequestQueue queue;
    Pokemon requestedPokemon;
    ProgressDialog progressDialog;
    PokemonDbHelper myDbHelper;
    PokemonDbAccess myDbAccess;
    Pokemon detailPokemon;
    ArrayList<String> movesList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pokedex_record);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#ff0000")));
        //JSON  request
        myDbHelper = new PokemonDbHelper(this);
        myDbAccess = new PokemonDbAccess(myDbHelper);

    }

    @Override
    protected void onStart() {
        super.onStart();
        requestedPokemon = new Pokemon();
        detailPokemon = myDbAccess.getPokemon(getIntent().getStringExtra("number"));
        if (detailPokemon.getType() == null) {
            volleyDetailRequest();
        } else if (detailPokemon.getType() != null) {
            fillFields();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                //NavUtils.navigateUpFromSameTask(this);
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        Context context = PokemonActivity.this;
        Intent showPokemonIntent = new Intent(context, MainActivity.class);
        context.startActivity(showPokemonIntent);
    }

    private void fillFields() {
        //int intentNumber = Integer.parseInt(getIntent().getStringExtra("number"));
        if (requestedPokemon.getType() != null) {
            requestedPokemon.setNumber(detailPokemon.getNumber());
            myDbAccess.updatePokemon(requestedPokemon);
            System.out.println("after update " + myDbAccess.getPokemon(getIntent().getStringExtra("number")).getType());
            detailPokemon.setType(requestedPokemon.getType());
            detailPokemon.setAbilities(requestedPokemon.getAbilities());
            detailPokemon.setHp(requestedPokemon.getHp());
            detailPokemon.setAttack(requestedPokemon.getAttack());
            detailPokemon.setDefense(requestedPokemon.getDefense());
            detailPokemon.setSpAttack(requestedPokemon.getSpAttack());
            detailPokemon.setSpDefense(requestedPokemon.getSpDefense());
            detailPokemon.setSpeed(requestedPokemon.getSpeed());
            detailPokemon.setEvolution(requestedPokemon.getEvolution());
            detailPokemon.setHeight(requestedPokemon.getHeight());
            detailPokemon.setWeight(requestedPokemon.getWeight());
            detailPokemon.setLocations(requestedPokemon.getLocations());
            detailPokemon.setMoves(requestedPokemon.getMoves());
        }

        number = (TextView) findViewById(R.id.number);
        number.setText(detailPokemon.getNumber());
        //number.setText("001");

        name = (TextView) findViewById(R.id.name);
        name.setText(detailPokemon.getName());
        //name.setText("Bulbasaur");

        typeOne = (TextView) findViewById(R.id.typeOne);
        typeTwo = (TextView) findViewById(R.id.typeTwo);
        String typeString = detailPokemon.getType();
        //int typeLength = typeString.length()-1-1;
        //typeString = typeString.substring(0,typeLength);

        int i = 0;
        String firstType = "";
        while (typeString.charAt(i) != ',') {
            firstType += typeString.charAt(i);
            i++;
        }
        String firstColour = typeColour(firstType);
        typeOne.setBackgroundColor(Color.parseColor(firstColour));
        typeOne.setText(firstType);

        i+=2;
        if (typeString.length() > i) {
            String secondType = "";
            while (typeString.charAt(i) != ',') {
                secondType += typeString.charAt(i);
                i++;
            }
            String secondColour = typeColour(secondType);
            typeTwo.setBackgroundColor(Color.parseColor(secondColour));
            typeTwo.setText(secondType);
        }

        abilities = (TextView) findViewById(R.id.abilities);
        String abilitiesString = detailPokemon.getAbilities();
        int abilitiesLength = abilitiesString.length()-1-1;
        abilitiesString = abilitiesString.substring(0,abilitiesLength);
        abilities.setText("Abilities:   " + abilitiesString);

        height = (TextView) findViewById(R.id.height);
        height.setText("Height:   " + detailPokemon.getHeight() + "m");

        weight = (TextView) findViewById(R.id.weight);
        weight.setText("Weight:   " + detailPokemon.getWeight() + "kg");

        image = (ImageView) findViewById(R.id.image);
        //Decode from byte array to bitmap:
        byte[] byteArray = detailPokemon.getImage();
        Bitmap bitmap = BitmapFactory.decodeByteArray(byteArray,0,byteArray.length);
        image.setImageBitmap(bitmap);

        hpData = (TextView) findViewById(R.id.hpData);
        int hpResult = Integer.parseInt(detailPokemon.getHp());
        hpData.setText(detailPokemon.getHp());

        hpBar = (ProgressBar)findViewById(R.id.hpProgressBar);
        hpBar.setProgress(hpResult);

        attackData = (TextView) findViewById(R.id.attackData);
        int attackResult = Integer.parseInt(detailPokemon.getAttack());
        attackData.setText(detailPokemon.getAttack());

        attackBar = (ProgressBar)findViewById(R.id.attackProgressBar);
        attackBar.setProgress(attackResult);

        defenseData = (TextView) findViewById(R.id.defenseData);
        int defenseResult = Integer.parseInt(detailPokemon.getDefense());
        defenseData.setText(detailPokemon.getDefense());

        defenseBar = (ProgressBar)findViewById(R.id.defenseProgressBar);
        defenseBar.setProgress(defenseResult);

        spAttackData = (TextView) findViewById(R.id.spAttackData);
        int spAttackResult = Integer.parseInt(detailPokemon.getSpAttack());
        spAttackData.setText(detailPokemon.getSpAttack());

        spAttackBar = (ProgressBar)findViewById(R.id.spAttackProgressBar);
        spAttackBar.setProgress(spAttackResult);

        spDefenseData = (TextView) findViewById(R.id.spDefenseData);
        int spDefenseResult = Integer.parseInt(detailPokemon.getSpDefense());
        spDefenseData.setText(detailPokemon.getSpDefense());

        spDefenseBar = (ProgressBar)findViewById(R.id.spDefenseProgressBar);
        spDefenseBar.setProgress(spDefenseResult);

        speedData = (TextView) findViewById(R.id.speedData);
        int speedResult = Integer.parseInt(detailPokemon.getSpeed());
        speedData.setText(detailPokemon.getSpeed());

        speedBar = (ProgressBar)findViewById(R.id.speedProgressBar);
        speedBar.setProgress(speedResult);

        totalData = (TextView) findViewById(R.id.totalData);
        int totalResult = hpResult + attackResult + defenseResult + spAttackResult + spDefenseResult + speedResult;
        totalData.setText(Integer.toString(totalResult));
/*
        evolution = (TextView) findViewById(R.id.evolution);
        evolution.setText("Evolutions:\n" + "Bulbasaur -> " + "Ivysaur -> " + "Venusaur");
*/
        location = (TextView) findViewById(R.id.location);
        String locationString = "";
        if (detailPokemon.getLocations() == null) {
            locationString = "No locations  ";
        } else {
            locationString = detailPokemon.getLocations();
        }
        int locationLength = locationString.length()-1-1;
        locationString = locationString.substring(0,locationLength);
        location.setText("Locations:\n" + locationString);

        //moves = (TextView) findViewById(R.id.moves);
        String movesString = detailPokemon.getMoves();
        int movesLength = movesString.length()-1-1;
        movesString = movesString.substring(0,movesLength);
        movesList = new ArrayList<>(Arrays.asList(movesString.split(",")));

        //moves.setText("Moves:\n" + movesString);

        evolution = (TextView) findViewById(R.id.evolution);
        String evolutionString = detailPokemon.getEvolution();
        int chainLength = evolutionString.length()-4;
        evolutionString = evolutionString.substring(0,chainLength);
        evolution.setText("Evolution:\n" + evolutionString);
    }
    /*
    public void typeColour(String type) {

        if(type.contains("Fire"))
        type.contains("Electric")
        type.contains
    }
    */

    public void volleyDetailRequest() {
        progressDialog = progressDialog.show(this, "Loading", "Wait while loading...");
        //progressDialog.setCancelable(true);
        //progressDialog.setCanceledOnTouchOutside(false);
        final String numberPassed = getIntent().getStringExtra("number");
        queue = Volley.newRequestQueue(this);
        String url = "http://pokeapi.co/api/v2/pokemon/" + numberPassed;
        basicRequest(url, new VolleyCallback() {
            @Override
            public void onSuccess(String result) {
                basicRequestProcessing(result);
                basicRequest(locationUrl, new VolleyCallback() {
                    @Override
                    public void onSuccess(String result) {
                        locationProcessing(result);
                        String speciesUrl = "http://pokeapi.co/api/v2/pokemon-species/" + numberPassed;
                        basicRequest(speciesUrl, new VolleyCallback() {
                            @Override
                            public void onSuccess(String result) {
                                try {
                                    JSONObject speciesObj = new JSONObject(result);
                                    JSONObject evolutionChainObj = speciesObj.getJSONObject("evolution_chain");
                                    String evolutionUrl = evolutionChainObj.getString("url");
                                    basicRequest(evolutionUrl, new VolleyCallback() {
                                        @Override
                                        public void onSuccess(String result) {
                                            evolutionProcessing(result);
                                            //Fill pokedex_record xml with values retrieved from jsonrequest in pokemon.java class
                                            fillFields();
                                            progressDialog.dismiss();
                                        }
                                    });
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                    }
                });
            }
        });

    }

    public void basicRequest(String url, final VolleyCallback callback) {
        RetryPolicy retryPolicy = new DefaultRetryPolicy(5000,1,1.0f);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        callback.onSuccess(response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(PokemonActivity.this, "Connection error please try again later", Toast.LENGTH_LONG).show();
                onBackPressed();
                System.out.println(error);
            }
        });
        stringRequest.setRetryPolicy(retryPolicy);
        queue.add(stringRequest);
    }

    public void basicRequestProcessing(String result) {
        try {
            JSONObject pokemonObject = new JSONObject(result);
            //Get types from JSON
            String listOfTypes = "";
            JSONArray types = pokemonObject.getJSONArray("types");
            for (int i = 0; i < types.length(); i++) {
                JSONObject typeObject = (JSONObject)types.get(i);
                JSONObject typeObjectInner = (JSONObject)typeObject.get("type");
                String type = typeObjectInner.getString("name");
                String capitalType = type.substring(0,1).toUpperCase() + type.substring(1);
                listOfTypes = listOfTypes + capitalType + ", ";
            }
            requestedPokemon.setType(listOfTypes);

            //Get abilities from JSON
            String abilities = "";
            JSONArray abilitiesArray = pokemonObject.getJSONArray("abilities");
            for (int i = 0; i < abilitiesArray.length(); i++) {
                JSONObject abilitiesObject = (JSONObject)abilitiesArray.get(i);
                JSONObject abilityObject = abilitiesObject.getJSONObject("ability");
                String ability = abilityObject.getString("name");
                String capitalAbility = ability.substring(0,1).toUpperCase() + ability.substring(1);
                abilities = abilities + capitalAbility + ", ";
            }
            requestedPokemon.setAbilities(abilities);

            //Get height from JSON
            double height = (double)Integer.parseInt(pokemonObject.getString("height"))/10;
            requestedPokemon.setHeight(Double.toString(height));

            //Get weight from JSON
            double weight = (double)Integer.parseInt(pokemonObject.getString("weight"))/10;
            requestedPokemon.setWeight(Double.toString(weight));

            //Get stats
            String hp = "";
            String attack = "";
            String defense = "";
            String spAttack = "";
            String spDefense = "";
            String speed = "";
            JSONArray stats = pokemonObject.getJSONArray("stats");
            for (int i = 0; i < stats.length(); i++) {
                JSONObject statObject = (JSONObject)stats.get(i);
                JSONObject statObjectInner = (JSONObject)statObject.get("stat");
                String statName = statObjectInner.getString("name");

                if (statName.equals("hp")) {
                    hp = statObject.getString("base_stat");
                } else if (statName.equals("attack")) {
                    attack = statObject.getString("base_stat");
                } else if (statName.equals("defense")) {
                    defense = statObject.getString("base_stat");
                } else if (statName.equals("special-attack")) {
                    spAttack = statObject.getString("base_stat");
                } else if (statName.equals("special-defense")) {
                    spDefense = statObject.getString("base_stat");
                } else if (statName.equals("speed")) {
                    speed = statObject.getString("base_stat");
                }
            }
            requestedPokemon.setHp(hp);
            requestedPokemon.setAttack(attack);
            requestedPokemon.setDefense(defense);
            requestedPokemon.setSpAttack(spAttack);
            requestedPokemon.setSpDefense(spDefense);
            requestedPokemon.setSpeed(speed);

            //Get moves
            String movesLearnable = "";
            JSONArray movesArray = pokemonObject.getJSONArray("moves");
            for (int i = 0; i < movesArray.length(); i++) {
                JSONObject moveObj = (JSONObject)movesArray.get(i);
                JSONArray versionDetailsArray = moveObj.getJSONArray("version_group_details");
                int found = 0;
                for (int j = 0; j < versionDetailsArray.length() && found != 1; j++) {
                    JSONObject versionDetailsObj = (JSONObject)versionDetailsArray.get(j);
                    JSONObject versionGroupObj = versionDetailsObj.getJSONObject("version_group");
                    String version = versionGroupObj.getString("name");
                    //System.out.println(version);
                    //if (version.equals("omega-ruby-alpha-sapphire")) {
                    found = 1;
                    String levelLearned = versionDetailsObj.getString("level_learned_at");
                    //System.out.println("Level learned " + levelLearned);
                                        /*if (levelLearned.equals("0")) {
                                            //handle this on query end instead
                                        }*/
                    JSONObject moveLearnedMethodObject = versionDetailsObj.getJSONObject("move_learn_method");
                    String levelMethod = moveLearnedMethodObject.getString("name");
                    //System.out.println("Level Method " + levelMethod);
                    //}
                }
                if (found == 1) {
                    JSONObject moveObjectInner = moveObj.getJSONObject("move");
                    String move = moveObjectInner.getString("name");
                    String capitalMove = move.substring(0,1).toUpperCase() + move.substring(1);
                    movesLearnable = movesLearnable + capitalMove + ", ";
                    requestedPokemon.setMoves(movesLearnable);
                    //System.out.println(move + " ");
                }
            }

            //Get location url
            locationUrl = "http://pokeapi.co" + pokemonObject.getString("location_area_encounters");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void locationProcessing(String result) {
        try {
            JSONArray locationArray = new JSONArray(result);
            int minOneLocation = 0;
            String locations = "";
            if (locationArray.length() != 0) {
                for (int i = 0; i < locationArray.length(); i++) {
                    JSONObject locationObject = (JSONObject) locationArray.get(i);
                    JSONArray versionDetailsArray = locationObject.getJSONArray("version_details");
                    int found = 0;
                    for (int j = 0; j < versionDetailsArray.length() && found != 1; j++) {
                        JSONObject versionObject = (JSONObject) versionDetailsArray.get(j);
                        JSONObject versionObjectInner = versionObject.getJSONObject("version");
                        String version = versionObjectInner.getString("name");
                        if (version.equals("firered") || version.equals("leafgreen") ||
                                version.equals("heartgold") || version.equals("soulsilver") ||
                                version.equals("crystal")) {
                            found = 1;
                            minOneLocation = 1;
                        }
                    }
                    if (found == 1) {
                        JSONObject locationAreaObject = locationObject.getJSONObject("location_area");
                        String locationArea = locationAreaObject.getString("name");
                        String capitalLocation = locationArea.substring(0,1).toUpperCase() + locationArea.substring(1);
                        locations = locations + capitalLocation + ", ";
                    }
                }
            }
            if (locationArray.length() == 0 || minOneLocation == 0) {
                locations += "No locations  ";
            }
            requestedPokemon.setLocations(locations);
        } catch (Exception e){
            System.out.println("Error is: " + e.getMessage());
        }
    }

    public void evolutionProcessing(String result) {
        try {
            //Evolution chain only from pokemon-species api
            String evolutionChainStore = "";
            JSONObject evolutionObject = new JSONObject(result);
            JSONObject chainObject = evolutionObject.getJSONObject("chain");
            JSONObject firstEvolution = chainObject.getJSONObject("species");
            String firstEvolutionName = firstEvolution.getString("name");
            String capitalFirst = firstEvolutionName.substring(0,1).toUpperCase() + firstEvolutionName.substring(1);
            evolutionChainStore = evolutionChainStore + capitalFirst + " -> ";
            JSONArray chainArray = chainObject.getJSONArray("evolves_to");
            if (chainArray.length() != 0) {
                JSONObject evolveObject = (JSONObject) chainArray.get(0);
                JSONObject secondEvolution = evolveObject.getJSONObject("species");
                String secondEvolutionName = secondEvolution.getString("name");
                String capitalSecond = secondEvolutionName.substring(0,1).toUpperCase() + secondEvolutionName.substring(1);
                evolutionChainStore = evolutionChainStore + capitalSecond + " -> ";
                JSONArray evolvesToArray = evolveObject.getJSONArray("evolves_to");
                if (evolvesToArray.length() != 0) {
                    JSONObject evolvesToObject = (JSONObject) evolvesToArray.get(0);
                    JSONObject thirdEvolution = evolvesToObject.getJSONObject("species");
                    String thirdEvolutionName = thirdEvolution.getString("name");
                    String capitalThird = thirdEvolutionName.substring(0,1).toUpperCase() + thirdEvolutionName.substring(1);
                    evolutionChainStore = evolutionChainStore + capitalThird + " -> ";
                }
            }
            requestedPokemon.setEvolution(evolutionChainStore);
        } catch (Exception e){
            System.out.println("Error is: " + e.getMessage());
        }
    }

    public String typeColour(String type) {
        HashMap typeColourMap = new HashMap();
        typeColourMap.put("Normal", "#A8A77A");
        typeColourMap.put("Fire", "#EE8130");
        typeColourMap.put("Water", "#6390F0");
        typeColourMap.put("Electric", "#F7D02C");
        typeColourMap.put("Grass", "#7AC74C");
        typeColourMap.put("Ice", "#96D9D6");
        typeColourMap.put("Fighting", "#C22E28");
        typeColourMap.put("Poison", "#A33EA1");
        typeColourMap.put("Ground", "#E2BF65");
        typeColourMap.put("Flying", "#A98FF3");
        typeColourMap.put("Psychic", "#F95587");
        typeColourMap.put("Bug", "#A6B91A");
        typeColourMap.put("Rock", "#B6A136");
        typeColourMap.put("Ghost", "#735797");
        typeColourMap.put("Dragon", "#6F35FC");
        typeColourMap.put("Dark", "#705746");
        typeColourMap.put("Steel", "#B7B7CE");
        typeColourMap.put("Fairy", "#D685AD");

        String colour = (String) typeColourMap.get(type);
        /*
        Set set = typeColourMap.entrySet();
        Iterator i = set.iterator();
        while(i.hasNext()) {
            Map.Entry me = (Map.Entry)i.next();
            System.out.print(me.getKey() + ": ");
            System.out.println(me.getValue());
        }
        */
        return colour;
    }

    public void movesDetail(View v) {
        Context context = PokemonActivity.this;
        Intent showPokemonIntent = new Intent(context, MovesActivity.class);
        String numberKey = detailPokemon.getNumber();
        showPokemonIntent.putStringArrayListExtra("moves", movesList);
        showPokemonIntent.putExtra("number", numberKey);
        context.startActivity(showPokemonIntent);
    }
}