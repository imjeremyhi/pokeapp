package com.example.android.pokeapp;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.sql.Blob;

/**
 * Created by Jeremy Fu on 7/09/2016.
 */
public class Pokemon implements Serializable {
    //Class for the pokemon objects. Implements serializable as pokemon objects need to be 
    //passed around in the application
    private String number;
    private String name;
    private String type;
    private String abilities;
    private String height;
    private String weight;
    private byte[] image;
    private String hp;
    private String attack;
    private String defense;
    private String spAttack;
    private String spDefense;
    private String speed;
    private String locations;
    private String moves;
    private String evolution;

    public Pokemon() {

    }

    public Pokemon (String number, String name, String type, String abilities, String height, String weight, byte[] image,
                    String hp, String attack, String defense, String spAttack, String spDefense, String speed,
                    String locations, String moves, String evolution) {
        this.number = number;
        this.name = name;
        this.type = type;
        this.abilities = abilities;
        this.height = height;
        this.weight = weight;
        this.image = image;
        this.hp = hp;
        this.attack = attack;
        this.defense = defense;
        this.spAttack = spAttack;
        this.spDefense = spDefense;
        this.speed = speed;
        this.locations = locations;
        this.moves = moves;
        this.evolution = evolution;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAbilities() {
        return abilities;
    }

    public void setAbilities(String abilities) {
        this.abilities = abilities;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage (byte[] image) {
        this.image = image;
    }

    public String getHp() {
        return hp;
    }

    public void setHp(String hp) {
        this.hp = hp;
    }

    public String getAttack() {
        return attack;
    }

    public void setAttack(String attack) {
        this.attack = attack;
    }

    public String getDefense() {
        return defense;
    }

    public void setDefense(String defense) {
        this.defense = defense;
    }

    public String getSpAttack() {
        return spAttack;
    }

    public void setSpAttack(String spAttack) {
        this.spAttack = spAttack;
    }

    public String getSpDefense() {
        return spDefense;
    }

    public void setSpDefense(String spDefense) {
        this.spDefense = spDefense;
    }

    public String getSpeed() {
        return speed;
    }

    public void setSpeed(String speed) {
        this.speed = speed;
    }

    public String getLocations() {
        return locations;
    }

    public void setLocations(String locations) {
        this.locations = locations;
    }

    public String getMoves() {
        return moves;
    }

    public void setMoves(String moves) {
        this.moves = moves;
    }

    public String getEvolution() {
        return evolution;
    }

    public void setEvolution(String evolution) {
        this.evolution = evolution;
    }
}
