//package com.dit.shubh.ShubhNetworkCallKit.network;
//
//import android.util.Log;
//
//import com.dit.shubh.ShubhNetworkCallKit.econstants.ShubhNetworkEconstants;
//import com.dit.shubh.ShubhNetworkCallKit.model.ShubhOfflineObject;
//import com.dit.shubh.ShubhNetworkCallKit.model.ShubhUploadObject;
//
//import java.io.BufferedReader;
//import java.io.DataOutputStream;
//import java.io.File;
//import java.io.FileInputStream;
//import java.io.IOException;
//import java.io.InputStream;
//import java.io.InputStreamReader;
//import java.net.HttpURLConnection;
//import java.net.URL;
//import java.util.List;
//
//public class ShubhMultipartManager {
//
//    public ShubhOfflineObject uploadMultipartData(ShubhUploadObject data) throws IOException {
//        String boundary = "----ShubhBoundary" + System.currentTimeMillis();
//        String LINE_FEED = "\r\n";
//        HttpURLConnection conn = null;
//        DataOutputStream outputStream = null;
//        BufferedReader reader = null;
//        ShubhOfflineObject response = new ShubhOfflineObject();
//
//        try {
//            String fullUrl = data.getUrl() + data.getMethodName();
//            URL url = new URL(fullUrl);
//            conn = (HttpURLConnection) url.openConnection();
//            conn.setUseCaches(false);
//            conn.setDoOutput(true);
//            conn.setDoInput(true);
//            conn.setRequestMethod("POST");
//            conn.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);
//            conn.setConnectTimeout(15000);
//            conn.setReadTimeout(15000);
//
//            outputStream = new DataOutputStream(conn.getOutputStream());
//
//            if (data.getBody() != null)
//                addFormField(outputStream, boundary, "data", data.getBody());
//
//            if (data.getImagePath() != null)
//                addFilePart(outputStream, boundary, "files", data.getImagePath());
//
//            List<String> imagePaths = data.getImagePathsList();
//            if (imagePaths != null) {
//                int i = 0;
//                for (String path : imagePaths) {
//                    addFilePart(outputStream, boundary, "files" + i, path);
//                    i++;
//                }
//            }
//
//            outputStream.writeBytes("--" + boundary + "--" + LINE_FEED);
//            outputStream.flush();
//
//            int code = conn.getResponseCode();
//            InputStream stream = (code == 200) ? conn.getInputStream() : conn.getErrorStream();
//            reader = new BufferedReader(new InputStreamReader(stream));
//            StringBuilder sb = new StringBuilder();
//            String line;
//            while ((line = reader.readLine()) != null) sb.append(line);
//
//            logRequest("MULTIPART", fullUrl, data.getParam(), data.getBody());
//            logResponse("MULTIPART", code, sb.toString());
//
//            response = ShubhNetworkEconstants.createOfflineObject(
//                    data.getUrl(), data.getParam(), sb.toString(), String.valueOf(code), data.getMethodName()
//            );
//        } catch (Exception e) {
//            e.printStackTrace();
//            response = ShubhNetworkEconstants.createOfflineObject(
//                    data.getUrl(), data.getParam(), e.getLocalizedMessage(), "500", data.getMethodName()
//            );
//        } finally {
//            if (outputStream != null) outputStream.close();
//            if (reader != null) reader.close();
//            if (conn != null) conn.disconnect();
//        }
//
//        return response;
//    }
//
//    private void addFormField(DataOutputStream out, String boundary, String name, String value) throws IOException {
//        out.writeBytes("--" + boundary + "\r\n");
//        out.writeBytes("Content-Disposition: form-data; name=\"" + name + "\"\r\n\r\n");
//        out.writeBytes(value + "\r\n");
//    }
//
//    private void addFilePart(DataOutputStream out, String boundary, String name, String filePath) throws IOException {
//        File file = new File(filePath);
//        String fileName = file.getName();
//        String mimeType = getMimeType(filePath);
//
//        out.writeBytes("--" + boundary + "\r\n");
//        out.writeBytes("Content-Disposition: form-data; name=\"" + name + "\"; filename=\"" + fileName + "\"\r\n");
//        out.writeBytes("Content-Type: " + mimeType + "\r\n\r\n");
//
//        FileInputStream inputStream = new FileInputStream(file);
//        byte[] buffer = new byte[4096];
//        int bytesRead;
//        while ((bytesRead = inputStream.read(buffer)) != -1) {
//            out.write(buffer, 0, bytesRead);
//        }
//        inputStream.close();
//        out.writeBytes("\r\n");
//    }
//
//    private String getMimeType(String filePath) {
//        if (filePath.endsWith(".jpg") || filePath.endsWith(".jpeg")) return "image/jpeg";
//        if (filePath.endsWith(".png")) return "image/png";
//        if (filePath.endsWith(".pdf")) return "application/pdf";
//        return "application/octet-stream";
//    }
//
//    private void logRequest(String type, String url, String param, String body) {
//        StringBuilder log = new StringBuilder("\n");
//        if (type != null) log.append("Type: ").append(type).append("\n");
//        if (url != null) log.append("URL: ").append(url).append("\n");
//        if (param != null) log.append("Params: ").append(param).append("\n");
//        if (body != null) log.append("Body: ").append(body);
//        Log.e("HTTP REQUEST", log.toString());
//    }
//
//    private void logResponse(String type, int code, String response) {
//        StringBuilder log = new StringBuilder("\n");
//        if (type != null) log.append("Type: ").append(type).append("\n");
//        log.append("Status: ").append(code).append("\n");
//        if (response != null) log.append("Response: ").append(response);
//        Log.e("HTTP RESPONSE", log.toString());
//    }
//}
