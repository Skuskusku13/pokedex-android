package com.example.pokeapp3;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;

import com.example.pokeapp3.fragments.PokemonFragment;
import com.example.pokeapp3.fragments.SearchFragment;
import com.example.pokeapp3.models.Pokemon;
import com.example.pokeapp3.services.SearchObserver;

public class MainActivity extends AppCompatActivity implements SearchObserver {

    private SearchFragment searchFragment;
    private PokemonFragment pokemonFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        searchFragment = new SearchFragment();
        pokemonFragment = new PokemonFragment();

        getSupportFragmentManager().beginTransaction()
                .add(R.id.frameLayout, searchFragment)
                .add(R.id.frameLayout, pokemonFragment)
                .hide(pokemonFragment)
                .commit();

        searchFragment.setSearchObserver(this);
    }

    @Override
    public void onReceivePokeInfo(Pokemon pokemon) {
        pokemonFragment.onSelectPokemon(pokemon);
        getSupportFragmentManager().beginTransaction()
                .hide(searchFragment)
                .show(pokemonFragment)
                .commit();
        getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                getSupportFragmentManager().beginTransaction()
                        .show(searchFragment)
                        .hide(pokemonFragment)
                        .commit();
            }
        });
    }
}