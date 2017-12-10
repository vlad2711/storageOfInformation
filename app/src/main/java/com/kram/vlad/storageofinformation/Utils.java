package com.kram.vlad.storageofinformation;

import java.util.ArrayList;

/**
 * Created by vlad on 07.11.17.
 */

public class Utils {
    public static ArrayList<String> sNotations = new ArrayList<>();
    public static boolean isSQL = false;

    public static String EncodeEmail(String string) {
        return string.replace(".", ",");
    }

    public static String DecodeEmail(String string) {
        return string.replace(",", ".");
    }
}
