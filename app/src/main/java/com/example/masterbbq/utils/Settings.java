package com.example.masterbbq.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.masterbbq.App;

public class Settings {

    public static final SharedPreferences PREFERENCES = App.getContext().getSharedPreferences(Settings.PREF, Context.MODE_PRIVATE);
    public static final SharedPreferences.Editor PREFERENCES_EDITOR = PREFERENCES.edit();

    public static final String MODEL = android.os.Build.MODEL;
    public static final String PREF = "new_app_pref";
    public static final String LOGIN = "LOGIN";
    public static final String USERNAME = "USERNAME";
    public static final String USER_TOKEN = "USER_TOKEN";
    public static final String BASE_URL = "http://190.47.229.40";
    public static final String BASE_PORT = ":8000";
    public static final String API = "/api";
    public static final String SERVICE_LOGIN = "/user/login/";
}