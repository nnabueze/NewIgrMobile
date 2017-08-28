package com.example.eze.igrmobile;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by EZE on 8/15/2017.
 */

public class Utility {
    public static final String LOGIN_URL ="http://igr.ercasng.com/api/igr_api_mobile";
    public static final String MDA_URL ="http://igr.ercasng.com/api/mda";

    public static void dashboard(Context context){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);

        Intent i = new Intent(context, Dashboard.class);
        i.putExtra("token", preferences.getString("token","defaultValue"));
        i.putExtra("lastMonth", preferences.getString("lastMonth","defaultValue"));
        i.putExtra("currentMonth", preferences.getString("currentMonth","defaultValue"));
        i.putExtra("yestarday", preferences.getString("yestarday","defaultValue"));
        i.putExtra("today", preferences.getString("today","defaultValue"));
        i.putExtra("name",preferences.getString("name","defaultValue"));
        i.putExtra("id", preferences.getString("id","defaultValue"));
        i.putExtra("image", preferences.getString("image","defaultValue"));

        context.startActivity(i);
    }

    public static void mda(Context context){
        Intent i = new Intent(context, MdaActivity.class);
        context.startActivity(i);
    }
}
