package com.teamI.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class Preferences {

    public static final String PREF_NAME = "PREFERENCES_APP";
    public static final int MODE = Context.MODE_PRIVATE;

    public static String writeInt(Context context, String key, int value) {
        getEditor(context).putInt(key, value).commit();

        return key;
    }

    public static String readString(Context context, String key) {
        return getPreferences(context).getString(key, "");
    }


    public static String writeString(Context context, String key, String value) {
        getEditor(context).putString(key, value).apply();
        return key;
    }

    public static int readInt(Context context, String key, int defValue) {
        return getPreferences(context).getInt(key, defValue);
    }

    private static SharedPreferences getPreferences(Context context) {
        Context context1 = context;
        return context1
                .getSharedPreferences(PREF_NAME, MODE);

    }

    private static Editor getEditor(Context context) {
        return getPreferences(context).edit();
    }

}