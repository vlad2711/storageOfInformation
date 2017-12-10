package com.kram.vlad.storageofinformation.mvp.presenters;

import android.content.Context;

import com.kram.vlad.storageofinformation.R;
import com.kram.vlad.storageofinformation.Utils;
import com.kram.vlad.storageofinformation.models.SignUpModel;
import com.kram.vlad.storageofinformation.mvp.model.files.AssetReader;
import com.kram.vlad.storageofinformation.mvp.model.firebase.FirebaseHelper;
import com.kram.vlad.storageofinformation.mvp.model.sqlite.helpers.SQLiteHelper;
import com.kram.vlad.storageofinformation.mvp.presenters.base.BasePresenter;
import com.kram.vlad.storageofinformation.mvp.view.SignUpView;

import java.util.ArrayList;
import java.util.Objects;

/**
 * Created by vlad on 13.11.2017.
 */

public class SignUpPresenter extends BasePresenter<SignUpView.View> implements SignUpView.Presenter{

    private SQLiteHelper mSQLiteHelper;
    private FirebaseHelper mFirebaseHelper;

    @Override
    public void viewIsReady() {
        super.viewIsReady();

    }

    @Override
    public void onAdd(Context context, SignUpModel signUpModel) {
        if(!Objects.equals(signUpModel.getLogInModel().getEmail(), "")
                && !Objects.equals(signUpModel.getLogInModel().getPassword(), "")) {
            if (Utils.isSQL) {
                mSQLiteHelper = new SQLiteHelper(context);
                mSQLiteHelper.signUp(signUpModel);
            } else {
                mFirebaseHelper = new FirebaseHelper();
                mFirebaseHelper.addNewUser(signUpModel);
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

}
