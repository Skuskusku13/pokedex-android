package com.example.pokeapp3.models;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.pokeapp3.R;
import com.example.pokeapp3.services.ApiService;
import com.example.pokeapp3.services.SearchObserver;

import java.util.ArrayList;

public class PokemonAdapter extends BaseAdapter {

    private ArrayList<Pokemon> pokemons;
    private Context context;

    public PokemonAdapter(ArrayList<Pokemon> pokemons, Context context) {
        this.pokemons = pokemons;
        this.context = context;
    }

    public void setPokemons(ArrayList<Pokemon> pokemons) {
        this.pokemons = pokemons;
    }

    @Override
    public int getCount() {
        return this.pokemons.size();
    }

    @Override
    public Object getItem(int i) {
        return this.pokemons.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if(view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.pokemon_list, viewGroup, false);
        }

        TextView textViewName = view.findViewById(R.id.namePoke);
        textViewName.setText(this.pokemons.get(i).getName().toUpperCase());

        TextView textViewId = view.findViewById(R.id.idPoke);
        textViewId.setText("ID #" + this.pokemons.get(i).getId());

        ImageView imageView = view.findViewById(R.id.imagePoke);
        ApiService.loadPokeAvatar(context, pokemons.get(i).getSpriteUrlFront().toLowerCase(), imageView);

        return view;
    }

}
