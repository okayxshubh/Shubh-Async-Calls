package com.dit.shubh.ShubhNetworkCallKit.network;

import android.util.Log;

import com.dit.shubh.ShubhNetworkCallKit.econstants.ShubhNetworkEconstants;
import com.dit.shubh.ShubhNetworkCallKit.model.ShubhOfflineObject;
import com.dit.shubh.ShubhNetworkCallKit.model.ShubhSuccessResponse;
import com.dit.shubh.ShubhNetworkCallKit.model.ShubhUploadObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class ShubhHttpManager {


    /**
     * 🔧 How to Configure ShubhUploadObject for Each HTTP Type
     * --------------------------------------------------------
     * ➤ GET:
     *    - url         ✅ (base URL)
     *    - methodName  ✅ (endpoint path)
     *    - param       🔁 (optional query string, e.g., "?id=123")
     *    - body        ❌ (ignored)
     *
     * ➤ POST:
     *    - url         ✅ (base URL)
     *    - methodName  ✅ (endpoint path)
     *    - param       🔁 (optional, usually null)
     *    - body        ✅ (JSON string)
     *
     * ➤ PUT:
     *    - url         ✅ (base URL)
     *    - methodName  ✅ (endpoint path)
     *    - param       🔁 (optional, usually null)
     *    - body        ✅ (JSON string)
     *
     * ➤ DELETE:
     *    - url         ✅ (base URL)
     *    - methodName  ✅ (endpoint path)
     *    - param       🔁 (optional query string)
     *    - body        ❌ (ignored)
     *
     * Common:
     *    - httpTaskType   ✅ (GET / POST / PUT / DELETE)
     *    - appTaskType    ✅ (For internal use e.g. tracking which call)
     *    - imagePath/imagePathsList ❌ (Optional, ignored unless used in future)
     */



//    GET POST PUT DELETE  #########################################################

    // GET
    public ShubhOfflineObject ShubhGetData(ShubhUploadObject data) throws IOException {
        BufferedReader reader = null;
        URL urlObj = null;
        ShubhOfflineObject response = null;
        HttpURLConnection connection = null;

        try {
            // Build URL
            StringBuilder SB = new StringBuilder();
            SB.append(data.getUrl());
            SB.append(data.getMethodName());

            if (data.getParam() != null)
                SB.append(data.getParam());

            urlObj = new URL(SB.toString());

            // Log Request
            logRequest("GET", urlObj.toString(), "", "");

            // Open connection
            connection = (HttpURLConnection) urlObj.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setConnectTimeout(10000);
            connection.setReadTimeout(10000);
//            connection.setRequestProperty("Authorization", "Bearer " + Preferences.getInstance().token);

            int responseCode = connection.getResponseCode();
            StringBuilder sb = new StringBuilder();

            InputStream inputStream = (responseCode == 200) ?
                    connection.getInputStream() : connection.getErrorStream();

            reader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line).append("\n");
            }

            // Log Response
            logResponse("GET", responseCode, sb.toString());

            connection.disconnect();

            response = ShubhNetworkEconstants.createOfflineObject(
                    data.getUrl(), data.getParam(), sb.toString(), String.valueOf(responseCode), data.getMethodName()
            );

            return response;

        } catch (Exception e) {
            e.printStackTrace();
            int code = (connection != null) ? connection.getResponseCode() : 0;
            response = ShubhNetworkEconstants.createOfflineObject(
                    data.getUrl(), data.getParam(), e.getLocalizedMessage(), String.valueOf(code), data.getMethodName()
            );
            return response;
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    int code = (connection != null) ? connection.getResponseCode() : 0;
                    response = ShubhNetworkEconstants.createOfflineObject(
                            data.getUrl(), data.getParam(), e.getLocalizedMessage(), String.valueOf(code), data.getMethodName()
                    );
                }
            }
        }
    }

    // POST
    public ShubhOfflineObject ShubhPostData(ShubhUploadObject data) throws IOException {
        HttpURLConnection conn = null;
        BufferedReader reader = null;
        ShubhOfflineObject response = null;

        try {
            String urlStr = data.getUrl() + data.getMethodName();
            URL url = new URL(urlStr);
            conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
//            conn.setRequestProperty("Authorization", "Bearer " + Preferences.getInstance().token);
            conn.setConnectTimeout(10000);
            conn.setReadTimeout(10000);

            if (data.getBody() != null) {
                conn.getOutputStream().write(data.getBody().getBytes());
            }

            logRequest("POST", urlStr, "", data.getBody());

            int responseCode = conn.getResponseCode();
            InputStream inputStream = (responseCode == 200) ? conn.getInputStream() : conn.getErrorStream();

            reader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line).append("\n");
            }

            logResponse("POST", responseCode, sb.toString());

            response = ShubhNetworkEconstants.createOfflineObject(
                    data.getUrl(), data.getParam(), sb.toString(), String.valueOf(responseCode), data.getMethodName());

            return response;

        } catch (Exception e) {
            e.printStackTrace();
            int code = (conn != null) ? conn.getResponseCode() : 0;
            response = ShubhNetworkEconstants.createOfflineObject(
                    data.getUrl(), data.getParam(), e.getLocalizedMessage(), String.valueOf(code), data.getMethodName());
            return response;
        } finally {
            if (reader != null) reader.close();
            if (conn != null) conn.disconnect();
        }
    }

    // Put
    public ShubhOfflineObject ShubhPutData(ShubhUploadObject data) throws IOException {
        HttpURLConnection conn = null;
        BufferedReader reader = null;
        ShubhOfflineObject response = null;

        try {
            String urlStr = data.getUrl() + data.getMethodName();
            URL url = new URL(urlStr);
            conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setRequestMethod("PUT");
            conn.setRequestProperty("Content-Type", "application/json");
//            conn.setRequestProperty("Authorization", "Bearer " + Preferences.getInstance().token);
            conn.setConnectTimeout(10000);
            conn.setReadTimeout(10000);

            if (data.getBody() != null) {
                conn.getOutputStream().write(data.getBody().getBytes());
            }

            logRequest("PUT", urlStr, "", data.getBody());

            int responseCode = conn.getResponseCode();
            InputStream inputStream = (responseCode == 200) ? conn.getInputStream() : conn.getErrorStream();

            reader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line).append("\n");
            }

            logResponse("PUT", responseCode, sb.toString());

            response = ShubhNetworkEconstants.createOfflineObject(
                    data.getUrl(), data.getParam(), sb.toString(), String.valueOf(responseCode), data.getMethodName());

            return response;

        } catch (Exception e) {
            e.printStackTrace();
            int code = (conn != null) ? conn.getResponseCode() : 0;
            response = ShubhNetworkEconstants.createOfflineObject(
                    data.getUrl(), data.getParam(), e.getLocalizedMessage(), String.valueOf(code), data.getMethodName());
            return response;
        } finally {
            if (reader != null) reader.close();
            if (conn != null) conn.disconnect();
        }
    }

    // Delete
    public ShubhOfflineObject ShubhDeleteData(ShubhUploadObject data) throws IOException {
        HttpURLConnection conn = null;
        BufferedReader reader = null;
        ShubhOfflineObject response = null;

        try {
            String urlStr = data.getUrl() + data.getMethodName();
            URL url = new URL(urlStr);
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("DELETE");
//            conn.setRequestProperty("Authorization", "Bearer " + Preferences.getInstance().token);
            conn.setConnectTimeout(10000);
            conn.setReadTimeout(10000);

            logRequest("DELETE", urlStr, "", "");

            int responseCode = conn.getResponseCode();
            InputStream inputStream = (responseCode == 200) ? conn.getInputStream() : conn.getErrorStream();

            reader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line).append("\n");
            }

            logResponse("DELETE", responseCode, sb.toString());

            response = ShubhNetworkEconstants.createOfflineObject(
                    data.getUrl(), data.getParam(), sb.toString(), String.valueOf(responseCode), data.getMethodName());

            return response;

        } catch (Exception e) {
            e.printStackTrace();
            int code = (conn != null) ? conn.getResponseCode() : 0;
            response = ShubhNetworkEconstants.createOfflineObject(
                    data.getUrl(), data.getParam(), e.getLocalizedMessage(), String.valueOf(code), data.getMethodName());
            return response;
        } finally {
            if (reader != null) reader.close();
            if (conn != null) conn.disconnect();
        }
    }


//    Multipart ##################################################################

    // Multipart Upload Method
    public ShubhOfflineObject ShubhMultipartUpload(ShubhUploadObject uploadObject) {
        ShubhOfflineObject result;

        try {
            ShubhMultipartUploader uploader = new ShubhMultipartUploader();
            result = uploader.doInBackground(uploadObject);
        } catch (Exception e) {
            Log.e("Multipart Upload", "Error: " + e.getMessage(), e);
            result = ShubhNetworkEconstants.createOfflineObject(
                    uploadObject.getUrl(),
                    uploadObject.getParam(),
                    "Upload failed: " + e.getMessage(),
                    "500",
                    uploadObject.getMethodName()
            );
        }

        return result;
    }



//    HELPER METHODS ##################################################################


    // Custom Helper Methods
    private void logRequest(String type, String url, String params, String body) {
        StringBuilder sb = new StringBuilder();
        sb.append("\n╔═══════════════════════════════════════════════╗\n");
        sb.append("║               📡 HTTP REQUEST                 ║\n");
        sb.append("╠═══════════════════════════════════════════════╣\n");
        sb.append("║ Type     : ").append(type).append("\n");
        sb.append("║ URL      : ").append(url).append("\n");
        if (params != null && !params.isEmpty())
            sb.append("║ Params   : ").append(params).append("\n");
        if (body != null && !body.isEmpty())
            sb.append("║ Body     : ").append(body).append("\n");
        sb.append("╚═══════════════════════════════════════════════╝\n");

        Log.e("HTTP REQUEST", sb.toString());
    }

    private void logResponse(String type, int code, String response) {
        StringBuilder sb = new StringBuilder();
        sb.append("\n\n\n╔═══════════════════════════════════════════════╗\n");
        sb.append("║               ✅ HTTP RESPONSE                ║\n");
        sb.append("╠═══════════════════════════════════════════════╣\n");
        sb.append("║ Type     : ").append(type).append("\n");
        sb.append("║ Status   : ").append(code).append("\n");
        sb.append("║ Response :\n").append(response).append("\n");
        sb.append("╚═══════════════════════════════════════════════╝\n\n\n");

        Log.e("HTTP RESPONSE", sb.toString());
    }

    // Inside ShubhHttpManager.java
    public ShubhResponseWrapper makeNetworkCall(ShubhUploadObject obj) {
        ShubhOfflineObject response = null;
        ShubhSuccessResponse parsed = null;

        try {
            if (obj != null) obj.printDetails();

            switch (obj.getHttpTaskType()) {
                case GET:
                    response = ShubhGetData(obj);
                    break;
                case POST:
                    response = ShubhPostData(obj);
                    break;
                case PUT:
                    response = ShubhPutData(obj);
                    break;
                case DELETE:
                    response = ShubhDeleteData(obj);
                    break;
                case MULTIPART_UPLOAD:
                    response = ShubhMultipartUpload(obj);
                    break;
                default:
                    Log.e("Network Call", "Unknown HttpTaskType: " + obj.getHttpTaskType());
                    return new ShubhResponseWrapper(null, null);
            }

            if (response != null && response.getResponse() != null) {
                try {
                    parsed = ShubhNetworkEconstants.getSuccessResponse(response.getResponse());
                } catch (Exception e) {
                    Log.e("Parser", "Failed to parse success response: " + e.getMessage());
                }
            }

        } catch (Exception e) {
            Log.e("Network Call Error", "Error: " + e.getMessage(), e);
        }

        return new ShubhResponseWrapper(response, parsed);
    }




//  EXPERIMENTAL SSL METHODS    #################################################################

//    public ShubhOfflineObject ShubhGetData(ShubhUploadObject data) throws IOException {
//        BufferedReader reader = null;
//        HttpURLConnection connection = null;
//        ShubhOfflineObject response = null;
//
//        try {
//            StringBuilder sbUrl = new StringBuilder();
//            sbUrl.append(data.getUrl()).append(data.getMethodName());
//            if (data.getParam() != null) sbUrl.append(data.getParam());
//
//            String finalUrl = sbUrl.toString();
//            logRequest("GET", finalUrl, "", "");
//
//            connection = ShubhSSLUtil.openConnection(finalUrl, "GET");
//            int responseCode = connection.getResponseCode();
//
//            InputStream inputStream = (responseCode == 200)
//                    ? connection.getInputStream() : connection.getErrorStream();
//
//            StringBuilder sb = ShubhSSLUtil.readStream(inputStream);
//            logResponse("GET", responseCode, sb.toString());
//
//            response = ShubhNetworkEconstants.createOfflineObject(
//                    data.getUrl(), data.getParam(), sb.toString(), String.valueOf(responseCode), data.getMethodName());
//
//        } catch (Exception e) {
//            e.printStackTrace();
//            int code = (connection != null) ? connection.getResponseCode() : 0;
//            response = ShubhNetworkEconstants.createOfflineObject(
//                    data.getUrl(), data.getParam(), e.getLocalizedMessage(), String.valueOf(code), data.getMethodName());
//        } finally {
//            if (reader != null) reader.close();
//            if (connection != null) connection.disconnect();
//        }
//        return response;
//    }


//    public ShubhOfflineObject ShubhPostData(ShubhUploadObject data) throws IOException {
//        HttpURLConnection conn = null;
//        BufferedReader reader = null;
//        ShubhOfflineObject response = null;
//
//        try {
//            String urlStr = data.getUrl() + data.getMethodName();
//            conn = ShubhSSLUtil.openConnection(urlStr, "POST");
//
//            if (data.getBody() != null)
//                conn.getOutputStream().write(data.getBody().getBytes());
//
//            logRequest("POST", urlStr, "", data.getBody());
//
//            int responseCode = conn.getResponseCode();
//            InputStream inputStream = (responseCode == 200) ? conn.getInputStream() : conn.getErrorStream();
//
//            StringBuilder sb = ShubhSSLUtil.readStream(inputStream);
//            logResponse("POST", responseCode, sb.toString());
//
//            response = ShubhNetworkEconstants.createOfflineObject(
//                    data.getUrl(), data.getParam(), sb.toString(), String.valueOf(responseCode), data.getMethodName());
//
//        } catch (Exception e) {
//            e.printStackTrace();
//            int code = (conn != null) ? conn.getResponseCode() : 0;
//            response = ShubhNetworkEconstants.createOfflineObject(
//                    data.getUrl(), data.getParam(), e.getLocalizedMessage(), String.valueOf(code), data.getMethodName());
//        } finally {
//            if (reader != null) reader.close();
//            if (conn != null) conn.disconnect();
//        }
//        return response;
//    }


//    public ShubhOfflineObject ShubhPutData(ShubhUploadObject data) throws IOException {
//        HttpURLConnection conn = null;
//        BufferedReader reader = null;
//        ShubhOfflineObject response = null;
//
//        try {
//            String urlStr = data.getUrl() + data.getMethodName();
//            conn = ShubhSSLUtil.openConnection(urlStr, "PUT");
//
//            if (data.getBody() != null)
//                conn.getOutputStream().write(data.getBody().getBytes());
//
//            logRequest("PUT", urlStr, "", data.getBody());
//
//            int responseCode = conn.getResponseCode();
//            InputStream inputStream = (responseCode == 200) ? conn.getInputStream() : conn.getErrorStream();
//
//            StringBuilder sb = ShubhSSLUtil.readStream(inputStream);
//            logResponse("PUT", responseCode, sb.toString());
//
//            response = ShubhNetworkEconstants.createOfflineObject(
//                    data.getUrl(), data.getParam(), sb.toString(), String.valueOf(responseCode), data.getMethodName());
//
//        } catch (Exception e) {
//            e.printStackTrace();
//            int code = (conn != null) ? conn.getResponseCode() : 0;
//            response = ShubhNetworkEconstants.createOfflineObject(
//                    data.getUrl(), data.getParam(), e.getLocalizedMessage(), String.valueOf(code), data.getMethodName());
//        } finally {
//            if (reader != null) reader.close();
//            if (conn != null) conn.disconnect();
//        }
//        return response;
//    }


//    public ShubhOfflineObject ShubhDeleteData(ShubhUploadObject data) throws IOException {
//        HttpURLConnection conn = null;
//        BufferedReader reader = null;
//        ShubhOfflineObject response = null;
//
//        try {
//            String urlStr = data.getUrl() + data.getMethodName();
//            conn = ShubhSSLUtil.openConnection(urlStr, "DELETE");
//
//            logRequest("DELETE", urlStr, "", "");
//
//            int responseCode = conn.getResponseCode();
//            InputStream inputStream = (responseCode == 200) ? conn.getInputStream() : conn.getErrorStream();
//
//            StringBuilder sb = ShubhSSLUtil.readStream(inputStream);
//            logResponse("DELETE", responseCode, sb.toString());
//
//            response = ShubhNetworkEconstants.createOfflineObject(
//                    data.getUrl(), data.getParam(), sb.toString(), String.valueOf(responseCode), data.getMethodName());
//
//        } catch (Exception e) {
//            e.printStackTrace();
//            int code = (conn != null) ? conn.getResponseCode() : 0;
//            response = ShubhNetworkEconstants.createOfflineObject(
//                    data.getUrl(), data.getParam(), e.getLocalizedMessage(), String.valueOf(code), data.getMethodName());
//        } finally {
//            if (reader != null) reader.close();
//            if (conn != null) conn.disconnect();
//        }
//        return response;
//    }


}


