package com.example.tubes_3.sharedPreference;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.tubes_3.model.HistoryRaw;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

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

    public void saveFavorite(String id) throws Exception {
        SharedPreferences.Editor editor = this.sharedPref.edit();
        List<String> prevFavorites = this.getFavorites();

        if (prevFavorites.size() >= 3) {
            throw new Exception("Favorite exceeds 3");
        }

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

    public int getFavoritesSize() {
        return this.getFavorites().size();
    }

    public List<HistoryRaw> getHistories() {
        String currHistory = this.sharedPref.getString(KEY_HISTORY, "");

        List<String> historyStrings = this.gson.fromJson(currHistory, List.class);
        List<HistoryRaw> histories = new ArrayList<>();

        if (historyStrings != null) {
            for (String str: historyStrings) {
                histories.add(this.gson.fromJson(str, HistoryRaw.class));
            }
        }

        return histories;
    }

    public void addHistory(HistoryRaw history) {
        List<HistoryRaw> historyList = this.getHistories();

        if (historyList.size() >= 10) {
            historyList.remove(0);
        }

        historyList.add(history);

        List<String> historyStringList = new ArrayList<>();

        for (HistoryRaw raws: historyList) {
            historyStringList.add(this.gson.toJson(raws));
        }

        String newHistoryString = this.gson.toJson(historyStringList);

        SharedPreferences.Editor editor = this.sharedPref.edit();

        editor.putString(KEY_HISTORY, newHistoryString);

        editor.commit();
    }

    /**
     * Must be manga ID
     * @param id
     */
    public void deleteHistory(String id) {
        List<HistoryRaw> historyList = this.getHistories();

        Iterator<HistoryRaw> it = historyList.iterator();

        while (it.hasNext()) {
            HistoryRaw hist = it.next();

            if (hist.getMangaId().equalsIgnoreCase(id)) {
                it.remove();
                break;
            }
        }

        String newHistoryString = this.gson.toJson(historyList);

        SharedPreferences.Editor editor = this.sharedPref.edit();

        editor.putString(KEY_HISTORY, newHistoryString);

        editor.commit();

        System.out.println(this.getHistories().size());
    }
}
