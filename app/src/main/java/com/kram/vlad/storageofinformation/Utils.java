package com.kram.vlad.storageofinformation;

import java.util.ArrayList;

/**
 * Created by vlad on 07.11.17.
 */

public class Utils {
    public static ArrayList<String> sNotations = new ArrayList<>();
    public static int sCode = 2;

    public static String EncodeEmail(String string) {
        if(sCode == Constants.FIREBASE_MODE)return string.replace(".", ",");
        return string;
    }

    public static String DecodeEmail(String string) {
        if (sCode == Constants.FIREBASE_MODE) return string.replace(",", ".");
        return string;
    }
}
