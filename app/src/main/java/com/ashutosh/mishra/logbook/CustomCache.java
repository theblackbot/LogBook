package com.ashutosh.mishra.logbook;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by Ashutosh on 13-08-2016.
 */
public class CustomCache {

    AppCompatActivity activity;

    public CustomCache(AppCompatActivity appCompatActivity) {
        this.activity = appCompatActivity;
    }

    private SharedPreferences getPrefs(){
        return activity.getSharedPreferences(AppConstants.TAG_APP_PREFS, Context.MODE_PRIVATE);
    }

    private SharedPreferences.Editor getEditor(){
        return getPrefs().edit();
    }


    public String getAccessToken() {
        return getPrefs().getString("access_token", null);
    }

    public void setAccessToken(String accessToken) {
        SharedPreferences.Editor editor = getEditor().putString("access_token", accessToken);
        editor.commit();
    }
}
