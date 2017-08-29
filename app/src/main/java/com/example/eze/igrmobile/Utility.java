package com.example.eze.igrmobile;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.view.MenuItem;
import android.widget.Toast;

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
    public static void draerableMenu(Context context, MenuItem item){
        switch(item.getItemId()){
            case R.id.dashboard:
                Utility.dashboard(context);
                //Toast.makeText(this, "clicked dashboard", Toast.LENGTH_SHORT).show();
                break;
            case R.id.mda:
                Utility.mda(context);
                //Toast.makeText(this, "clicked MdaActivity", Toast.LENGTH_SHORT).show();
                break;
            case R.id.pos:
                Toast.makeText(context, "clicked pos", Toast.LENGTH_SHORT).show();
                break;
            case R.id.remittance:
                Toast.makeText(context, "clicked Remittance", Toast.LENGTH_SHORT).show();
                break;
            case R.id.ebills:
                Toast.makeText(context, "clicked ebills", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
