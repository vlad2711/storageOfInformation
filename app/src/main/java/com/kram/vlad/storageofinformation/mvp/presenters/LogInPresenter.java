package com.kram.vlad.storageofinformation.mvp.presenters;

import android.content.Context;

import com.kram.vlad.storageofinformation.R;
import com.kram.vlad.storageofinformation.Utils;
import com.kram.vlad.storageofinformation.callbacks.LogInCallback;
import com.kram.vlad.storageofinformation.models.LogInModel;
import com.kram.vlad.storageofinformation.mvp.model.files.SharedPreferencesReader;
import com.kram.vlad.storageofinformation.mvp.model.firebase.FirebaseHelper;
import com.kram.vlad.storageofinformation.mvp.model.sqlite.helpers.SQLiteHelper;
import com.kram.vlad.storageofinformation.mvp.presenters.base.BasePresenter;
import com.kram.vlad.storageofinformation.mvp.view.LogInView;

/**
 * Created by vlad on 13.11.2017.
 */

public class LogInPresenter extends BasePresenter<LogInView.View> implements LogInView.Presenter, LogInCallback{

    public static final String TAG = LogInPresenter.class.getSimpleName();

    @Override
    public void onLogIn(Context context, LogInModel logInModel) {
        if(Utils.isSQL){
            LogInModel model = new SQLiteHelper(context).logIn(logInModel);
            if(model != null){
                getView().next();
            } else {
                getView().showMessage(R.string.wrong_password_message);
            }
        } else {
            new FirebaseHelper().getUser(logInModel, this);
        }
    }

    @Override
    public void onSignUp(Context context) {
        getView().openSignUp();
    }

    @Override
    public void checkLogin(Context context) {
        if(new SharedPreferencesReader().getIsLoginFromSharedPreferences(context)){
            getView().nextFromPreferences();
        }
    }

    @Override
    public void onLogInDataDownload(LogInModel model) {
        if(model != null){
            getView().next();
        } else {
            getView().showMessage(R.string.wrong_password_message);
        }
    }
}
