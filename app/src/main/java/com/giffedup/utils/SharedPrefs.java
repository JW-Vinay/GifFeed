package com.giffedup.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by vinay-r on 8/10/15.
 */
public class SharedPrefs {

    private static final String PREF_FILE = "user_pref";
    private static final String GIF_API_KEY = "gif_key";
    private static final String APP_NAME = "app_version";
    private static final String APP_VERSION = "app_version";
    private SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor mEditor;


    public SharedPrefs(Context context) {
        mSharedPreferences = context.getSharedPreferences(PREF_FILE, Context.MODE_PRIVATE);
        mEditor = mSharedPreferences.edit();
    }

    public  void setGifApiKey(String key) {
        mEditor.putString(GIF_API_KEY, key).commit();
    }

    public  String getGifApiKey() {
        return mSharedPreferences.getString(GIF_API_KEY, "dc6zaTOxFJmzC");
    }

}
