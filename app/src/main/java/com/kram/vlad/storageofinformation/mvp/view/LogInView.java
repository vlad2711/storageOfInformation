package com.kram.vlad.storageofinformation.mvp.view;

import android.content.Context;

import com.kram.vlad.storageofinformation.models.LogInModel;
import com.kram.vlad.storageofinformation.mvp.presenters.base.MvpPresenter;

/**
 * Created by vlad on 13.11.2017.
 */

public interface LogInView {
    interface View extends MvpView {

       // get text from field
        String getTextEmail();
        String getTextPassword();

        void clearAll();
        void showMessage(int messageResId);
        void openSignUp();
        // go to next screen
        void next();
        void nextFromPreferences();

        // close screen
        void close();

    }

    interface Presenter extends MvpPresenter<View> {
        void onLogIn(Context context, LogInModel logInModel);
        void onSignUp(Context context);
        void checkLogin(Context context);
    }
}
