package com.ercasng.eze.igrmobile.parser;

import com.ercasng.eze.igrmobile.model.PosModel;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by EZE on 9/8/2017.
 */

public class PosParser {
    public static PosModel parseFeed(String content){
        try {
            JSONObject jsonObject = new JSONObject(content);
            PosModel posModel = new PosModel();
            posModel.setLastMonth(jsonObject.getJSONObject("data").getString("last_month"));
            posModel.setCurrentMonth(jsonObject.getJSONObject("data").getString("current_month"));
            posModel.setYesterday(jsonObject.getJSONObject("data").getString("yestarday"));
            posModel.setToday(jsonObject.getJSONObject("data").getString("today"));

            return posModel;
        } catch (JSONException e) {
            e.printStackTrace();
            return  null;
        }

    }
}
