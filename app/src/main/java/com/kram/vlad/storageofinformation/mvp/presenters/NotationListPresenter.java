package com.kram.vlad.storageofinformation.mvp.presenters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.util.Log;

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
 * Presenter for NotationListActivity
 */

public class NotationListPresenter extends BasePresenter<NotationListView.View> implements NotationListView.Presenter, Callback<RESTModels.NotationResponse> {

    private static final String TAG = NotationListPresenter.class.getSimpleName();

    private  NotationsDownloadedCallback notationsDownloadedCallback;

    public NotationListPresenter(NotationsDownloadedCallback notationsDownloadedCallback){
        this.notationsDownloadedCallback = notationsDownloadedCallback;
    }

    /**
     * Put user login data to preferences
     * @param logInModel user login data
     */
    @Override
    public void pushDataToPreferences(Context context, LogInModel logInModel) {
        new SharedPreferencesReader().putLoginModel(context, logInModel);
    }

    /**
     * Remember user
     * @param isLogin If user login put true else false
     */
    @Override
    public void pushIsLoginToPreferences(Context context, boolean isLogin) {
        new SharedPreferencesReader().putIsLogin(context, isLogin);
    }

    /**
     * Download notations from database in range between start and end param
     * @param logInModel user login data
     * @param notationsDownloadedCallback called when data already downloaded
     * @param start All notations id greater then start
     * @param end All notations id lower then start
     */
    @Override
    public void downloadNotations(Context context, LogInModel logInModel,
                                  NotationsDownloadedCallback notationsDownloadedCallback, int start, int end) {
        switch (Utils.sCode) {
            case Constants.SQL_MODE:
                new SQLiteHelper(context).downloadNotations(logInModel, start, end);
                break;
            case Constants.FIREBASE_MODE:
                new FirebaseHelper().getDataInRange(logInModel, notationsDownloadedCallback, start, end);
                break;
            case Constants.REST_MODE:
                Log.d(TAG, start + " " + end);
                GetNotationsAPI.Factory.create().getNotations(logInModel.getEmail(), logInModel.getPassword(), 0, 10).enqueue(this);
                break;
        }
    }

    /**
     * Get user login data
     * @param intent getIntent() in Activity
     * @return user login data
     */
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

    /**
     * Return user login data from Preferences
     * @return user login data
     */
    @Override
    public LogInModel getLoginFromPreferences(Context context) {
        return new SharedPreferencesReader().getLoginModel(context);
    }

    /**
     * Get user notations by REST API
     */
    @Override
    public void onResponse(@NonNull Call<RESTModels.NotationResponse> call, @NonNull Response<RESTModels.NotationResponse> response) {

        for (int i = 0; i < response.body().getResponse().size(); i++) {
            if(response.body().getResponse().get(i).getId() < Utils.sNotations.size()) {
                Utils.sNotations.set(response.body().getResponse().get(i).getId(), response.body().getResponse().get(i).getNotation());
            } else {
                Utils.sNotations.add(response.body().getResponse().get(i).getNotation());
            }
        }

        notationsDownloadedCallback.onNotationsDownLoaded();
    }

    @Override
    public void onFailure(@NonNull Call<RESTModels.NotationResponse> call, @NonNull Throwable t) {
        t.printStackTrace();
    }
}
