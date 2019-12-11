package com.project.androidlivetrack;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferenceUtil {
    private SharedPreferenceUtil(){}

    public static final String PREF_NAME = "AppPrefs";

    public static SharedPreferences initPref(Context context){
        return context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    public static String getPrefString(Context context, String key){
        return initPref(context).getString(key,"");
    }
    public static void setPrefString(Context context, String key, String value){
        SharedPreferences.Editor editor = initPref(context).edit();
        editor.putString(key,value);
        editor.apply();
    }
}
