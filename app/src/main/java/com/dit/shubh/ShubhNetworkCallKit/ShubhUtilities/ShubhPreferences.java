package com.dit.shubh.ShubhNetworkCallKit.ShubhUtilities;

import android.content.Context;
import android.content.SharedPreferences;

public class ShubhPreferences {

    private static final String PREF_NAME = "com.dit.shubh.preferences";
    private static ShubhPreferences instance;
    private SharedPreferences prefs;

    private ShubhPreferences(Context context) {
        prefs = context.getApplicationContext().getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    // ⚙️ Singleton init
    public static ShubhPreferences getInstance(Context context) {
        if (instance == null) {
            instance = new ShubhPreferences(context);
        }
        return instance;
    }

    // 🔐 Save Methods
    public void putString(String key, String value) {
        prefs.edit().putString(key, value).apply();
    }

    public void putInt(String key, int value) {
        prefs.edit().putInt(key, value).apply();
    }

    public void putBoolean(String key, boolean value) {
        prefs.edit().putBoolean(key, value).apply();
    }

    public void putLong(String key, long value) {
        prefs.edit().putLong(key, value).apply();
    }

    // 🔓 Get Methods
    public String getString(String key) {
        return prefs.getString(key, null);
    }

    public int getInt(String key) {
        return prefs.getInt(key, -1);
    }

    public boolean getBoolean(String key) {
        return prefs.getBoolean(key, false);
    }

    public long getLong(String key) {
        return prefs.getLong(key, -1);
    }

    // ❌ Clear All
    public void clearAll() {
        prefs.edit().clear().apply();
    }
}
