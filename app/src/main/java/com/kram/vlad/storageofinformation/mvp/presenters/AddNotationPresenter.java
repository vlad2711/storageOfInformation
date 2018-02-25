package com.kram.vlad.storageofinformation.mvp.presenters;

import android.content.Context;

import com.kram.vlad.storageofinformation.Constants;
import com.kram.vlad.storageofinformation.R;
import com.kram.vlad.storageofinformation.Utils;
import com.kram.vlad.storageofinformation.callbacks.NotationCountCallback;
import com.kram.vlad.storageofinformation.models.LogInModel;
import com.kram.vlad.storageofinformation.models.NotationsModel;
import com.kram.vlad.storageofinformation.mvp.model.firebase.FirebaseHelper;
import com.kram.vlad.storageofinformation.mvp.model.sqlite.helpers.SQLiteHelper;
import com.kram.vlad.storageofinformation.mvp.model.web.AddNotationsAPI;
import com.kram.vlad.storageofinformation.mvp.model.web.pojo.RESTModels;
import com.kram.vlad.storageofinformation.mvp.presenters.base.BasePresenter;
import com.kram.vlad.storageofinformation.mvp.view.AddNotationView;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by vlad on 15.11.2017.
 */

public class AddNotationPresenter extends BasePresenter<AddNotationView.View> implements AddNotationView.Presenter, Callback<RESTModels.NotationAddResponse> {

    @Override
    public void onAdd(Context context, NotationsModel notationsModel) {
        switch (Utils.sCode){
            case Constants.FIREBASE_MODE:
                new FirebaseHelper().addNotation(notationsModel);
                break;
            case Constants.SQL_MODE:
                new SQLiteHelper(context).addNotations(notationsModel.getLogInModel(), notationsModel);
                break;
            case Constants.REST_MODE:
                AddNotationsAPI.Factory.create().addNotations(notationsModel.getLogInModel().getEmail(),
                        notationsModel.getLogInModel().getPassword(),
                        notationsModel.getNotations()).enqueue(this);
        }
        getView().next();
        getView().close();
    }

    @Override
    public void getNotationCount(LogInModel logInModel, NotationCountCallback notationCountCallback) {
        new FirebaseHelper().getNotationCount(logInModel, notationCountCallback);
    }

    @Override
    public void onResponse(Call<RESTModels.NotationAddResponse> call, Response<RESTModels.NotationAddResponse> response) {
        if(!Objects.equals("OK", response.body().getResult())){
            getView().showMessage(R.string.error);
        }
    }

    @Override
    public void onFailure(Call<RESTModels.NotationAddResponse> call, Throwable t) {
        t.printStackTrace();
    }
}
