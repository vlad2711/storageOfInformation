package com.kram.vlad.storageofinformation.mvp.view;

import android.content.Context;
import android.content.Intent;
import com.kram.vlad.storageofinformation.callbacks.NotationsDownloadedCallback;
import com.kram.vlad.storageofinformation.models.LogInModel;
import com.kram.vlad.storageofinformation.mvp.presenters.base.MvpPresenter;
import com.kram.vlad.storageofinformation.mvp.view.base.MvpView;

/**
 * Created by vlad on 14.11.2017.
 * View for NotationListActivity
 */

public interface NotationListView {

    interface View extends MvpView {
        void next();
        void close();
    }

    interface Presenter extends MvpPresenter<NotationListView.View> {

        void pushDataToPreferences(Context context, LogInModel logInModel);
        void pushIsLoginToPreferences(Context context, boolean isLogin);
        void downloadNotations(Context context, LogInModel logInModel, NotationsDownloadedCallback notationsDownloadedCallback,
                               int start, int end);
        LogInModel getLogIn(Intent intent, Context context);
        LogInModel getLoginFromPreferences(Context context);
    }
}
