package com.example.norkholis.securegateopener.helper;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPrefManager {
    public static final String SGO_APP = "spSGOApp";

    public static final String SP_NAMA = "spNama";
    public static final String SP_ID = "spId";
    public static final String SP_TOKEN = "spToken";

    public static final String SP_SUDAH_LOGIN = "spSudahLogin";

    SharedPreferences sp;
    SharedPreferences.Editor spEditor;

    public SharedPrefManager(Context context){
        sp = context.getSharedPreferences(SGO_APP, Context.MODE_PRIVATE);
        spEditor = sp.edit();
    }

    public void saveSPString(String keySP, String value){
        spEditor.putString(keySP, value);
        spEditor.commit();
    }

    public void saveSPInt(String keySP, int value){
        spEditor.putInt(keySP, value);
        spEditor.commit();
    }

    public void saveSPBoolean(String keySP, boolean value){
        spEditor.putBoolean(keySP, value);
        spEditor.commit();
    }

    public String getSpNama(){
        return sp.getString(SP_NAMA,"");
    }

    public String getSpToken(){
        return sp.getString(SP_TOKEN, "");
    }

    public int getSpId(){
        return sp.getInt(SP_ID, 0);
    }

    public Boolean getSpSudahLogin(){
        return  sp.getBoolean(SP_SUDAH_LOGIN,false);
    }

}
