package com.example.administrator.cbkproject.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Administrator on 2016/6/20.
 */
public class SharedPreferenceUtil {

    public static void putBoolean(String fileName, int model, Context context, String key, boolean value) {
        SharedPreferences preferences = context.getSharedPreferences(fileName, model);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(key, value);
        editor.commit();
    }

    public static boolean getBoolean(String fileName, int model, Context context, String key, boolean defaultValue) {
        boolean value = defaultValue;
        SharedPreferences preferences = context.getSharedPreferences(fileName, model);
        value = preferences.getBoolean(key, defaultValue);
        System.out.println(value);
        return value;
    }
}
