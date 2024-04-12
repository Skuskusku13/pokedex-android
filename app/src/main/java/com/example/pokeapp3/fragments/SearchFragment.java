package com.example.pokeapp3.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.viewmodel.CreationExtras;

import com.example.pokeapp3.R;
import com.example.pokeapp3.models.Pokemon;
import com.example.pokeapp3.models.PokemonAdapter;
import com.example.pokeapp3.services.ApiService;
import com.example.pokeapp3.services.SearchObserver;

import java.util.ArrayList;
import java.util.Collections;

public class SearchFragment extends Fragment implements SearchObserver, SearchView.OnQueryTextListener, AdapterView.OnItemClickListener, SearchView.OnCloseListener {
    private SearchObserver searchObserver;
    private SearchView searchView;
    private ListView listView;
    private PokemonAdapter pokemonAdapter;
    private ArrayList<Pokemon> pokemons;
    private ArrayList<Pokemon> stockage;
    private boolean firstSearch;

    public void setSearchObserver(SearchObserver searchObserver) {
        this.searchObserver = searchObserver;
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_search, null);

        this.searchView = v.findViewById(R.id.searchView);
        this.listView = v.findViewById(R.id.listViewPokeMain);
        this.listView.setOnItemClickListener(this);

        this.firstSearch = true;


        this.searchView.setOnQueryTextListener(this);
        this.searchView.setOnCloseListener(this);
        this.searchView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchView.setIconified(false); // Active la recherche lors du clic sur le SearchView
            }
        });

        this.pokemons = new ArrayList<>();
        this.pokemonAdapter = new PokemonAdapter(pokemons, getContext());

        ApiService.searchRequest(getContext(), "", this);

        this.listView.setAdapter(pokemonAdapter);
        return v;
    }

    @Override
    public void onReceivePokeInfo(Pokemon pokemon) {
        if (!pokemons.contains(pokemon)) {
            pokemons.add(pokemon);
            Collections.sort(this.pokemons);
            pokemonAdapter.setPokemons(pokemons);
            pokemonAdapter.notifyDataSetChanged();

            if(this.firstSearch) {
                this.stockage = pokemons;
                this.firstSearch = false;
            }
        }
    }

    @Override
    public boolean onQueryTextSubmit(String s) {
        if (s.equals("")) {
            ApiService.searchRequest(getContext(), "", this);
            this.listView.setAdapter(pokemonAdapter);
        } else {
            this.pokemons = new ArrayList<>();
            ApiService.searchRequest(getContext(), s.toLowerCase(), this);
        }
        return false;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        return false;
    }

    @NonNull
    @Override
    public CreationExtras getDefaultViewModelCreationExtras() {
        return super.getDefaultViewModelCreationExtras();
    }


    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        this.searchObserver.onReceivePokeInfo(pokemons.get(i));
    }

    @Override
    public boolean onClose() {
        this.pokemons.clear();
        this.pokemons.addAll(this.stockage);
        this.pokemonAdapter.setPokemons(stockage);
        this.listView.setAdapter(pokemonAdapter);
        this.pokemonAdapter.notifyDataSetChanged();
        return false;
    }
}
