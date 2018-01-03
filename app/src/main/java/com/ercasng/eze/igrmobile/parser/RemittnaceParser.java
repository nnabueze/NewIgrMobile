package com.ercasng.eze.igrmobile.parser;

import com.ercasng.eze.igrmobile.model.RemittanceModel;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by EZE on 9/7/2017.
 */

public class RemittnaceParser {
    public static RemittanceModel parseFeed(String content){
        try {
            JSONObject jsonObject = new JSONObject(content);
            RemittanceModel remittanceModel = new RemittanceModel();
            remittanceModel.setLastMonthRemite(jsonObject.getJSONObject("data").getString("last_month_remitted"));
            remittanceModel.setCurrentMonthRemite(jsonObject.getJSONObject("data").getString("current_month_remitted"));
            remittanceModel.setLastMonth(jsonObject.getJSONObject("data").getString("last_month"));
            remittanceModel.setCurrentMonth(jsonObject.getJSONObject("data").getString("current_month"));

            return remittanceModel;
        } catch (JSONException e) {
            e.printStackTrace();
            return  null;
        }

    }
}
