package com.example.pokeapp3.services;

import android.content.Context;
import android.graphics.Bitmap;
import android.widget.ImageView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.pokeapp3.models.Pokemon;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;

public class ApiService {

    private static final String URL_API_SEARCH = "https://pokeapi.co/api/v2/pokemon/";
    private static final String URL_API_FORMAT = "?offset=0&limit=200";
    private static boolean typesBool = false;


    public static void searchRequest(Context context, String search, SearchObserver listener) {
        RequestQueue queue = Volley.newRequestQueue(context);
        String finalSearch = search.equals("") ? URL_API_SEARCH + URL_API_FORMAT : URL_API_SEARCH + search;
        StringRequest request = new StringRequest(finalSearch,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject object = new JSONObject(response);

                            if (search.equals("")) {
                                JSONArray results = object.getJSONArray("results");
                                typesBool = true;

                                for (int i = 0; i < results.length(); i++) {
                                    JSONObject namePoke = results.getJSONObject(i);
                                    String oneName = namePoke.getString("name");
                                    getPokeInfo(context, URL_API_SEARCH + oneName, listener);
                                }

                            } else {
                                typesBool = false;
                                getPokeInfo(context, finalSearch, listener);
                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        queue.add(request);
    }

    public static void getPokeInfo(Context context, String urlPokeInfo, SearchObserver listener) {
        RequestQueue queue = Volley.newRequestQueue(context);
        StringRequest request = new StringRequest(urlPokeInfo,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            ArrayList<String> types = new ArrayList<>();
                            if (typesBool) {
                                JSONArray jsonArrayType = jsonObject.getJSONArray("types");
                                for (int i = 0; i < jsonArrayType.length(); i++) {
                                    String nameType = jsonArrayType.getJSONObject(i).getJSONObject("type").getString("name");
                                    types.add(nameType);
                                }
                            }

                            Pokemon pokemon = new Pokemon(
                                    jsonObject.getInt("id"),
                                    jsonObject.getString("name"),
                                    jsonObject.getInt("weight"),
                                    jsonObject.getInt("height"),
                                    jsonObject.getJSONObject("sprites")
                                            .getString("front_shiny"),
                                    jsonObject.getJSONObject("sprites")
                                            .getString("back_shiny"),
                                    types
                            );
                            listener.onReceivePokeInfo(pokemon);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        queue.add(request);
    }

    public static void loadPokeAvatar(Context context, String pokeName, final ImageView imageView) {
        RequestQueue queue = Volley.newRequestQueue(context);
        ImageRequest request = new ImageRequest(pokeName,
                new Response.Listener<Bitmap>() {
                    @Override
                    public void onResponse(Bitmap bitmap) {
                        imageView.setImageBitmap(bitmap);
                    }
                }, 0, 0, null,
                new Response.ErrorListener() {
                    public void onErrorResponse(VolleyError error) {
                        imageView.setImageResource(android.R.drawable.ic_menu_gallery);
                    }
                });
        queue.add(request);
    }
}
