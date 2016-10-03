package com.example.android.pokeapp;

import android.database.sqlite.SQLiteOpenHelper;

import java.util.List;

/**
 * Created by Jeremy Fu on 13/09/2016.
 */
public class PokemonDbAccess {

    private final PokemonDbContract pokemonDbContract;

    public PokemonDbAccess(SQLiteOpenHelper sqLiteOpenHelper) {
        this.pokemonDbContract = new PokemonDbContract(sqLiteOpenHelper);
    }

    //Example of overriding interface
    //@Override
    public List<Pokemon> getAll() {
        return pokemonDbContract.getPokemons();
    }

    //Example standard contract methods
    public void insertPokemon(Pokemon pokemon){
        pokemonDbContract.insert(pokemon);
    }

    public void deletePokemon(String number){
        pokemonDbContract.delete(number);
    }

    //Example of extra methods
    public void insertPokemons(List<Pokemon> pokemons){
        for(Pokemon pokemon : pokemons){
            pokemonDbContract.insert(pokemon);
        }
    }
/*
    public void deletePokemons(String numbers[]){
        for(String number : numbers){
            pokemonDbContract.delete(number);
        }
    }
    */
    public void deleteAll(){
        String numbers[] = {"001"};
        for (int i = 0; i < 151; i++) {
            String numberString = "";
            if (i < 9) {
                numberString = "00" + Integer.toString(i+1);
            } else if (i < 99) {
                numberString = "0" + Integer.toString(i+1);
            } else {
                numberString = Integer.toString(i+1);
            }
            numbers[i] = numberString;
        }
        for(String number : numbers){
            pokemonDbContract.delete(number);
        }
    }

    public Pokemon getPokemon(String number){
        return pokemonDbContract.getPokemon(number);
    }


    public boolean hasPokemon(String number){
        return pokemonDbContract.getPokemon(number) != null;
    }

    public void updatePokemon(Pokemon pokemon) {
        pokemonDbContract.updatePokemon(pokemon);
    }
}
