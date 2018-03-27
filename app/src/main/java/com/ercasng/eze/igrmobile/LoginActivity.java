package com.ercasng.eze.igrmobile;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
import com.android.volley.toolbox.*;
import com.android.volley.toolbox.Volley;
import com.ercasng.eze.igrmobile.AnimationDir.MyAnimation;
import com.ercasng.eze.igrmobile.model.Auth;
import com.ercasng.eze.igrmobile.model.TokenModel;
import com.ercasng.eze.igrmobile.parser.AuthParser;
import com.ercasng.eze.igrmobile.parser.TokenParser;

import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

public class LoginActivity extends AppCompatActivity {

    private Auth auth;
    private TokenModel tokenModel;
    private Boolean exit = false;
    @Bind(R.id.input_email) EditText emailText;
    @Bind(R.id.input_password) EditText passwordText;
    @Bind(R.id.btn_login) Button loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        MyAnimation.animateEditText2(emailText);
        MyAnimation.animateEditText2(passwordText);
        MyAnimation.animateButton(loginButton);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login();
                /*Intent i = new Intent(LoginActivity.this, Dashboard.class);
                startActivity(i);*/

            }
        });
    }

    private void login() {
        if (!validate()){
            onLoginFail();
        }else{
            if (!isOnLine()){
                Toast.makeText(this, "Network isn't available", Toast.LENGTH_SHORT).show();
            }else{
                tokenCall();
            }
        }
    }

    private void tokenCall() {
        StringRequest request = new StringRequest(Request.Method.POST, Utility.NEW_LOGIN_URL,

                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        tokenModel = TokenParser.parseFeed(response);
                        responseTokenResult();

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
                params.put("username", emailText.getText().toString());
                params.put("password", passwordText.getText().toString());
                params.put("grant_type", "password");

                return params;
            }
        };

        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(request);
    }

    private void responseTokenResult() {
        if (tokenModel == null){
            Toast.makeText(this, "Parser Error occurred", Toast.LENGTH_SHORT).show();
        }else{
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString("token",tokenModel.getAccess_token());
            editor.commit();

            makeCall();
        }
    }

    private void makeCall() {
        final SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        final ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this, R.style.Theme_AppCompat_Light_Dialog);
        progressDialog.setMessage("Authenticating...");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
        StringRequest request = new StringRequest(Request.Method.POST, Utility.NEW_LOGIN_TWO_URL,

                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        auth = AuthParser.parseFeed(response);
                        responseResult();

                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                        //onLoginFailDialog();
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
                        }else{
                            onLoginFailDialog("Invalid email and password");
                        }
                    }
                }){
            //adding header param

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("Email", emailText.getText().toString());
                params.put("Password", passwordText.getText().toString());

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

        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(request);
    }

    private void responseResult() {
        if (auth == null){
            Toast.makeText(this, "Parser Error occurred", Toast.LENGTH_SHORT).show();
        }else {
            Intent i = new Intent(this, Dashboard.class);
            i.putExtra("token", auth.getToken());
            i.putExtra("lastMonth", auth.getLastMonth());
            i.putExtra("currentMonth", auth.getCurrentMonth());
            i.putExtra("yestarday", auth.getYestarday());
            i.putExtra("today", auth.getToday());
            i.putExtra("name",auth.getName());
            i.putExtra("logo", auth.getLogo());
            /*i.putExtra("id", auth.getId());
            i.putExtra("image", auth.getImage());*/

            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString("lastMonth", auth.getLastMonth());
            editor.putString("currentMonth", auth.getCurrentMonth());
            editor.putString("yestarday", auth.getYestarday());
            editor.putString("today", auth.getToday());
            editor.putString("name",auth.getName());
/*            editor.putString("id", auth.getId());
            editor.putString("image", auth.getImage());*/
            editor.putString("billerId", auth.getBillerId());
            editor.putString("last_month_remitted", auth.getLastMonthRemitted());
            editor.putString("current_month_remitted", auth.getCurrentMonthRemitted());
            editor.putString("yestarday_remitted", auth.getYestardayRemitted());
            editor.putString("today_remitted", auth.getTodayRemitted());
            editor.putString("logo", auth.getLogo());
            editor.commit();

            startActivity(i);
        }
    }

    private void onLoginFailDialog() {
        AlertDialog.Builder builder;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder = new AlertDialog.Builder(this, android.R.style.Theme_Material_Dialog_Alert);
        } else {
            builder = new AlertDialog.Builder(this);
        }

        builder.setTitle("Error Message");
        builder.setMessage("Invalid Email and Password");
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        }).show();
    }

    private void onLoginFailDialog(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
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

    private void onLoginFail() {
        Toast.makeText(this, "Invalid email and password", Toast.LENGTH_SHORT).show();
    }

    private boolean validate() {
        boolean valid = true;

        String email = emailText.getText().toString();
        String password = passwordText.getText().toString();

        if (email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            emailText.setError("Enter a valid email address");
            valid=false;
        }else{
            emailText.setError(null);
        }

        if (password.isEmpty() || password.length() < 3){
            passwordText.setError("Password can not be less than 3");
            valid=false;
        }else{
            passwordText.setError(null);
        }
        return valid;
    }

    @Override
    public void onBackPressed() {

        if (exit) {
            finish(); // finish activity
        } else {
            Toast.makeText(this, "Press Back again to Exit.",
                    Toast.LENGTH_SHORT).show();
            exit = true;
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    exit = false;
                }
            }, 10 * 1000);

        }
    }
}
