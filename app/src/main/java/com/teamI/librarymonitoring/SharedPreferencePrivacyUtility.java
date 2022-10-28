package com.teamI.librarymonitoring;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferencePrivacyUtility {

    private static final String prefFileName = "LMPreferences";
    private static final String privacyConsentKey = "PrivacyConsent";

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
}
