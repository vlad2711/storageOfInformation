package com.kram.vlad.storageofinformation.mvp.presenters;

import android.content.Context;

import com.kram.vlad.storageofinformation.Utils;
import com.kram.vlad.storageofinformation.callbacks.NotationCountCallback;
import com.kram.vlad.storageofinformation.models.LogInModel;
import com.kram.vlad.storageofinformation.models.NotationsModel;
import com.kram.vlad.storageofinformation.mvp.model.firebase.FirebaseHelper;
import com.kram.vlad.storageofinformation.mvp.model.sqlite.helpers.SQLiteHelper;
import com.kram.vlad.storageofinformation.mvp.presenters.base.BasePresenter;
import com.kram.vlad.storageofinformation.mvp.view.AddNotationView;

/**
 * Created by vlad on 15.11.2017.
 */

public class AddNotationPresenter extends BasePresenter<AddNotationView.View> implements AddNotationView.Presenter{

    @Override
    public void onAdd(Context context, NotationsModel notationsModel) {
        if(!Utils.isSQL) {
            new FirebaseHelper().addNotation(notationsModel);
        } else {
            new SQLiteHelper(context).addNotations(notationsModel.getLogInModel(), notationsModel);
        }
        getView().next();
        getView().close();
    }

    @Override
    public void getNotationCount(LogInModel logInModel, NotationCountCallback notationCountCallback) {
        new FirebaseHelper().getNotationCount(logInModel, notationCountCallback);
    }

}
