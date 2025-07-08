package com.dit.shubh.ShubhNetworkCallKit.network;

import com.dit.shubh.ShubhNetworkCallKit.model.ShubhOfflineObject;
import com.dit.shubh.ShubhNetworkCallKit.model.ShubhSuccessResponse;

// Wrapper Class Helps to Parse the response if its 200
// Returns parsed response on 200 else Complete Raw Response

// This is JUST a Nested Pojo.. With 2 Types of Response Pojos

public class ShubhResponseWrapper {

    private ShubhOfflineObject rawResponse;
    private ShubhSuccessResponse parsedResponse;

    // Constructor
    public ShubhResponseWrapper(ShubhOfflineObject raw, ShubhSuccessResponse parsed) {
        this.rawResponse = raw;
        this.parsedResponse = parsed;
    }


    // Getter Setter
    public ShubhOfflineObject getRawResponse() {
        return rawResponse;
    }

    public void setRawResponse(ShubhOfflineObject rawResponse) {
        this.rawResponse = rawResponse;
    }

    public ShubhSuccessResponse getParsedResponse() {
        return parsedResponse;
    }

    public void setParsedResponse(ShubhSuccessResponse parsedResponse) {
        this.parsedResponse = parsedResponse;
    }
}
