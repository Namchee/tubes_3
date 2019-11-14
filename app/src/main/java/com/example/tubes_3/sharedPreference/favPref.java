package com.example.tubes_3.sharedPreference;

import android.content.Context;
import android.content.SharedPreferences;

public class favPref {

    protected SharedPreferences sharedPref;
    protected final static String NAME_SP="sp_set_favourite";
    protected final static String KEY_FAV="FAVOURITE";

    public favPref(Context context) {
        this.sharedPref=context.getSharedPreferences(NAME_SP,0);
    }

    public void saveFav(String id){
        SharedPreferences.Editor editor = this.sharedPref.edit();
        editor.putString(KEY_FAV,id);
        editor.commit();
    }

    public String[] getFav(){
        String currFav = sharedPref.getString(KEY_FAV,"");
        String[]idFav = currFav.split(",");
        return idFav;
    }
}
