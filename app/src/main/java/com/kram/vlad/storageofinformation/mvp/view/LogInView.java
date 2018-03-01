package com.kram.vlad.storageofinformation.mvp.view;

import android.content.Context;

import com.kram.vlad.storageofinformation.models.LogInModel;
import com.kram.vlad.storageofinformation.mvp.presenters.base.MvpPresenter;
import com.kram.vlad.storageofinformation.mvp.view.base.MvpView;

/**
 * Created by vlad on 13.11.2017.
 * View for LogInActivity
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
        void onSignUp();
        void checkLogin(Context context);
    }
}
