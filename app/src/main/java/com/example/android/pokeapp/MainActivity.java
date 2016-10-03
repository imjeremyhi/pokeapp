package com.example.android.pokeapp;

import android.app.SearchManager;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Parcelable;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;

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
import org.json.JSONStringer;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {
    private List<Pokemon> returnedList;
    private RecyclerView myRecyclerView;
    private GridLayoutManager myGridLayoutManager;
    private RecyclerAdapter myAdapter;
    //Parcelable listState;

    //int allResultsReceived;
    //private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);
        //ActionBar actionBar = getSupportActionBar();

        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#ff0000")));
        myRecyclerView = (RecyclerView)findViewById(R.id.myRecyclerView);
        myGridLayoutManager = new GridLayoutManager(this,3);
        myRecyclerView.setLayoutManager(myGridLayoutManager);
        PokemonDbHelper myDbHelper = new PokemonDbHelper(this);
        PokemonDbAccess myDbAccess = new PokemonDbAccess(myDbHelper);
        //if (savedInstanceState != null) {
          //  listState = savedInstanceState.getParcelable("positionList");
        //}

        System.out.println("called going back");
        List<Pokemon> passedList = (List<Pokemon>)getIntent().getSerializableExtra("returnedList");
        if (passedList != null) {
            System.out.println("passed list is ");
            System.out.println(passedList);
            returnedList = passedList;
            //onRestoreInstanceState(savedInstanceState);
            loadAdapter();
        } else {
            System.out.println("passed list is ");
            System.out.println(passedList);
            returnedList = myDbAccess.getAll();
            loadAdapter();
        }
    }
/*
    @Override
    protected void onSaveInstanceState(Bundle state) {
        super.onSaveInstanceState(state);
        state.putParcelable("positionList", myGridLayoutManager.onSaveInstanceState());
    }

    @Override
    protected void onRestoreInstanceState(Bundle state) {
        super.onRestoreInstanceState(state);
        System.out.println("in restore");
        listState = state.getParcelable("positionList");
    }

    @Override
    protected void onResume() {
        super.onResume();
        System.out.println("in on resume");
        if (listState != null) {
            System.out.println("in not null");
            myGridLayoutManager.onRestoreInstanceState(listState);
        }
    }
*/
    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main,menu);
        final MenuItem item = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(item);
        searchView.setOnQueryTextListener(this);

        MenuItemCompat.setOnActionExpandListener(item,
                new MenuItemCompat.OnActionExpandListener() {
                    @Override
                    public boolean onMenuItemActionCollapse(MenuItem item) {
                        myAdapter.setFilter(returnedList);
                        return true;
                    }

                    @Override
                    public boolean onMenuItemActionExpand(MenuItem item) {
                        return true;
                    }
                });
        return true;
    }

    @Override
    public boolean onQueryTextChange(String searchText) {
        final List<Pokemon>filteredList = filter(returnedList, searchText);
        myAdapter.setFilter(filteredList);
        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    private List<Pokemon> filter(List<Pokemon>originalList, String searchText) {
        final List<Pokemon> filteredList = new ArrayList<>();
        for (Pokemon pokemon:originalList) {
            final String text = pokemon.getName().toLowerCase();
            if (text.contains(searchText)) {
                filteredList.add(pokemon);
            }
        }
        return filteredList;
    }

    public void loadAdapter(){
        myAdapter = new RecyclerAdapter(returnedList/*pokemonList*/);
        myRecyclerView.setAdapter(myAdapter);
    }

}
