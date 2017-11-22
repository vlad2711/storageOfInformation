package com.kram.vlad.storageofinformation.mvp.model.files;

import android.content.Context;
import android.content.SharedPreferences;

import com.kram.vlad.storageofinformation.Constants;
import com.kram.vlad.storageofinformation.models.LogInModel;

import static com.kram.vlad.storageofinformation.Constants.APP_PREFERENCES;

/**
 * Created by vlad on 13.11.2017.
 */

public class SharedPreferencesReader {

    public boolean getIsLoginFromSharedPreferences(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
        return sharedPreferences.contains("isLogin") && sharedPreferences.getBoolean("isLogin", false);
    }

    public void putIsLogin(Context context, boolean isLogin){
        SharedPreferences sharedPreferences = context.getSharedPreferences(Constants.APP_PREFERENCES, Context.MODE_PRIVATE);
        sharedPreferences.edit().putBoolean("isLogin", isLogin).apply();
    }

    public void putLoginModel(Context context, LogInModel logInModel){
        SharedPreferences sharedPreferences = context.getSharedPreferences(Constants.APP_PREFERENCES, Context.MODE_PRIVATE);
        sharedPreferences.edit().putBoolean("isLogin", true).apply();
        sharedPreferences.edit().putString("email", logInModel.getEmail()).apply();
        sharedPreferences.edit().putString("password", logInModel.getPassword()).apply();
    }

    public LogInModel getLoginModel(Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences(Constants.APP_PREFERENCES, Context.MODE_PRIVATE);
        LogInModel logInModel = null;
        if(sharedPreferences.contains("email")){
            logInModel = new LogInModel(sharedPreferences.getString("email",""),
                    sharedPreferences.getString("password",""));
        }

        return logInModel;
    }

}
