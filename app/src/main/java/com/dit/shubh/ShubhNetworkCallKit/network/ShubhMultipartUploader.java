package com.dit.shubh.ShubhNetworkCallKit.network;

import android.util.Log;

import com.dit.shubh.ShubhNetworkCallKit.econstants.ShubhNetworkEconstants;
import com.dit.shubh.ShubhNetworkCallKit.model.ShubhOfflineObject;
import com.dit.shubh.ShubhNetworkCallKit.model.ShubhUploadObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class ShubhMultipartUploader {

    public ShubhOfflineObject doInBackground(ShubhUploadObject uploadObject) {
        String boundary = "*****";
        String lineEnd = "\r\n";
        String twoHyphens = "--";
        String fileField = "files";
        String dataField = "data";

        HttpURLConnection conn = null;
        DataOutputStream dos;

        String urlStr = uploadObject.getUrl() + uploadObject.getMethodName();
        File uploadFile = new File(uploadObject.getImagePath());

        try {
            // Validate file
            if (!isAllowedFileType(uploadObject.getImagePath())) {
                return ShubhNetworkEconstants.createOfflineObject(
                        uploadObject.getUrl(),
                        uploadObject.getParam(),
                        "❌ Unsupported file type! Only images/pdf allowed.",
                        "400",
                        uploadObject.getMethodName()
                );
            }

            URL url = new URL(urlStr);
            conn = (HttpURLConnection) url.openConnection();
            conn.setDoInput(true);
            conn.setDoOutput(true);
            conn.setUseCaches(false);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Connection", "Keep-Alive");
            conn.setRequestProperty("ENCTYPE", "multipart/form-data");
            conn.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);
            conn.setRequestProperty(fileField, uploadFile.getName());

            dos = new DataOutputStream(conn.getOutputStream());

            // JSON body part
            dos.writeBytes(twoHyphens + boundary + lineEnd);
            dos.writeBytes("Content-Disposition: form-data; name=\"" + dataField + "\"" + lineEnd);
            dos.writeBytes("Content-Type: application/json" + lineEnd);
            dos.writeBytes(lineEnd);
            dos.writeBytes(uploadObject.getBody());
            dos.writeBytes(lineEnd);

            // File part
            FileInputStream fileInputStream = new FileInputStream(uploadFile);
            dos.writeBytes(twoHyphens + boundary + lineEnd);
            dos.writeBytes("Content-Disposition: form-data; name=\"" + fileField + "\"; filename=\"" + uploadFile.getName() + "\"" + lineEnd);
            dos.writeBytes("Content-Type: application/octet-stream" + lineEnd);
            dos.writeBytes(lineEnd);

            byte[] buffer = new byte[4096];
            int bytesRead;
            while ((bytesRead = fileInputStream.read(buffer)) != -1) {
                dos.write(buffer, 0, bytesRead);
            }
            dos.writeBytes(lineEnd);
            dos.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);

            fileInputStream.close();
            dos.flush();
            dos.close();

            // Read API response
            int responseCode = conn.getResponseCode();
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    responseCode == 200 ? conn.getInputStream() : conn.getErrorStream()
            ));
            StringBuilder responseStr = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                responseStr.append(line).append("\n");
            }
            reader.close();
            conn.disconnect();

            return ShubhNetworkEconstants.createOfflineObject(
                    uploadObject.getUrl(),
                    uploadObject.getParam(),
                    responseStr.toString(),
                    String.valueOf(responseCode),
                    uploadObject.getMethodName()
            );

        } catch (Exception e) {
            Log.e("MultipartUploader", "Upload failed", e);
            return ShubhNetworkEconstants.createOfflineObject(
                    uploadObject.getUrl(),
                    uploadObject.getParam(),
                    e.getMessage(),
                    "500",
                    uploadObject.getMethodName()
            );
        }
    }

    // ✅ Helper for file validation
    private boolean isAllowedFileType(String filePath) {
        String[] allowed = {".jpg", ".jpeg", ".png", ".pdf", ".bmp", ".gif", ".webp"};
        filePath = filePath.toLowerCase();
        for (String ext : allowed) if (filePath.endsWith(ext)) return true;
        return false;
    }
}
