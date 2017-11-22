package com.kram.vlad.storageofinformation.mvp.view;

import android.content.Context;

import com.kram.vlad.storageofinformation.models.SignUpModel;
import com.kram.vlad.storageofinformation.mvp.presenters.base.MvpPresenter;

import java.util.ArrayList;

public interface SignUpView {

    interface View extends MvpView {
        // get text from field
        String getTextName();
        String getTextEmail();
        String getTextPassword();

        void clearAll();
        void showMessage(int messageResId);

        // go to next screen
        void next();

        // close screen
        void close();
    }

    interface Presenter extends MvpPresenter<View> {
        void onAdd(Context context, SignUpModel signUpModel);
        void onLogIn();
        ArrayList<String> getSpinnerData(Context context);
    }
}