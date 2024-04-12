package com.example.pokeapp3.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.pokeapp3.R;
import com.example.pokeapp3.models.Pokemon;
import com.example.pokeapp3.models.PokemonAdapter;
import com.example.pokeapp3.services.ApiService;
import com.example.pokeapp3.services.SearchObserver;

import java.util.ArrayList;

public class PokemonFragment extends Fragment {

    private ImageView imageViewFront;
    private ImageView imageViewBack;
    private ListView listView;
    private TextView namePoke, weightPoke, heightPoke;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.pokemon_details, null);

        imageViewFront = v.findViewById(R.id.imageViewPokeDetailsFront);
        imageViewBack = v.findViewById(R.id.imageViewPokeDetailsBack);
        namePoke = v.findViewById(R.id.namePoke);
        weightPoke = v.findViewById(R.id.weightPoke);
        heightPoke = v.findViewById(R.id.heightPoke);
        heightPoke = v.findViewById(R.id.heightPoke);
        listView = v.findViewById(R.id.typesDetailList);


        return v;
    }

    public void onSelectPokemon(Pokemon pokemon) {

        this.namePoke.setText(pokemon.getName().toUpperCase() + " - № " + pokemon.getId());
        this.weightPoke.setText(pokemon.getWeight() + " Kg");
        this.heightPoke.setText(pokemon.getHeight() * 10 + " cm");


        ApiService.loadPokeAvatar(getContext(), pokemon.getSpriteUrlFront(), imageViewFront);

        ApiService.loadPokeAvatar(getContext(), pokemon.getSpriteUrlBack(), imageViewBack);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), R.layout.types_details, pokemon.getTypes());
        listView.setAdapter(adapter);

    }


}
