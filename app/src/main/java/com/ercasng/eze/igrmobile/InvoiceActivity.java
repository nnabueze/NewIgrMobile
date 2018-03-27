package com.ercasng.eze.igrmobile;

import android.content.SharedPreferences;
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
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.ercasng.eze.igrmobile.model.InvoiceModel;
import com.ercasng.eze.igrmobile.parser.InvoiceParser;
import com.miguelcatalan.materialsearchview.MaterialSearchView;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.codecrafters.tableview.TableView;
import de.codecrafters.tableview.listeners.TableDataClickListener;
import de.codecrafters.tableview.toolkit.SimpleTableDataAdapter;
import de.codecrafters.tableview.toolkit.SimpleTableHeaderAdapter;
import de.codecrafters.tableview.toolkit.TableDataRowBackgroundProviders;

public class InvoiceActivity extends AppCompatActivity {
    private Menu menu;
    MaterialSearchView searchView;

    String[] spaceProbeHeaders={"ID ","AMOUNT", "STATUS"};
    String[][] spaceProbes;

    public List<InvoiceModel> invoiceModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invoice);

        setUpCollapsToolBar();
        pullData();
    }

    private void pullData() {
        if (!isOnLine()) {
            Toast.makeText(this, "Network isn't available", Toast.LENGTH_SHORT).show();
        } else {
            makeCall();
        }
    }

    private void makeCall() {
        final SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        StringRequest request = new StringRequest(Request.Method.POST, Utility.INVOICE_URL,

                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
//                        Toast.makeText(MdaActivity.this, response, Toast.LENGTH_SHORT).show();
                        invoiceModel = InvoiceParser.parseFeed(response);
                        setTableView();
                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
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
        RequestQueue queue = com.android.volley.toolbox.Volley.newRequestQueue(this);
        queue.add(request);
    }

    private void setTableView() {
        final TableView<String[]> tb =(TableView<String[]>) findViewById(R.id.tableView);
        tb.setColumnCount(3);
        tb.setHeaderBackgroundColor(Color.parseColor("#E0E0E0"));

        populateData();

        //adapters
        tb.setHeaderAdapter(new SimpleTableHeaderAdapter(this, spaceProbeHeaders));
        tb.setDataAdapter(new SimpleTableDataAdapter(this, spaceProbes));
        tb.addDataClickListener(new TableDataClickListener() {
            @Override
            public void onDataClicked(int rowIndex, Object clickedData) {
                Toast.makeText(InvoiceActivity.this, ((String[])clickedData)[0], Toast.LENGTH_SHORT).show();
            }
        });

        /*TableColumnPxWidthModel columnModel = new TableColumnPxWidthModel(3, 350);
        columnModel.setColumnWidth(1, 350);
        columnModel.setColumnWidth(2, 350);
        columnModel.setColumnWidth(3, 350);
        tb.setColumnModel(columnModel);*/

        int colorEvenRows = getResources().getColor(R.color.white);
        int colorOddRows = getResources().getColor(R.color.grey);
        tb.setDataRowBackgroundProvider(TableDataRowBackgroundProviders.alternatingRowColors(colorEvenRows, colorOddRows));
    }

    private void populateData() {
        spaceProbes = new String[invoiceModel.size()][3];
        for (int i = 0; i <invoiceModel.size(); i++) {
            InvoiceModel s = invoiceModel.get(i);
            spaceProbes[i][0] = s.getId();
            spaceProbes[i][1] = numberFormat(s.getAmount());
            spaceProbes[i][2] = s.getStatus();

        }
    }

    private void onLoginFailDialog(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
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
                   // hideOption(R.id.action_info);
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        this.menu = menu;
        getMenuInflater().inflate(R.menu.menu_scrolling, menu);
//        hideOption(R.id.action_info);

        //MenuItem item = menu.findItem(R.id.action_search);
        //searchView = (MaterialSearchView) findViewById(R.id.search_view);
        //searchView.setMenuItem(item);
        //searchQuery();
        return true;
    }

    private void searchQuery() {
        searchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                //Do some magic
                Toast.makeText(InvoiceActivity.this, "submit", Toast.LENGTH_SHORT).show();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                //Do some magic

                return false;
            }
        });

        searchView.setOnSearchViewListener(new MaterialSearchView.SearchViewListener() {
            @Override
            public void onSearchViewShown() {
                //Do some magic
            }

            @Override
            public void onSearchViewClosed() {
                //Do some magic
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
