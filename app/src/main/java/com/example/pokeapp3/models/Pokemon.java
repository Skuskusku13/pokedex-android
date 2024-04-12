package com.example.pokeapp3.models;

import androidx.annotation.Nullable;

import java.io.Serializable;
import java.util.ArrayList;

public class Pokemon implements Serializable, Comparable {

    private int id;
    private String name;
    private int weight;
    private int height;
    private String spriteUrlFront;
    private String spriteUrlBack;
    private ArrayList<String> types;

    public Pokemon(int id, String name, int weight, int height, String spriteUrlFront, String spriteUrlBack, ArrayList<String> types) {
        this.id = id;
        this.name = name;
        this.weight = weight;
        this.height = height;
        this.spriteUrlFront = spriteUrlFront;
        this.spriteUrlBack = spriteUrlBack;
        this.types = types;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public String getSpriteUrlFront() {
        return spriteUrlFront;
    }

    public void setSpriteUrlFront(String spriteUrlFront) {
        this.spriteUrlFront = spriteUrlFront;
    }

    public String getSpriteUrlBack() {
        return spriteUrlBack;
    }

    public void setSpriteUrlBack(String spriteUrlBack) {
        this.spriteUrlBack = spriteUrlBack;
    }

    public ArrayList<String> getTypes() {
        return types;
    }

    public void setTypes(ArrayList<String> types) {
        this.types = types;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        Pokemon poke = (Pokemon) obj;
        return id == poke.getId();
    }

    @Override
    public int compareTo(Object o) {
        Pokemon pokemon = (Pokemon) o;
        return id - pokemon.id;
    }
}
