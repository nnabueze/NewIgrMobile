package com.example.eze.igrmobile;

import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.*;
import com.example.eze.igrmobile.model.Mda;
import com.example.eze.igrmobile.parser.AuthParser;
import com.example.eze.igrmobile.parser.MdaParser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MdaActivity extends AppCompatActivity {
    private List<Mda> mdaList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mda);

        mdaList = new ArrayList<>();

        pullData();
    }

    private void pullData() {
        if (!isOnLine()){
            Toast.makeText(this, "Network isn't available", Toast.LENGTH_SHORT).show();
        }else{
            makeCall();
        }
    }

    private void setUpRecyclerView() {
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        MyAdpter myAdpter = new MyAdpter(this, mdaList);
        recyclerView.setAdapter(myAdpter);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        recyclerView.setItemAnimator(new DefaultItemAnimator());
    }

    private void makeCall() {
        final SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);

        StringRequest request = new StringRequest(Request.Method.POST, Utility.MDA_URL,

                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //Toast.makeText(MdaActivity.this, response, Toast.LENGTH_SHORT).show();
                        mdaList = MdaParser.parseFeed(response);
                        setUpRecyclerView();

                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        onLoginFailDialog();
                    }
                }){
            //adding header param

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("billerId", preferences.getString("billerId", null).toString());
                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<>();
                headers.put("Authorization", "Bearer " + preferences.getString("token", null).toString());
                return headers;
            }
        };

        RequestQueue queue = com.android.volley.toolbox.Volley.newRequestQueue(this);
        queue.add(request);
    }

    private void onLoginFailDialog() {
        Toast.makeText(this, "Unable to get response", Toast.LENGTH_SHORT).show();
    }

    private boolean isOnLine() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting()){
            return true;
        }else {
            return false;
        }
    }
}
