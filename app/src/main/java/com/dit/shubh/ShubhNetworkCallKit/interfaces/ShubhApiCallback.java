package com.dit.shubh.ShubhNetworkCallKit.interfaces;

import com.dit.shubh.ShubhNetworkCallKit.model.ShubhOfflineObject;
import com.dit.shubh.ShubhNetworkCallKit.model.ShubhSuccessResponse;

public interface ShubhApiCallback {
    void onSuccess(ShubhSuccessResponse response);
    void onFailure(String errorMessage, ShubhOfflineObject fullResponse);
}
