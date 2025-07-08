package com.dit.shubh.ShubhNetworkCallKit.ShubhUtilities;
// ✅ Universal SSL handler for HttpURLConnection / HttpsURLConnection
// ✅ No need to modify all method logic separately
// ✅ Use this class to open SSL-safe connections

import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

public class ShubhSSLUtil {

    // ✅ Creates SSL Socket Factory (skips SSL cert validation)
    public static SSLSocketFactory getSSLSocketFactory() {
        try {
            TrustManager[] trustAllCerts = new TrustManager[]{
                    new X509TrustManager() {
                        public void checkClientTrusted(X509Certificate[] chain, String authType) {}
                        public void checkServerTrusted(X509Certificate[] chain, String authType) {}
                        public X509Certificate[] getAcceptedIssuers() { return new X509Certificate[0]; }
                    }
            };

            SSLContext sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, trustAllCerts, new java.security.SecureRandom());

            HttpsURLConnection.setDefaultHostnameVerifier((hostname, session) -> true);

            return sslContext.getSocketFactory();

        } catch (Exception e) {
            Log.e("SSL", "SSLContext error: " + e.getMessage());
            return null;
        }
    }

    // ✅ Open a universal connection (GET/POST/PUT/DELETE supported)
    public static HttpURLConnection openConnection(String urlStr, String method) {
        try {
            URL url = new URL(urlStr);
            HttpURLConnection conn;

            if (urlStr.startsWith("https")) {
                HttpsURLConnection httpsConn = (HttpsURLConnection) url.openConnection();
                httpsConn.setSSLSocketFactory(getSSLSocketFactory());
                conn = httpsConn;
            } else {
                conn = (HttpURLConnection) url.openConnection();
            }

            conn.setRequestMethod(method);
            conn.setConnectTimeout(10000);
            conn.setReadTimeout(10000);
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setDoOutput(true);

            return conn;
        } catch (Exception e) {
            Log.e("ConnectionError", e.getMessage());
            return null;
        }
    }

    // ✅ Get response from InputStream
    public static StringBuilder readStream(InputStream stream) {
        StringBuilder sb = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(stream))) {
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line).append("\n");
            }
        } catch (Exception e) {
            Log.e("ReadStreamError", e.getMessage());
        }
        return sb;
    }
}
