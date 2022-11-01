package com.teamI.librarymonitoring;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferenceUtility {

    private static final String prefFileName = "LMPreferences";
    private static final String privacyConsentKey = "PrivacyConsent";
    private static final String roleKey = "Role";

    public static boolean getPrivacyConsent(Context context){
        SharedPreferences sharedPref = context.getSharedPreferences(prefFileName, Context.MODE_PRIVATE);
        if(!sharedPref.contains(privacyConsentKey)){
            return false;
        }
        return sharedPref.getBoolean(privacyConsentKey, false);
    }

    public static void setPrivacyConsent(Context context, boolean bConsent){
        SharedPreferences sharedPref = context.getSharedPreferences(prefFileName, Context.MODE_PRIVATE);
        SharedPreferences.Editor ed = sharedPref.edit();
        ed.putBoolean(privacyConsentKey, bConsent);
        ed.apply();
    }

    public static void setIsStudent(Context context) {
        SharedPreferences sharedPref = context.getSharedPreferences(prefFileName, Context.MODE_PRIVATE);
        SharedPreferences.Editor ed = sharedPref.edit();
        ed.putString(roleKey, RoleEnum.Student.toString());
        ed.apply();
    }

    public static void setIsLibrarian(Context context) {
        SharedPreferences sharedPref = context.getSharedPreferences(prefFileName, Context.MODE_PRIVATE);
        SharedPreferences.Editor ed = sharedPref.edit();
        ed.putString(roleKey, RoleEnum.Librarian.toString());
        ed.apply();
    }

    public static RoleEnum getRole(Context context) {
        SharedPreferences sharedPref = context.getSharedPreferences(prefFileName, Context.MODE_PRIVATE);
        return RoleEnum.toRoleEnum(sharedPref.getString(roleKey, ""));
    }

    // deletes all shared preferences
    public static void clearAll(Context context){
        SharedPreferences sharedPref = context.getSharedPreferences(prefFileName, Context.MODE_PRIVATE);
        SharedPreferences.Editor ed = sharedPref.edit();
        ed.clear();
        ed.commit();
    }
}

