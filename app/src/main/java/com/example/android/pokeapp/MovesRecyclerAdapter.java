package com.example.android.pokeapp;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Jeremy Fu on 27/09/2016.
 * Based from Android Developer Documentation
 */
public class MovesRecyclerAdapter extends RecyclerView.Adapter<MovesRecyclerAdapter.ViewHolder> {
    private List<String> movesList;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView move;

        public ViewHolder (View v) {
            super(v);
            move = (TextView) v.findViewById(R.id.move);
        }
    }
    public MovesRecyclerAdapter(List<String>movesList) {
        this.movesList = movesList;
    }

    @Override
    public MovesRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflatedView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.moves_row, parent, false);
        ViewHolder vh = new ViewHolder(inflatedView);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.move.setText(movesList.get(position));
    }

    @Override
    public int getItemCount() {
        return movesList.size();
    }
}
