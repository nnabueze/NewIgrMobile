package com.ercasng.eze.igrmobile.parser;

import com.ercasng.eze.igrmobile.model.TokenModel;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by USER on 12/2/2017.
 */

public class TokenParser {
    public static TokenModel parseFeed(String content){

        TokenModel tokenModel = new TokenModel();
        try {
            JSONObject jsonObj = new JSONObject(content);
            tokenModel.setAccess_token(jsonObj.getString("access_token"));

            //return auth;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }

        return tokenModel;
    }
}
