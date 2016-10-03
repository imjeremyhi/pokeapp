package com.example.android.pokeapp;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;

public class MovesActivity extends AppCompatActivity {
    private RecyclerView myRecyclerView;
    private LinearLayoutManager myLinearLayoutManager;
    private MovesRecyclerAdapter myAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_moves);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#ff0000")));
        myRecyclerView = (RecyclerView)findViewById(R.id.myMovesRecyclerView);
        myLinearLayoutManager = new LinearLayoutManager(this);
        myRecyclerView.setLayoutManager(myLinearLayoutManager);
        myAdapter = new MovesRecyclerAdapter(getIntent().getStringArrayListExtra("moves"));
        myRecyclerView.setAdapter(myAdapter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return false;
    }

    @Override
    public void onBackPressed() {
        Context context = MovesActivity.this;
        Intent showPokemonIntent = new Intent(context, PokemonActivity.class);
        showPokemonIntent.putExtra("number", getIntent().getStringExtra("number"));
        context.startActivity(showPokemonIntent);
    }
}
