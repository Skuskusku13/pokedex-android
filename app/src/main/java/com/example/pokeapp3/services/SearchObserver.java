package com.example.pokeapp3.services;

import com.example.pokeapp3.models.Pokemon;

import java.util.ArrayList;

public interface SearchObserver {
    void onReceivePokeInfo(Pokemon pokemon);
}
