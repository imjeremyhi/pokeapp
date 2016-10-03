package com.example.android.pokeapp;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jeremy Fu on 8/09/2016.
 * Based from Android Developer Documentation
 */
public final class PokemonDbContract {

    public static final String TABLE_NAME = "pokemon";
    private final SQLiteOpenHelper dbHelper;
    private static final String TEXT_TYPE = " TEXT";
    //private static final String REAL_TYPE = " REAL"
    private static final String BLOB_TYPE = " BLOB";
    private static final String COMMA_SEP = ",";

    public static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    PokemonEntry._ID + " INTEGER PRIMARY KEY," +
                    PokemonEntry.COLUMN_NAME_ONE + TEXT_TYPE + COMMA_SEP +
                    PokemonEntry.COLUMN_NAME_TWO + TEXT_TYPE + COMMA_SEP +
                    PokemonEntry.COLUMN_NAME_THREE + TEXT_TYPE + COMMA_SEP +
                    PokemonEntry.COLUMN_NAME_FOUR + TEXT_TYPE + COMMA_SEP +
                    PokemonEntry.COLUMN_NAME_FIVE + TEXT_TYPE + COMMA_SEP +
                    PokemonEntry.COLUMN_NAME_SIX + TEXT_TYPE + COMMA_SEP +
                    PokemonEntry.COLUMN_NAME_SEVEN + BLOB_TYPE + COMMA_SEP +
                    PokemonEntry.COLUMN_NAME_EIGHT + TEXT_TYPE + COMMA_SEP +
                    PokemonEntry.COLUMN_NAME_NINE + TEXT_TYPE + COMMA_SEP +
                    PokemonEntry.COLUMN_NAME_TEN + TEXT_TYPE + COMMA_SEP +
                    PokemonEntry.COLUMN_NAME_ELEVEN + TEXT_TYPE + COMMA_SEP +
                    PokemonEntry.COLUMN_NAME_TWELVE + TEXT_TYPE + COMMA_SEP +
                    PokemonEntry.COLUMN_NAME_THIRTEEN + TEXT_TYPE + COMMA_SEP +
                    PokemonEntry.COLUMN_NAME_FOURTEEN + TEXT_TYPE + COMMA_SEP +
                    PokemonEntry.COLUMN_NAME_FIFTEEN + TEXT_TYPE + COMMA_SEP +
                    PokemonEntry.COLUMN_NAME_SIXTEEN + TEXT_TYPE + ")";


    public static final String SQL_DELETE_ENTRIES =
        "DROP TABLE IF EXISTS " + TABLE_NAME;

    /* Inner class that defines the table contents */
    public static class PokemonEntry implements BaseColumns {
        public static final String COLUMN_NAME_ONE = "number";
        public static final String COLUMN_NAME_TWO = "name";
        public static final String COLUMN_NAME_THREE = "type";
        public static final String COLUMN_NAME_FOUR = "abilities";
        public static final String COLUMN_NAME_FIVE = "height";
        public static final String COLUMN_NAME_SIX = "weight";
        public static final String COLUMN_NAME_SEVEN = "image";
        public static final String COLUMN_NAME_EIGHT = "hp";
        public static final String COLUMN_NAME_NINE = "attack";
        public static final String COLUMN_NAME_TEN = "defense";
        public static final String COLUMN_NAME_ELEVEN = "spAttack";
        public static final String COLUMN_NAME_TWELVE = "spDefense";
        public static final String COLUMN_NAME_THIRTEEN = "speed";
        public static final String COLUMN_NAME_FOURTEEN = "locations";
        public static final String COLUMN_NAME_FIFTEEN = "moves";
        public static final String COLUMN_NAME_SIXTEEN = "evolution";
    }

    public PokemonDbContract(SQLiteOpenHelper dbHelper) {
        this.dbHelper = dbHelper;
    }

    public long insert(Pokemon pokemon){
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(PokemonEntry.COLUMN_NAME_ONE, pokemon.getNumber());
        values.put(PokemonEntry.COLUMN_NAME_TWO, pokemon.getName());
        values.put(PokemonEntry.COLUMN_NAME_THREE, pokemon.getType());
        values.put(PokemonEntry.COLUMN_NAME_FOUR, pokemon.getAbilities());
        values.put(PokemonEntry.COLUMN_NAME_FIVE, pokemon.getHeight());
        values.put(PokemonEntry.COLUMN_NAME_SIX, pokemon.getWeight());
        values.put(PokemonEntry.COLUMN_NAME_SEVEN, pokemon.getImage());
        values.put(PokemonEntry.COLUMN_NAME_EIGHT, pokemon.getHp());
        values.put(PokemonEntry.COLUMN_NAME_NINE, pokemon.getAttack());
        values.put(PokemonEntry.COLUMN_NAME_TEN, pokemon.getDefense());
        values.put(PokemonEntry.COLUMN_NAME_ELEVEN, pokemon.getSpAttack());
        values.put(PokemonEntry.COLUMN_NAME_TWELVE, pokemon.getSpDefense());
        values.put(PokemonEntry.COLUMN_NAME_THIRTEEN, pokemon.getSpeed());
        values.put(PokemonEntry.COLUMN_NAME_FOURTEEN, pokemon.getLocations());
        values.put(PokemonEntry.COLUMN_NAME_FIFTEEN, pokemon.getMoves());
        values.put(PokemonEntry.COLUMN_NAME_SIXTEEN, pokemon.getEvolution());

        long newRowId;
        newRowId = db.insert(TABLE_NAME, null, values);
        db.close();

        return newRowId;
    }

    public List<Pokemon> getPokemons(){
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        //Columns to query
        String[] columns = {
                PokemonEntry._ID,
                PokemonEntry.COLUMN_NAME_ONE,
                PokemonEntry.COLUMN_NAME_TWO,
                PokemonEntry.COLUMN_NAME_THREE,
                PokemonEntry.COLUMN_NAME_FOUR,
                PokemonEntry.COLUMN_NAME_FIVE,
                PokemonEntry.COLUMN_NAME_SIX,
                PokemonEntry.COLUMN_NAME_SEVEN,
                PokemonEntry.COLUMN_NAME_EIGHT,
                PokemonEntry.COLUMN_NAME_NINE,
                PokemonEntry.COLUMN_NAME_TEN,
                PokemonEntry.COLUMN_NAME_ELEVEN,
                PokemonEntry.COLUMN_NAME_TWELVE,
                PokemonEntry.COLUMN_NAME_THIRTEEN,
                PokemonEntry.COLUMN_NAME_FOURTEEN,
                PokemonEntry.COLUMN_NAME_FIFTEEN,
                PokemonEntry.COLUMN_NAME_SIXTEEN

        };

        //Sort order
        String sortOrder = PokemonEntry.COLUMN_NAME_ONE;

        Cursor cur = db.query(
                TABLE_NAME,  // The table to query
                columns,                               // The columns to return
                null,                                // The columns for the WHERE clause
                null,                            // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                sortOrder                                 // The sort order
        );

        List<Pokemon> pokemons = new ArrayList<>();

        while (cur.moveToNext()){
            Pokemon pokemon = new Pokemon();
            pokemon.setNumber(cur.getString(cur.getColumnIndexOrThrow(PokemonEntry.COLUMN_NAME_ONE)));
            pokemon.setName(cur.getString(cur.getColumnIndexOrThrow(PokemonEntry.COLUMN_NAME_TWO)));
            pokemon.setType(cur.getString(cur.getColumnIndexOrThrow(PokemonEntry.COLUMN_NAME_THREE)));
            pokemon.setAbilities(cur.getString(cur.getColumnIndexOrThrow(PokemonEntry.COLUMN_NAME_FOUR)));
            pokemon.setHeight(cur.getString(cur.getColumnIndexOrThrow(PokemonEntry.COLUMN_NAME_FIVE)));
            //ball.setIcon(BitmapFactory.decodeByteArray(img, 0, img.length));
            pokemon.setWeight(cur.getString(cur.getColumnIndexOrThrow(PokemonEntry.COLUMN_NAME_SIX)));
            pokemon.setImage(cur.getBlob(cur.getColumnIndexOrThrow(PokemonEntry.COLUMN_NAME_SEVEN)));
            pokemon.setHp(cur.getString(cur.getColumnIndexOrThrow(PokemonEntry.COLUMN_NAME_EIGHT)));
            pokemon.setAttack(cur.getString(cur.getColumnIndexOrThrow(PokemonEntry.COLUMN_NAME_NINE)));
            pokemon.setDefense(cur.getString(cur.getColumnIndexOrThrow(PokemonEntry.COLUMN_NAME_TEN)));
            pokemon.setSpAttack(cur.getString(cur.getColumnIndexOrThrow(PokemonEntry.COLUMN_NAME_ELEVEN)));
            pokemon.setSpDefense(cur.getString(cur.getColumnIndexOrThrow(PokemonEntry.COLUMN_NAME_TWELVE)));
            pokemon.setSpeed(cur.getString(cur.getColumnIndexOrThrow(PokemonEntry.COLUMN_NAME_THIRTEEN)));
            pokemon.setLocations(cur.getString(cur.getColumnIndexOrThrow(PokemonEntry.COLUMN_NAME_FOURTEEN)));
            pokemon.setMoves(cur.getString(cur.getColumnIndexOrThrow(PokemonEntry.COLUMN_NAME_FIFTEEN)));
            pokemon.setEvolution(cur.getString(cur.getColumnIndexOrThrow(PokemonEntry.COLUMN_NAME_SIXTEEN)));

            pokemons.add(pokemon);
        }

        cur.close();
        db.close();
        return pokemons;
    }

    public void delete(String number){
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        //WHERE ID = ?
        String selection = PokemonEntry.COLUMN_NAME_ONE + " = ?";

        //All the different IDs its equal to
        String[] selectionArgs = {number};

        db.delete(TABLE_NAME, selection, selectionArgs);

        db.close();
    }

    public Pokemon getPokemon(String number){
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String selection = PokemonEntry.COLUMN_NAME_ONE + " = ?";

        String[] selectionArgs = {number};

        //Columns to query
        String[] columns = {
                PokemonEntry._ID,
                PokemonEntry.COLUMN_NAME_ONE,
                PokemonEntry.COLUMN_NAME_TWO,
                PokemonEntry.COLUMN_NAME_THREE,
                PokemonEntry.COLUMN_NAME_FOUR,
                PokemonEntry.COLUMN_NAME_FIVE,
                PokemonEntry.COLUMN_NAME_SIX,
                PokemonEntry.COLUMN_NAME_SEVEN,
                PokemonEntry.COLUMN_NAME_EIGHT,
                PokemonEntry.COLUMN_NAME_NINE,
                PokemonEntry.COLUMN_NAME_TEN,
                PokemonEntry.COLUMN_NAME_ELEVEN,
                PokemonEntry.COLUMN_NAME_TWELVE,
                PokemonEntry.COLUMN_NAME_THIRTEEN,
                PokemonEntry.COLUMN_NAME_FOURTEEN,
                PokemonEntry.COLUMN_NAME_FIFTEEN,
                PokemonEntry.COLUMN_NAME_SIXTEEN
        };

        Cursor cur = db.query(
                TABLE_NAME,  // The table to query
                columns,                               // The columns to return
                selection,                                // The columns for the WHERE clause
                selectionArgs,                            // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                null                                 // The sort order
        );
        Pokemon pokemon = null;
        if(cur.moveToNext()){
            pokemon = new Pokemon();
            pokemon.setNumber(cur.getString(cur.getColumnIndexOrThrow(PokemonEntry.COLUMN_NAME_ONE)));
            pokemon.setName(cur.getString(cur.getColumnIndexOrThrow(PokemonEntry.COLUMN_NAME_TWO)));
            pokemon.setType(cur.getString(cur.getColumnIndexOrThrow(PokemonEntry.COLUMN_NAME_THREE)));
            pokemon.setAbilities(cur.getString(cur.getColumnIndexOrThrow(PokemonEntry.COLUMN_NAME_FOUR)));
            pokemon.setHeight(cur.getString(cur.getColumnIndexOrThrow(PokemonEntry.COLUMN_NAME_FIVE)));
            pokemon.setWeight(cur.getString(cur.getColumnIndexOrThrow(PokemonEntry.COLUMN_NAME_SIX)));
            pokemon.setImage(cur.getBlob(cur.getColumnIndexOrThrow(PokemonEntry.COLUMN_NAME_SEVEN)));
            pokemon.setHp(cur.getString(cur.getColumnIndexOrThrow(PokemonEntry.COLUMN_NAME_EIGHT)));
            pokemon.setAttack(cur.getString(cur.getColumnIndexOrThrow(PokemonEntry.COLUMN_NAME_NINE)));
            pokemon.setDefense(cur.getString(cur.getColumnIndexOrThrow(PokemonEntry.COLUMN_NAME_TEN)));
            pokemon.setSpAttack(cur.getString(cur.getColumnIndexOrThrow(PokemonEntry.COLUMN_NAME_ELEVEN)));
            pokemon.setSpDefense(cur.getString(cur.getColumnIndexOrThrow(PokemonEntry.COLUMN_NAME_TWELVE)));
            pokemon.setSpeed(cur.getString(cur.getColumnIndexOrThrow(PokemonEntry.COLUMN_NAME_THIRTEEN)));
            pokemon.setLocations(cur.getString(cur.getColumnIndexOrThrow(PokemonEntry.COLUMN_NAME_FOURTEEN)));
            pokemon.setMoves(cur.getString(cur.getColumnIndexOrThrow(PokemonEntry.COLUMN_NAME_FIFTEEN)));
            pokemon.setEvolution(cur.getString(cur.getColumnIndexOrThrow(PokemonEntry.COLUMN_NAME_SIXTEEN)));
        }
        cur.close();
        db.close();
        return pokemon;
    }

    public void updatePokemon(Pokemon pokemon) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(PokemonEntry.COLUMN_NAME_THREE, pokemon.getType());
        values.put(PokemonEntry.COLUMN_NAME_FOUR, pokemon.getAbilities());
        values.put(PokemonEntry.COLUMN_NAME_FIVE, pokemon.getHeight());
        values.put(PokemonEntry.COLUMN_NAME_SIX, pokemon.getWeight());
        values.put(PokemonEntry.COLUMN_NAME_EIGHT, pokemon.getHp());
        values.put(PokemonEntry.COLUMN_NAME_NINE, pokemon.getAttack());
        values.put(PokemonEntry.COLUMN_NAME_TEN, pokemon.getDefense());
        values.put(PokemonEntry.COLUMN_NAME_ELEVEN, pokemon.getSpAttack());
        values.put(PokemonEntry.COLUMN_NAME_TWELVE, pokemon.getSpDefense());
        values.put(PokemonEntry.COLUMN_NAME_THIRTEEN, pokemon.getSpeed());
        values.put(PokemonEntry.COLUMN_NAME_FOURTEEN, pokemon.getLocations());
        values.put(PokemonEntry.COLUMN_NAME_FIFTEEN, pokemon.getMoves());
        values.put(PokemonEntry.COLUMN_NAME_SIXTEEN, pokemon.getEvolution());

        String selection = PokemonEntry.COLUMN_NAME_ONE + " = ?";
        System.out.println(pokemon.getNumber());
        //All the different IDs its equal to
        String[] selectionArgs = {pokemon.getNumber()};

        db.update(TABLE_NAME, values, selection, selectionArgs);
        db.close();
    }
}