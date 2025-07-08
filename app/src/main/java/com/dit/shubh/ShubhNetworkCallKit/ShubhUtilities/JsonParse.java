package com.dit.shubh.ShubhNetworkCallKit.ShubhUtilities;


import com.dit.shubh.ShubhNetworkCallKit.model.ShubhSuccessResponse;

import org.json.JSONException;
import org.json.JSONObject;

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




}
