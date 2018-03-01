package com.kram.vlad.storageofinformation.mvp.presenters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.kram.vlad.storageofinformation.Constants;
import com.kram.vlad.storageofinformation.R;
import com.kram.vlad.storageofinformation.Utils;
import com.kram.vlad.storageofinformation.callbacks.LogInCallback;
import com.kram.vlad.storageofinformation.models.LogInModel;
import com.kram.vlad.storageofinformation.mvp.model.files.SharedPreferencesReader;
import com.kram.vlad.storageofinformation.mvp.model.firebase.FirebaseHelper;
import com.kram.vlad.storageofinformation.mvp.model.sqlite.helpers.SQLiteHelper;
import com.kram.vlad.storageofinformation.mvp.model.web.LogInAPI;
import com.kram.vlad.storageofinformation.mvp.model.web.pojo.RESTModels;
import com.kram.vlad.storageofinformation.mvp.presenters.base.BasePresenter;
import com.kram.vlad.storageofinformation.mvp.view.LogInView;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by vlad on 13.11.2017.
 * Presenter for LogInActivity
 */

public class LogInPresenter extends BasePresenter<LogInView.View> implements LogInView.Presenter, LogInCallback,
        Callback<RESTModels.LogInModelResponse> {

    private static final String TAG = LogInPresenter.class.getSimpleName();

    /**
     * Called when user try to login. Check user registration in database
     * @param context of current Activity
     * @param logInModel use login data
     */
    @Override
    public void onLogIn(Context context, LogInModel logInModel) {
        switch (Utils.sCode) { //get current database mode
            case Constants.SQL_MODE:
                LogInModel model = new SQLiteHelper(context).logIn(logInModel);
                if (model != null) {
                    getView().next();
                } else {
                    getView().showMessage(R.string.wrong_password_message);
                }
                break;
            case Constants.FIREBASE_MODE:
                new FirebaseHelper().getUser(logInModel, this);
                break;
            case Constants.REST_MODE:
                LogInAPI.Factory.create().login(logInModel.getEmail(), logInModel.getPassword()).enqueue(this);
                break;
        }
    }

    /**
     * User want to open SignUpActivity
     */
    @Override
    public void onSignUp() {
        getView().openSignUp();
    }

    /**
     * Check is user already login
     * @param context of current Activity
     */
    @Override
    public void checkLogin(Context context) {
        if(new SharedPreferencesReader().getIsLoginFromSharedPreferences(context)){
            getView().nextFromPreferences();
        }
    }

    /**
     * Check user registration
     * @param model user login data
     */
    @Override
    public void onLogInDataDownload(LogInModel model) {
        if(model != null){
            getView().next();
        } else {
            getView().showMessage(R.string.wrong_password_message);
        }
    }

    /**
     * Check user registration in database by REST API
     */
    @Override
    public void onResponse(@NonNull Call<RESTModels.LogInModelResponse> call, @NonNull Response<RESTModels.LogInModelResponse> response) {
        Log.d(TAG, String.valueOf(response));
        if(Objects.equals(response.body().getResult(), "OK")){
            onLogInDataDownload(response.body().getLogInModel());
        }
    }

    @Override
    public void onFailure(Call<RESTModels.LogInModelResponse> call, @NonNull Throwable t) {
        t.printStackTrace();
    }
}
