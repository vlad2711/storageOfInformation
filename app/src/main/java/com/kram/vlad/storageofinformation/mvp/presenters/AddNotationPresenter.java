package com.kram.vlad.storageofinformation.mvp.presenters;

import android.content.Context;
import android.support.annotation.NonNull;

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
 * Presenter for AddNotationActivity
 */

public class AddNotationPresenter extends BasePresenter<AddNotationView.View> implements AddNotationView.Presenter, Callback<RESTModels.NotationAddResponse> {

    /**
     * This function add notation to database
     * @param context of current Activity
     * @param notationsModel notation data, that must be added
     */
    @Override
    public void onAdd(Context context, NotationsModel notationsModel) {
        switch (Utils.sCode){ // get database mode
            case Constants.FIREBASE_MODE:
                new FirebaseHelper().addNotation(notationsModel);
                break;
            case Constants.SQL_MODE:
                new SQLiteHelper(context).addNotations(notationsModel);
                break;
            case Constants.REST_MODE:
                AddNotationsAPI.Factory.create().addNotations(notationsModel.getLogInModel().getEmail(),
                        notationsModel.getLogInModel().getPassword(),
                        notationsModel.getNotations()).enqueue(this);
        }
        getView().next();
        getView().close();
    }

    /**
     * Return count of notations in database. ONLY FOR FIREBASE MODE!!!
     * @param logInModel user login data
     * @param notationCountCallback Callback, called when database return notation count
     */
    @Override
    public void getNotationCount(LogInModel logInModel, NotationCountCallback notationCountCallback) {
        if(Utils.sCode == Constants.FIREBASE_MODE) new FirebaseHelper().getNotationCount(logInModel, notationCountCallback);
    }

    /**
     * Response when app get response from rest api. ONLY FOR REST MODE
     */
    @Override
    public void onResponse(@NonNull Call<RESTModels.NotationAddResponse> call, @NonNull Response<RESTModels.NotationAddResponse> response) {
        if(!Objects.equals("OK", response.body().getResult())) getView().showMessage(R.string.error); //if user not registered show error message
    }

    @Override
    public void onFailure(@NonNull Call<RESTModels.NotationAddResponse> call, @NonNull Throwable t) {
        t.printStackTrace();
    }
}
