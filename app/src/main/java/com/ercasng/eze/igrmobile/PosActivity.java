package com.ercasng.eze.igrmobile;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.ercasng.eze.igrmobile.AnimationDir.MyAnimation;
import com.ercasng.eze.igrmobile.model.PosModel;
import com.ercasng.eze.igrmobile.parser.PosParser;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

public class PosActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private TextView lastMonth, currentMonth, yesterday, today, indicator, indicator2, indicator3;
    private PosModel posModel;
    private Menu menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pos);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_NOSENSOR);

        setUpCollapsToolBar();
       setTextView();
        pullData();
    }
    private void setUpCollapsToolBar() {
        final Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        AppBarLayout mAppBarLayout = (AppBarLayout) findViewById(R.id.app_bar);
        mAppBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = false;
            int scrollRange = -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                if (scrollRange + verticalOffset == 0) {
                    isShow = true;
                    //showOption(R.id.action_info);
                } else if (isShow) {
                    isShow = false;
                    //hideOption(R.id.action_info);
                }
            }
        });
    }

    private void hideOption(int id) {
        MenuItem item = menu.findItem(id);
        item.setVisible(false);
    }

    private void showOption(int id) {
        MenuItem item = menu.findItem(id);
        item.setVisible(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        this.menu = menu;
        getMenuInflater().inflate(R.menu.menu_scrolling, menu);
//        hideOption(R.id.action_info);
        return true;
    }

    private void setUpToolBarMenu() {
        toolbar = (Toolbar) findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Pos Collection");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    public void setTextView() {
        lastMonth = (TextView) findViewById(R.id.lastMonth);
        currentMonth = (TextView) findViewById(R.id.currentMonth);
        yesterday = (TextView) findViewById(R.id.yestarday);
        today = (TextView) findViewById(R.id.today);
        indicator = (TextView) findViewById(R.id.indicator);
        indicator2 = (TextView) findViewById(R.id.indicator2);
        indicator3 = (TextView) findViewById(R.id.indicator3);

        MyAnimation.animateHome(lastMonth);
        MyAnimation.animateHome(currentMonth);
        MyAnimation.animateHome(yesterday);
        MyAnimation.animateHome(today);
        MyAnimation.animateHome(indicator);
        MyAnimation.animateHome(indicator2);
        MyAnimation.animateHome(indicator3);

    }

    private void pullData() {
        if (!isOnLine()) {
            Toast.makeText(this, "Network isn't available", Toast.LENGTH_SHORT).show();
        } else {
            makeCall();
        }
    }

    private void makeCall() {
        final ProgressDialog progressDialog = new ProgressDialog(PosActivity.this, R.style.Theme_AppCompat_Light_Dialog);
        progressDialog.setMessage("Loading...");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
        final SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        StringRequest request = new StringRequest(Request.Method.POST, Utility.POS_URL,

                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.hide();
//                        Toast.makeText(MdaActivity.this, response, Toast.LENGTH_SHORT).show();
                        parseRemittanceFeed(response);
                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.hide();
                        if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                            onLoginFailDialog("Communication Error!");

                        } else if (error instanceof AuthFailureError) {
                            onLoginFailDialog("Authentication Error!");
                        } else if (error instanceof ServerError) {
                            onLoginFailDialog("Server Side Error!");
                        } else if (error instanceof NetworkError) {
                            onLoginFailDialog("Network Error!");
                        } else if (error instanceof ParseError) {
                            onLoginFailDialog("Parse Error!");
                        }

                    }
                }) {
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

        request.setRetryPolicy(new RetryPolicy() {
            @Override
            public int getCurrentTimeout() {
                return 90000;
            }

            @Override
            public int getCurrentRetryCount() {
                return 90000;
            }

            @Override
            public void retry(VolleyError error) throws VolleyError {

            }
        });

        RequestQueue queue = com.android.volley.toolbox.Volley.newRequestQueue(this);
        queue.add(request);
    }

    private void parseRemittanceFeed(String response) {
        posModel = PosParser.parseFeed(response);

        lastMonth.setText(numberFormat(posModel.getLastMonth()));
        currentMonth.setText(numberFormat(posModel.getCurrentMonth()));
        yesterday.setText(numberFormat(posModel.getYesterday()));
        today.setText(numberFormat(posModel.getToday()));

        //getting current month
        currentIndicator();
        yesterdayIndicator();
        todayIndicator();

    }

    private void todayIndicator() {
        double yesterdayAmount = Double.parseDouble(posModel.getYesterday());
        double todayAmount = Double.parseDouble(posModel.getToday());

        if (todayAmount > yesterdayAmount){
            //Increase = New Number - Original Number
            double increase = todayAmount - yesterdayAmount;

            //% increase = Increase ÷ Original Number × 100
            double percentageIncrease = increase / yesterdayAmount* 100;

            indicator3.setText(String.format("%.1f", percentageIncrease)+"%");
            indicator3.setVisibility(View.VISIBLE);
            indicator3.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_arrow_drop_up_black_18dp, 0, 0, 0);
            indicator3.setTextColor(Color.parseColor("#2E7D32"));
        }

        if (yesterdayAmount > todayAmount){
            //Increase = New Number - Original Number
            double decrease = todayAmount - yesterdayAmount;

            //% increase = Increase ÷ Original Number × 100
            double percentageDecrease = decrease / yesterdayAmount * 100;

            indicator3.setText(String.format("%.1f", percentageDecrease)+"%");
            indicator3.setVisibility(View.VISIBLE);
            indicator3.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_arrow_drop_down_black_18dp, 0, 0, 0);
            indicator3.setTextColor(Color.parseColor("#C62828"));
        }
    }

    private void yesterdayIndicator() {
        double yesterdayAmount = Double.parseDouble(posModel.getYesterday());
        double currentAmount = Double.parseDouble(posModel.getCurrentMonth());

        if (yesterdayAmount > currentAmount){
            //Increase = New Number - Original Number
            double increase = yesterdayAmount - currentAmount;

            //% increase = Increase ÷ Original Number × 100
            double percentageIncrease = increase / currentAmount* 100;

            indicator2.setText(String.format("%.1f", percentageIncrease)+"%");
            indicator2.setVisibility(View.VISIBLE);
            indicator2.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_arrow_drop_up_black_18dp, 0, 0, 0);
            indicator2.setTextColor(Color.parseColor("#2E7D32"));
        }

        if (currentAmount > yesterdayAmount){
            //Increase = New Number - Original Number
            double decrease = yesterdayAmount - currentAmount;

            //% increase = Increase ÷ Original Number × 100
            double percentageDecrease = decrease / currentAmount * 100;

            indicator2.setText(String.format("%.1f", percentageDecrease)+"%");
            indicator2.setVisibility(View.VISIBLE);
            indicator2.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_arrow_drop_down_black_18dp, 0, 0, 0);
            indicator2.setTextColor(Color.parseColor("#C62828"));
        }
    }

    private void currentIndicator() {
        //checking increase
        double lastAmount = Double.parseDouble(posModel.getLastMonth());
        double currentAmount = Double.parseDouble(posModel.getCurrentMonth());
        if (currentAmount > lastAmount){
            //Increase = New Number - Original Number
            double increase = currentAmount - lastAmount;

            //% increase = Increase ÷ Original Number × 100
            double percentageIncrease = increase / lastAmount* 100;

            indicator.setText(String.format("%.1f", percentageIncrease)+"%");
            indicator.setVisibility(View.VISIBLE);
            indicator.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_arrow_drop_up_black_18dp, 0, 0, 0);
            indicator.setTextColor(Color.parseColor("#2E7D32"));
        }

        if (lastAmount > currentAmount){
            //Increase = New Number - Original Number
            double decrease = currentAmount - lastAmount;

            //% increase = Increase ÷ Original Number × 100
            double percentageDecrease = decrease / lastAmount * 100;

            indicator.setText(String.format("%.1f", percentageDecrease)+"%");
            indicator.setVisibility(View.VISIBLE);
            indicator.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_arrow_drop_down_black_18dp, 0, 0, 0);
            indicator.setTextColor(Color.parseColor("#C62828"));
        }
    }

    private void onLoginFailDialog(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    private boolean isOnLine() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private String numberFormat(String number){
        double num = Double.parseDouble(number);
        DecimalFormat money = new DecimalFormat("###,###,###,###");
        String formattedText = "₦" + money.format(num);

        return formattedText;
    }
}
