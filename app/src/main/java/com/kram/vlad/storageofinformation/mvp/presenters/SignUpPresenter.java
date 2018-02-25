package com.kram.vlad.storageofinformation.mvp.presenters;

import android.content.Context;

import com.kram.vlad.storageofinformation.Constants;
import com.kram.vlad.storageofinformation.R;
import com.kram.vlad.storageofinformation.Utils;
import com.kram.vlad.storageofinformation.models.SignUpModel;
import com.kram.vlad.storageofinformation.mvp.model.files.AssetReader;
import com.kram.vlad.storageofinformation.mvp.model.firebase.FirebaseHelper;
import com.kram.vlad.storageofinformation.mvp.model.sqlite.helpers.SQLiteHelper;
import com.kram.vlad.storageofinformation.mvp.model.web.SignUpAPI;
import com.kram.vlad.storageofinformation.mvp.model.web.pojo.RESTModels;
import com.kram.vlad.storageofinformation.mvp.presenters.base.BasePresenter;
import com.kram.vlad.storageofinformation.mvp.view.SignUpView;

import java.util.ArrayList;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by vlad on 13.11.2017.
 */

public class SignUpPresenter extends BasePresenter<SignUpView.View> implements SignUpView.Presenter, Callback<RESTModels.SignUpModelResponse> {

    @Override
    public void viewIsReady() {
        super.viewIsReady();

    }

    @Override
    public void onAdd(Context context, SignUpModel signUpModel) {
        if(!Objects.equals(signUpModel.getLogInModel().getEmail(), "")
                && !Objects.equals(signUpModel.getLogInModel().getPassword(), "")) {
            switch (Utils.sCode) {
                case Constants.SQL_MODE:
                    new SQLiteHelper(context).signUp(signUpModel);
                    break;
                case Constants.FIREBASE_MODE:
                    new FirebaseHelper().addNewUser(signUpModel);
                    break;
                case Constants.REST_MODE:
                    SignUpAPI.Factory.create().signUp(signUpModel.getLogInModel().getEmail(),
                                                      signUpModel.getLogInModel().getPassword(),
                                                      signUpModel.getName()).enqueue(this);
                    break;

            }
        } else {
            getView().showMessage(R.string.empty_password_message);
        }
        getView().next();
        getView().close();
    }

    @Override
    public void onLogIn() {
        getView().next();
        getView().close();
    }

    @Override
    public ArrayList<String> getSpinnerData(Context context) {
        return new AssetReader().getIslandArrayList(context);
    }

    @Override
    public void onResponse(Call<RESTModels.SignUpModelResponse> call, Response<RESTModels.SignUpModelResponse> response) {
        if(Objects.equals(response.body().getResult(), "OK")){
            getView().next();
        } else {
            getView().showMessage(R.string.account_already_exist);
        }
    }

    @Override
    public void onFailure(Call<RESTModels.SignUpModelResponse> call, Throwable t) {
        t.printStackTrace();
    }
}
