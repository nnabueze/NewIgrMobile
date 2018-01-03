package com.ercasng.eze.igrmobile.parser;

import com.ercasng.eze.igrmobile.model.InvoiceModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by USER on 10/16/2017.
 */

public class InvoiceParser {
    public static List<InvoiceModel> parseFeed(String content){
        List<InvoiceModel> invoiceList;
        try {

            JSONObject jsonObj = new JSONObject(content);

            JSONArray ar = jsonObj.getJSONArray("info");

            invoiceList = new ArrayList<>();

            for (int i = 0; i < ar.length(); i++) {
                JSONObject obj = ar.getJSONObject(i);
                InvoiceModel invoiceModel = new InvoiceModel();

                invoiceModel.setId(obj.getString("id"));
                invoiceModel.setAmount(obj.getString("amount"));
                invoiceModel.setStatus(obj.getString("status"));

                invoiceList.add(invoiceModel);
            }


        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
        return invoiceList;
    }
}
