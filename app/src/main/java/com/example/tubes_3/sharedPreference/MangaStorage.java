package com.example.tubes_3.sharedPreference;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.tubes_3.model.History;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class MangaStorage {
    private Gson gson;

    SharedPreferences sharedPref;

    private final static String SP_KEY = "SP_FAVORITES";
    private final static String KEY_FAV = "FAVORITES";
    private final static String KEY_HISTORY = "HISTORIES";

    public MangaStorage(Context context) {
        this.sharedPref = context.getSharedPreferences(SP_KEY, 0);
        this.gson = new Gson();
    }

    public List<String> getFavorites(){
        String currFav = this.sharedPref.getString(KEY_FAV,"");

        List<String> favs = this.gson.fromJson(currFav, List.class);

        if (favs == null) {
            favs = new ArrayList<>();
        }

        return favs;
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

        SharedPreferences.Editor editor = this.sharedPref.edit();

        editor.putString(KEY_FAV, this.gson.toJson(favorites));

        editor.commit();
    }

    public List<History> getHistories() {
        String currHistory = this.sharedPref.getString(KEY_HISTORY, "");

        List<String> historyStrings = this.gson.fromJson(currHistory, List.class);
        List<History> histories = new ArrayList<>();

        if (historyStrings != null) {
            for (String str: historyStrings) {
                histories.add(this.gson.fromJson(str, History.class));
            }
        }

        return histories;
    }

    public void addHistory(History history) {
        List<History> historyList = this.getHistories();

        if (historyList.size() >= 10) {
            historyList.remove(0);
        }

        historyList.add(history);

        String newHistoryString = this.gson.toJson(historyList);

        SharedPreferences.Editor editor = this.sharedPref.edit();

        editor.putString(KEY_FAV, newHistoryString);

        editor.commit();
    }

    /**
     * Must be manga ID
     * @param id
     */
    public void deleteHistory(String id) {
        List<History> historyList = this.getHistories();

        Iterator<History> it = historyList.iterator();

        while (it.hasNext()) {
            History hist = it.next();

            if (hist.getIdManga().equalsIgnoreCase(id)) {
                it.remove();
                break;
            }
        }

        String newHistoryString = this.gson.toJson(historyList);

        SharedPreferences.Editor editor = this.sharedPref.edit();

        editor.putString(KEY_FAV, newHistoryString);

        editor.commit();
    }
}
