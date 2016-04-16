package com.example.alma.roommates.utils;

import android.content.Context;

/**
 * Created by alma on 16/04/2016.
 */
public class Functions {

    public static boolean isRTL(Context c) {
        if (isLanguage(c, "Hebrew") || isLanguage(c, "Arabic")) {
            return true;
        }
        return false;
    }

    public static boolean isLanguage(Context c, String lang) {
        return c.getResources().getConfiguration().locale.getDisplayLanguage().toLowerCase().equals(lang.toLowerCase());
    }
}
