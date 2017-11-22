package com.kram.vlad.storageofinformation.mvp.presenters;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.google.gson.Gson;
import com.kram.vlad.storageofinformation.Utils;
import com.kram.vlad.storageofinformation.callbacks.NotationsDownloadedCallack;
import com.kram.vlad.storageofinformation.models.LogInModel;
import com.kram.vlad.storageofinformation.mvp.model.files.SharedPreferencesReader;
import com.kram.vlad.storageofinformation.mvp.model.firebase.FirebaseHelper;
import com.kram.vlad.storageofinformation.mvp.model.sqlite.helpers.SQLiteHelper;
import com.kram.vlad.storageofinformation.mvp.presenters.base.BasePresenter;
import com.kram.vlad.storageofinformation.mvp.view.NotationListView;

/**
 * Created by vlad on 14.11.2017.
 */

public class NotationListPresenter extends BasePresenter<NotationListView.View> implements NotationListView.Presenter {

    @Override
    public void pushDataToPreferences(Context context, LogInModel logInModel) {
        new SharedPreferencesReader().putLoginModel(context, logInModel);
    }

    @Override
    public void pushIsLoginToPreferences(Context context, boolean isLogin) {
        new SharedPreferencesReader().putIsLogin(context, isLogin);
     }


    @Override
    public void downloadNotations(Context context, LogInModel logInModel,
                                  NotationsDownloadedCallack notationsDownloadedCallack, int start, int end) {
        if(Utils.isSQL){
            new SQLiteHelper(context).downloadNotations(logInModel,notationsDownloadedCallack, start, end);
        }else {
            new FirebaseHelper().getDataInRange(logInModel, notationsDownloadedCallack, start, end);
        }
    }

    @Override
    public LogInModel getLogIn(Intent intent, Context context) {
        LogInModel logInModel;
        if(intent.hasExtra("login")) {
             logInModel = new Gson().fromJson(intent.getStringExtra("login"), LogInModel.class);
        } else {
            logInModel = getLoginFromPreferences(context);
        }

        pushDataToPreferences(context, logInModel);
        return logInModel;
    }

    @Override
    public LogInModel getLoginFromPreferences(Context context) {
        return new SharedPreferencesReader().getLoginModel(context);
    }
}
