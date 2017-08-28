package com.example.eze.igrmobile.parser;

import com.example.eze.igrmobile.model.DashboardModel;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by EZE on 8/15/2017.
 */

public class DashboardParser {
    public static DashboardModel parseFeed(String content){
        try {
            JSONObject jsonObject = new JSONObject(content);
            DashboardModel dashboardModel = new DashboardModel();
            dashboardModel.setBillerName(jsonObject.getString("billerName"));
        } catch (JSONException e) {
            e.printStackTrace();
            return  null;
        }
        return null;

    }
}
