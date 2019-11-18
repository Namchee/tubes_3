package com.example.tubes_3.sharedPreference;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.Iterator;
import java.util.List;

public class FavoritesPreferences {
    private Gson gson;

    protected SharedPreferences sharedPref;
    protected final static String SP_KEY = "SP_FAVORITES";
    protected final static String KEY_FAV = "FAVORITEs";

    public FavoritesPreferences(Context context) {
        this.sharedPref = context.getSharedPreferences(SP_KEY, 0);
        this.gson = new Gson();
    }

    public List<String> getFavorites(){
        String currFav = sharedPref.getString(KEY_FAV,"");
        Type type = new TypeToken<List<String>>(){}.getType();

        return this.gson.fromJson(currFav, type);
    }

    public void saveFavorite(String id){
        SharedPreferences.Editor editor = this.sharedPref.edit();
        List<String> prevFavorites = this.getFavorites();

        prevFavorites.add(id);

        editor.putString(KEY_FAV, this.gson.toJson(prevFavorites));
        editor.commit();
    }


    public boolean isFavorite(String id){
        List<String> favorites = this.getFavorites();

        for (String favorite: favorites) {
            if (favorite.equals(id)) {
                return true;
            }
        }

        return false;
    }

    public void deleteFavorite(String id) {
        List<String> favorites = this.getFavorites();

        Iterator<String> it = favorites.iterator();

        while (it.hasNext()) {
            String test = it.next();

            if (test.equals(id)) {
                it.remove();
                break;
            }
        }
    }
}