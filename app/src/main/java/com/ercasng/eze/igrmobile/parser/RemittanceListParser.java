package com.ercasng.eze.igrmobile.parser;

import com.ercasng.eze.igrmobile.model.RemittanceListModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by USER on 10/15/2017.
 */

public class RemittanceListParser {
    public static List<RemittanceListModel> parseFeed(String content){
        List<RemittanceListModel> remittanceList;
        try {

            JSONObject jsonObj = new JSONObject(content);

            JSONArray ar = jsonObj.getJSONArray("info");

            remittanceList = new ArrayList<>();

            for (int i = 0; i < ar.length(); i++) {
                JSONObject obj = ar.getJSONObject(i);
                RemittanceListModel remittanceListModel = new RemittanceListModel();

                remittanceListModel.setId(obj.getString("Id"));
                remittanceListModel.setAmount(obj.getString("amount"));
                remittanceListModel.setName(obj.getString("name"));
                remittanceListModel.setRemittedate("remiteDate");
                remittanceListModel.setStatus(obj.getString("status"));

                remittanceList.add(remittanceListModel);
            }


        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
        return remittanceList;
    }
}
