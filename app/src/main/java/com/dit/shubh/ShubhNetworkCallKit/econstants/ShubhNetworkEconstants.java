package com.dit.shubh.ShubhNetworkCallKit.econstants;
import com.dit.shubh.ShubhNetworkCallKit.model.ShubhOfflineObject;
import com.dit.shubh.ShubhNetworkCallKit.model.ShubhResponseObject;
import com.dit.shubh.ShubhNetworkCallKit.model.ShubhSuccessResponse;
import com.dit.shubh.ShubhNetworkCallKit.model.ShubhUploadObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;


public class ShubhNetworkEconstants {

    // Is Not Trimmed Empty
    public static boolean isNotEmpty(String str) {
        return str != null && !str.trim().isEmpty();
    }

    public static final String internetNotAvailable = "Internet not Available. Please Connect to Internet and try again.";

    // status
    public static final Boolean STATUS_TRUE = true;
    public static final Boolean STATUS_FALSE = false;

    // Search Delay
    public static final Integer SEARCH_DELAY = 700;
    public static final Integer PAGE_SIZE = 30;


    // Add master names
    public static final String RANDOM_GENERIC_VALUE = "ABC";
    public static final Integer RANDOM_GENERIC_INT_VALUE = 9999;
    public static final String appUniqueCode = "HRTCDDR";
    public static final String serviceId = "3";

    public static final String loginMethod = "/login?";





// ________________________________________________________  GENERIC CUSTOM METHODS  ________________________________________________________________


    // Custom method to build params.. Use by creating a Map and passing it here..
    public static String buildParams(Map<String, String> params) {
        StringBuilder paramBuilder = new StringBuilder();
        for (Map.Entry<String, String> entry : params.entrySet()) {
            if (paramBuilder.length() > 0) {
                paramBuilder.append("&");
            }
            paramBuilder.append(entry.getKey()).append("=").append(entry.getValue());
        }
        return paramBuilder.toString();
    }

//    //USE Above Method Like
//    Map<String, String> params = new HashMap<>();
//    params.put("username",URLEncoder.encode(encrypteduserName,"UTF-8"));
//    params.put("password",URLEncoder.encode(encryptedPassword,"UTF-8"));
//    String finalParams = buildParams(params); // Use Method
//    uploadObject.setParams();




    // Generic Method to Parse Response Object
    public static ShubhSuccessResponse getSuccessResponse(String data) throws JSONException {
        JSONObject responseObject = new JSONObject(data);
        ShubhSuccessResponse sr = new ShubhSuccessResponse();
        sr.setStatus(responseObject.optString("status"));
        sr.setMessage(responseObject.optString("message"));
        sr.setResponse(responseObject.optString("data"));
        return sr;
    }

    // Used in Shubh Http Manager.. // Helper Method
    public static ShubhOfflineObject createOfflineObject(String url, String requestParams, String response, String Code, String functionName) {
        ShubhOfflineObject pojo = new ShubhOfflineObject();
        pojo.setUrl(url);
        pojo.setRequestParams(requestParams);
        pojo.setResponse(response);
        pojo.setFunctionName(functionName);
        pojo.setResponseCode(Code);
        return pojo;
    }




}

