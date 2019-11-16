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
        String res = sharedPref.getString(KEY_FAV,"");
        if(res.isEmpty()){
            res=res+id;
        }else{
            res=res+","+id;
        }
        editor.putString(KEY_FAV,res);
        editor.commit();
    }

    public String[] getFav(){
        String currFav = sharedPref.getString(KEY_FAV,"");
        String[]idFav = currFav.split(",");
        return idFav;
    }

    public boolean cekIsFav(String id){
        if(sharedPref.getString(KEY_FAV,"").contains(id)){
            return true;
        }else{
            return false;
        }
    }
}
