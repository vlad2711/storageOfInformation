package com.kram.vlad.storageofinformation.mvp.model.files;

import android.content.Context;
import android.content.SharedPreferences;

import com.kram.vlad.storageofinformation.Constants;
import com.kram.vlad.storageofinformation.models.LogInModel;

import static com.kram.vlad.storageofinformation.Constants.APP_PREFERENCES;

/**
 * Created by vlad on 13.11.2017.
 * This class read SharedPreferences
 */

public class SharedPreferencesReader {

    /**
     * Check is user login or not
     * @param context current Activity context
     * @return true if user already log in or false if not
     */
    public boolean getIsLoginFromSharedPreferences(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
        return sharedPreferences.contains("isLogin") && sharedPreferences.getBoolean("isLogin", false);
    }

    /**
     * Put boolean variable isLogin to SharedPreferences. If user already login - true, else false
     * @param context current Activity context
     * @param isLogin If user already login - true, else false
     */
    public void putIsLogin(Context context, boolean isLogin){
        SharedPreferences sharedPreferences = context.getSharedPreferences(Constants.APP_PREFERENCES, Context.MODE_PRIVATE);
        sharedPreferences.edit().putBoolean("isLogin", isLogin).apply();
    }

    /**
     * Put login data to SharedPreferences
     * @param context of current Activity
     * @param logInModel LogIn data that must be put at SharedPreferences
     */
    public void putLoginModel(Context context, LogInModel logInModel){
        SharedPreferences sharedPreferences = context.getSharedPreferences(Constants.APP_PREFERENCES, Context.MODE_PRIVATE);
        sharedPreferences.edit().putBoolean("isLogin", true).apply();
        sharedPreferences.edit().putString("email", logInModel.getEmail()).apply();
        sharedPreferences.edit().putString("password", logInModel.getPassword()).apply();
    }

    /**
     * @param context of current Activity
     * @return Log in data from SharedPreferences if user already login
     */
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
