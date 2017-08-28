package com.example.eze.igrmobile;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.ValueDependentColor;
import com.jjoe64.graphview.series.BarGraphSeries;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.text.DecimalFormat;

public class Dashboard extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private TextView lastMonth, currentMonth, yestarday, today, billerName;
    private NavigationView navigationView;

    private Bundle extra;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        setUpToolBarMenu();
        setUpNavigationDrawerMenu();

        setTextView();
    }

    private void setTextView() {
        lastMonth = (TextView) findViewById(R.id.lastMonth);
        currentMonth = (TextView) findViewById(R.id.currentMonth);
        yestarday = (TextView) findViewById(R.id.yestarday);
        today = (TextView) findViewById(R.id.today);

        View header = navigationView.getHeaderView(0);
        billerName = (TextView) header.findViewById(R.id.billerName);

        extra = getIntent().getExtras();
        if (extra == null){
            Log.d("Dashboard","Missing param");
        }else {

            lastMonth.setText(numberFormat(extra.getString("lastMonth")));
            currentMonth.setText(numberFormat(extra.getString("currentMonth")));
            yestarday.setText(numberFormat(extra.getString("yestarday")));
            today.setText(numberFormat(extra.getString("today")));
            billerName.setText(extra.getString("name"));

            plotGraph();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.overlay_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.logout:
                break;
        }
        return true;
    }

    private void setUpNavigationDrawerMenu() {
        navigationView = (NavigationView) findViewById(R.id.navigation_view);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);

        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this,
                drawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close);

        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
    }

    private void setUpToolBarMenu() {
        toolbar = (Toolbar) findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Dashboard");
    }

    private String numberFormat(String number){
        double num = Double.parseDouble(number);
        DecimalFormat money = new DecimalFormat("###,###,###,###");
        String formattedText = "₦" + money.format(num);

        return formattedText;
    }

    private void plotGraph(){

        GraphView graph = (GraphView) findViewById(R.id.graph);
        BarGraphSeries<DataPoint> series = new BarGraphSeries<>(new DataPoint[] {
                new DataPoint(0, 0),
                new DataPoint(1, Double.parseDouble(extra.getString("lastMonth"))),
                new DataPoint(2, Double.parseDouble(extra.getString("currentMonth"))),
                new DataPoint(3, Double.parseDouble(extra.getString("yestarday"))),
                new DataPoint(4, Double.parseDouble(extra.getString("today")))
        });
        graph.addSeries(series);

// styling
        series.setValueDependentColor(new ValueDependentColor<DataPoint>() {
            @Override
            public int get(DataPoint data) {
                return Color.rgb((int) data.getX()*255/4, (int) Math.abs(data.getY()*255/6), 100);
            }
        });

        series.setSpacing(20);

// draw values on top
        series.setDrawValuesOnTop(true);
        series.setValuesOnTopColor(Color.RED);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        closeDrawer();

        switch(item.getItemId()){
            case R.id.dashboard:
                Utility.dashboard(this);
                //Toast.makeText(this, "clicked dashboard", Toast.LENGTH_SHORT).show();
                break;
            case R.id.mda:
                Utility.mda(this);
                //Toast.makeText(this, "clicked MdaActivity", Toast.LENGTH_SHORT).show();
                break;
            case R.id.pos:
                Toast.makeText(this, "clicked pos", Toast.LENGTH_SHORT).show();
                break;
            case R.id.remittance:
                Toast.makeText(this, "clicked Remittance", Toast.LENGTH_SHORT).show();
                break;
            case R.id.ebills:
                Toast.makeText(this, "clicked ebills", Toast.LENGTH_SHORT).show();
                break;
            case R.id.logout:
                Toast.makeText(this, "clicked logout", Toast.LENGTH_SHORT).show();
                break;
        }
        return true;
    }

    private void closeDrawer() {
        drawerLayout.closeDrawer(GravityCompat.START);
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)){
            closeDrawer();
        }else{
            super.onBackPressed();
        }

    }
}

