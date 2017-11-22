package com.kram.vlad.storageofinformation.mvp.model.files;

import android.content.Context;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * Created by vlad on 13.11.2017.
 */

public class AssetReader {

    public ArrayList<String> getIslandArrayList(Context context)  {
        String islands = null;
        try {
            islands = getTextFileInString(context);
        } catch (IOException e) {
            e.printStackTrace();
        }
        ArrayList<String> arrayList = new ArrayList<>();
        char islandsChar[] = islands.toCharArray();
        String temp = "";
        for (char anIslandsChar : islandsChar) {
            if (anIslandsChar != ',') {
                temp += anIslandsChar;
            } else {
                arrayList.add(temp);
                temp = "";
            }
        }
        return arrayList;
    }

    private String getTextFileInString(Context context) throws IOException {
        InputStream islands = context.getAssets().open("islands.txt");
        BufferedReader in = new BufferedReader(new InputStreamReader(islands, "UTF-8"));
        String str = in.readLine().replace("�", "");
        in.close();

        return str;

    }
}
