package com.dit.shubh.ShubhNetworkCallKit.ShubhUtilities;

import android.Manifest;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import androidx.annotation.RequiresPermission;

public class AppStatus {
    private static AppStatus instance = new AppStatus();
    private static Context context;
    private ConnectivityManager connectivityManager;
    private boolean connected = false;

    public static AppStatus getInstance(Context ctx) {
        context = ctx.getApplicationContext();
        return instance;
    }

    // Check if String is null or empty
    public static boolean isNullOrEmpty(String str) {
        return str == null || str.trim().isEmpty();
    }

    // Add Internet Usage in Manifest Files
    @RequiresPermission(Manifest.permission.ACCESS_NETWORK_STATE)
    public boolean isOnline() {
        try {
            connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
            connected = networkInfo != null && networkInfo.isAvailable() && networkInfo.isConnected();
            return connected;
        } catch (Exception e) {
            Log.e("AppStatus", "CheckConnectivity Exception: " + e.getMessage());
        }
        return false;
    }
}
