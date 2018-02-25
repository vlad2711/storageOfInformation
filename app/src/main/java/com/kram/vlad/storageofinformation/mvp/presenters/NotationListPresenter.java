package com.kram.vlad.storageofinformation.mvp.presenters;

import android.content.Context;
import android.content.Intent;

import com.google.gson.Gson;
import com.kram.vlad.storageofinformation.Constants;
import com.kram.vlad.storageofinformation.Utils;
import com.kram.vlad.storageofinformation.callbacks.NotationsDownloadedCallback;
import com.kram.vlad.storageofinformation.models.LogInModel;
import com.kram.vlad.storageofinformation.mvp.model.files.SharedPreferencesReader;
import com.kram.vlad.storageofinformation.mvp.model.firebase.FirebaseHelper;
import com.kram.vlad.storageofinformation.mvp.model.sqlite.helpers.SQLiteHelper;
import com.kram.vlad.storageofinformation.mvp.model.web.GetNotationsAPI;
import com.kram.vlad.storageofinformation.mvp.model.web.pojo.RESTModels;
import com.kram.vlad.storageofinformation.mvp.presenters.base.BasePresenter;
import com.kram.vlad.storageofinformation.mvp.view.NotationListView;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by vlad on 14.11.2017.
 */

public class NotationListPresenter extends BasePresenter<NotationListView.View> implements NotationListView.Presenter, Callback<RESTModels.NotationResponse> {

    private  NotationsDownloadedCallback notationsDownloadedCallback;

    NotationListPresenter(NotationsDownloadedCallback notationsDownloadedCallback){
        this.notationsDownloadedCallback = notationsDownloadedCallback;
    }

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
                                  NotationsDownloadedCallback notationsDownloadedCallback, int start, int end) {
        switch (Utils.sCode) {
            case Constants.SQL_MODE:
                new SQLiteHelper(context).downloadNotations(logInModel, notationsDownloadedCallback, start, end);
                break;
            case Constants.FIREBASE_MODE:
                new FirebaseHelper().getDataInRange(logInModel, notationsDownloadedCallback, start, end);
                break;
            case Constants.REST_MODE:
                GetNotationsAPI.Factory.create().getNotations(logInModel.getEmail(), logInModel.getPassword(), start, end).enqueue(this);
                break;
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

    @Override
    public void onResponse(Call<RESTModels.NotationResponse> call, Response<RESTModels.NotationResponse> response) {
        for (int i = 0; i < response.body().getResponse().size(); i++) {
            Utils.sNotations.add(response.body().getResponse().get(i).getNotation());
        }

        notationsDownloadedCallback.onNotationsDownLoaded();
    }

    @Override
    public void onFailure(Call<RESTModels.NotationResponse> call, Throwable t) {
        t.printStackTrace();
    }
}
