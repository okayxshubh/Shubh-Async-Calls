package com.dit.shubh.ShubhNetworkCallKit.ShubhUtilities;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import com.dit.shubh.ShubhNetworkCallKit.econstants.HttpTaskType;
import com.dit.shubh.ShubhNetworkCallKit.interfaces.ShubhApiCallback;
import com.dit.shubh.ShubhNetworkCallKit.model.ShubhOfflineObject;
import com.dit.shubh.ShubhNetworkCallKit.model.ShubhSuccessResponse;
import com.dit.shubh.ShubhNetworkCallKit.model.ShubhUploadObject;
import com.dit.shubh.ShubhNetworkCallKit.network.ShubhHttpManager;
import com.dit.shubh.ShubhNetworkCallKit.network.ShubhResponseWrapper;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

// In ShubhNetworkEconstants.java or ShubhNetworkUtil.java
public class ShubhNetworkUtil {

    // This has a generic static method to make API Calls easily
    public static void makeApiCall(Context context, HttpTaskType httpType, String url, String method, String params, String body, ShubhApiCallback callback) {
        if (!AppStatus.getInstance(context).isOnline()) {
            callback.onFailure("Please connect to the internet", null);
            return;
        }

        // 🔄 Show loader
        Handler handler = new Handler(Looper.getMainLooper()); // Handler Goes Back to Main UI
        ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Please wait..");
        progressDialog.setCancelable(false);
        progressDialog.show();

        ExecutorService executor = Executors.newSingleThreadExecutor();

        executor.execute(() -> {
            try {
                ShubhUploadObject uploadObject = new ShubhUploadObject();
                uploadObject.setUrl(url);
                uploadObject.setMethodName(method);
                uploadObject.setHttpTaskType(httpType);
                uploadObject.setParam(params);
                uploadObject.setBody(body);

                ShubhResponseWrapper resultWrapper = new ShubhHttpManager().makeNetworkCall(uploadObject);
                ShubhOfflineObject raw = (resultWrapper != null) ? resultWrapper.getRawResponse() : null;

                handler.post(() -> {
                    progressDialog.dismiss(); // ❌ hide loader

                    if (raw == null) {
                        callback.onFailure("No response from server", null);
                        return;
                    }

                    String code = raw.getResponseCode();
                    String rawMessage = raw.getResponse();
                    ShubhSuccessResponse parsed = resultWrapper.getParsedResponse();

                    switch (code) {
                        case "200":
                        case "201":
                            if (parsed != null) callback.onSuccess(parsed);
                            else callback.onFailure("Success but response body is empty", raw);
                            break;

                        case "400": callback.onFailure("Bad Request", raw); break;
                        case "401": callback.onFailure("Unauthorized: Login again", raw); break;
                        case "403": callback.onFailure("Forbidden: Access denied", raw); break;
                        case "404": callback.onFailure("Not Found: Invalid endpoint", raw); break;
                        case "500": callback.onFailure("Server Error: Try again later", raw); break;
                        case "503": callback.onFailure("Service Unavailable: Server busy", raw); break;

                        default: callback.onFailure("Unexpected status (" + code + "): " + rawMessage, raw); break;
                    }
                });

            } catch (Exception e) {
                handler.post(() -> {
                    progressDialog.dismiss(); // ❌ hide loader
                    callback.onFailure("Exception: " + e.getMessage(), null);
                });
            }
        });
    }


}
