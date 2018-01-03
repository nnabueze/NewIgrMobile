package com.ercasng.eze.igrmobile.parser;

import com.ercasng.eze.igrmobile.model.Auth;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by EZE on 8/16/2017.
 */

public class AuthParser {
    public static Auth parseFeed(String content){
        Auth auth = new Auth();
        try {
            JSONObject jsonObj = new JSONObject(content);

            //Log.d("OutPut",jsonObj.getString("token"));
            /*auth.setToken(jsonObj.getJSONObject("data").getString("token"));*/
            auth.setLastMonth(jsonObj.getJSONObject("data").getString("last_month"));
            auth.setCurrentMonth(jsonObj.getJSONObject("data").getString("current_month"));
            auth.setYestarday(jsonObj.getJSONObject("data").getString("yestarday"));
            auth.setToday(jsonObj.getJSONObject("data").getString("today"));
            auth.setName(jsonObj.getJSONObject("data").getString("state_name"));
            /*auth.setImage(jsonObj.getJSONObject("data").getString("logo"));
            auth.setId(jsonObj.getJSONObject("data").getString("igr_key"));*/
            auth.setBillerId(jsonObj.getJSONObject("data").getString("billerId"));
            auth.setCurrentMonthRemitted(jsonObj.getJSONObject("data").getString("current_month_remitted"));
            auth.setLastMonthRemitted(jsonObj.getJSONObject("data").getString("last_month_remitted"));
            auth.setYestardayRemitted(jsonObj.getJSONObject("data").getString("yestarday_remitted"));
            auth.setTodayRemitted(jsonObj.getJSONObject("data").getString("today_remitted"));
            auth.setLogo(jsonObj.getJSONObject("data").getString("logo"));

            //return auth;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }

        return auth;
    }
}
