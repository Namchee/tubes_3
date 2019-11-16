package com.example.tubes_3.sharedPreference;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.tubes_3.model.History;
import com.google.gson.Gson;

public class HistorySP {
    protected SharedPreferences sharedPref;
    protected final static String NAME_SP="sp_history";
    protected final static String KEY_HIS="HISTORY";


    public HistorySP(Context context) {
        this.sharedPref=context.getSharedPreferences(NAME_SP,0);
    }

    public void saveHistory(History hist){
        Gson gson = new Gson();
        String json = gson.toJson(hist);
        SharedPreferences.Editor editor = this.sharedPref.edit();
        String res = sharedPref.getString(KEY_HIS,"");
        if(res.isEmpty()){
            res=res+json;
        }else{
            res=res+","+json;
        }
        editor.putString(KEY_HIS,res);
        editor.commit();
    }

    public String[] getHistory(){
        String allHist = sharedPref.getString(KEY_HIS,"");
        String[]jsonHistory = allHist.split(",");
        return jsonHistory;
    }
}
