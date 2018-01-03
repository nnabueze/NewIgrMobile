package com.ercasng.eze.igrmobile.parser;

import com.ercasng.eze.igrmobile.model.Mda;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by EZE on 8/21/2017.
 */

public class MdaParser {
    public static List<Mda> parseFeed(String content){
        List<Mda> mdaList;
        try {

            JSONObject jsonObj = new JSONObject(content);

            JSONArray ar = jsonObj.getJSONArray("info");
            System.out.println(ar.length());
            mdaList = new ArrayList<>();

            for (int i = 0; i < ar.length(); i++) {
                JSONObject obj = ar.getJSONObject(i);
                Mda mda = new Mda();

                mda.setName(obj.getString("name"));
                mda.setAmount(obj.getString("amount"));

                mdaList.add(mda);
            }


        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
        return mdaList;
    }
}
