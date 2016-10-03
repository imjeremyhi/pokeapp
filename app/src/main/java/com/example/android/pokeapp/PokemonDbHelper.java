package com.example.android.pokeapp;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Jeremy Fu on 8/09/2016.
 */
public class PokemonDbHelper extends SQLiteOpenHelper {
    // If you change the database schema, you must increment the database version.
    //private static PokemonDbHelper instance;
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Pokemon.db";

    public PokemonDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
/*
    public static synchronized PokemonDbHelper getHelper(Context context) {
        if (instance == null) {
            instance = new PokemonDbHelper(context);
        }
        return instance;
    }
    */

    @Override
    public void onCreate(SQLiteDatabase db) {
        System.out.println("here first");
        //db.execSQL(PokemonDbContract.SQL_DELETE_ENTRIES);
        db.execSQL(PokemonDbContract.SQL_CREATE_ENTRIES);
    }
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        db.execSQL(PokemonDbContract.SQL_DELETE_ENTRIES);
        onCreate(db);
    }
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }
}