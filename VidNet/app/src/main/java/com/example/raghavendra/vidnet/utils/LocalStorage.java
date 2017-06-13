package com.example.raghavendra.vidnet.utils;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import com.example.raghavendra.vidnet.model.User;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;


public class LocalStorage {

    private static final String PREF_NAME = "com.propcube.android";

    private static final String USER_KEY = "user_key";
    private static final String KEY_REMINDERS = "key_reminders";
    private static final String KEY_PROMPT_COUNT = "prompt_count";
    private static final String KEY_GCM_ID = "gcm_id";
    private static final String KEY_IS_NEW_USER = "is_new_user";

    /** Update the user profile from the server every 10 times the app opens or if the app has
     * not been opened for last 5 days. Here we maintain the session count and last session
     * time. */
    private static final String KEY_SESSION_COUNT = "session_count";
    private static final String KEY_LAST_SESSION_TIMESTAMP = "last_session_timestamp";

    private static LocalStorage sInstance;

    private SharedPreferences mPref;

    public static LocalStorage getInstance(Context context) {
        if (sInstance == null) {
            return new LocalStorage(context);
        }
        return sInstance;
    }

    public static LocalStorage getInstance() {
        if (sInstance == null) {
            throw new RuntimeException("Local storage cannot be null");
        }
        return sInstance;
    }

    private LocalStorage(Context context) {
        sInstance = this;
        mPref = context.getSharedPreferences(PREF_NAME, Activity.MODE_PRIVATE);
    }

    public void storeUser(User user) {
        String userString = new Gson().toJson(user);
        SharedPreferences.Editor editor = mPref.edit();
        editor.putString(USER_KEY, userString);
        editor.apply();
    }

    public void clearUser() {
        SharedPreferences.Editor editor = mPref.edit();
        editor.remove(USER_KEY);
        editor.apply();
    }

    public User getUser() {
        String userString = mPref.getString(USER_KEY, null);
        if (userString == null) {
            return null;
        }
        return new Gson().fromJson(userString, User.class);
    }


    public int getPromptCount() {
        return mPref.getInt(KEY_PROMPT_COUNT, 0);
    }

    public void setPromptCount(int promptCount) {
        SharedPreferences.Editor editor = mPref.edit();
        editor.putInt(KEY_PROMPT_COUNT, promptCount);
        editor.apply();
    }

    public void setGcmId(String token) {
        SharedPreferences.Editor editor = mPref.edit();
        editor.putString(KEY_GCM_ID, token);
        editor.apply();
    }

    public String getGcmId() {
        return mPref.getString(KEY_GCM_ID, null);
    }

    public void setIsNewUser(boolean isNewUser) {
        mPref.edit().putBoolean(KEY_IS_NEW_USER, isNewUser).apply();
    }

    public boolean isNewUser() {
        return mPref.getBoolean(KEY_IS_NEW_USER, false);
    }

    public void incrementSessionCount() {
        int newCount = mPref.getInt(KEY_SESSION_COUNT, 0);
        mPref.edit().putInt(KEY_SESSION_COUNT, ++newCount).apply();
    }

    public void resetSessionCount() {
        mPref.edit().putInt(KEY_SESSION_COUNT, 0).apply();
    }

    public int getSessionCount() {
        return mPref.getInt(KEY_SESSION_COUNT, 0);
    }

    public void setLastSessionTimestamp(long timestamp) {
        mPref.edit().putLong(KEY_LAST_SESSION_TIMESTAMP, timestamp).apply();
    }

    public long getLastSessionTimestamp() {
        return mPref.getLong(KEY_LAST_SESSION_TIMESTAMP, 0);
    }
}
