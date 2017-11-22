package com.kram.vlad.storageofinformation.mvp.view;

import android.content.Context;

import com.kram.vlad.storageofinformation.callbacks.NotationCountCallback;
import com.kram.vlad.storageofinformation.models.LogInModel;
import com.kram.vlad.storageofinformation.models.NotationsModel;
import com.kram.vlad.storageofinformation.mvp.presenters.base.MvpPresenter;

/**
 * Created by vlad on 15.11.2017.
 */

public interface AddNotationView {

    interface View extends MvpView {

        String getTextNotation();

        void clearAll();
        void showMessage(int messageResId);
        void next();
        void close();
    }

    interface Presenter extends MvpPresenter<AddNotationView.View> {
        void onAdd(Context context, NotationsModel notationsModel);
        void getNotationCount(LogInModel logInModel, NotationCountCallback notationCountCallback);
    }
}
