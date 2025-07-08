package com.dit.shubh.ShubhNetworkCallKit.ShubhUtilities;


import com.dit.shubh.GenderPojo;
import com.dit.shubh.ShubhNetworkCallKit.model.ShubhSuccessResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonParse {

    // JSON PARSE METHODS

    public static ShubhSuccessResponse getSuccessResponse(String data) throws JSONException {
        JSONObject responseObject = new JSONObject(data);
        ShubhSuccessResponse sr = new ShubhSuccessResponse();
        sr.setStatus(responseObject.optString("status"));
        sr.setMessage(responseObject.optString("message"));
        sr.setResponse(responseObject.optString("data"));
        return sr;
    }


    public static List<GenderPojo> parseGendersList(String response) throws JSONException {
        JSONArray array = new JSONArray(response);

        List<GenderPojo> list = new ArrayList<>();
        for (int i = 0; i < array.length(); i++) {
            JSONObject obj = array.getJSONObject(i);
            GenderPojo item = new GenderPojo();
            item.setId(obj.optLong("id"));
            item.setName(obj.optString("name"));
            list.add(item);
        }

        return list;
    }




}
