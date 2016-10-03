package com.example.android.pokeapp;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jeremy Fu on 7/09/2016.
 * Based from Developer Android Documentation
 */
public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {

    private List<Pokemon> pokemonList;

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView name;
        public TextView number;
        public ImageView image;

        public ViewHolder (View v) {
            super(v);
            name = (TextView) v.findViewById(R.id.name);
            number = (TextView) v.findViewById(R.id.number);
            image = (ImageView) v.findViewById(R.id.image);
            v.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Context context = itemView.getContext();
            Intent showPokemonIntent = new Intent(context, PokemonActivity.class);
            String numberKey = number.getText().toString();
            System.out.println(numberKey);
            showPokemonIntent.putExtra("number", numberKey);
            context.startActivity(showPokemonIntent);
        }
    }

    public RecyclerAdapter(List<Pokemon> pokemonList) {
        this.pokemonList = pokemonList;
    }

    @Override
    public RecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflatedView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recyclerview_row, parent, false);
        ViewHolder vh = new ViewHolder(inflatedView);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.name.setText(pokemonList.get(position).getName());
        byte[] byteArray = pokemonList.get(position).getImage();
        holder.number.setText(pokemonList.get(position).getNumber());
        Bitmap bmp = BitmapFactory.decodeByteArray(byteArray,0,byteArray.length);
        holder.image.setImageBitmap(bmp);
    }

    @Override
    public int getItemCount() {
        return pokemonList.size();
    }

    public void setFilter(List<Pokemon>filteredPokemon) {
        pokemonList = new ArrayList<>();
        pokemonList.addAll(filteredPokemon);
        notifyDataSetChanged();
    }
}
