package com.kram.vlad.storageofinformation.models;

/**
 * Created by vlad on 23.10.17.
 * SingUp model used when user sign up
 */

public class SignUpModel {

    private String mName;
    private LogInModel mLogInModel;

    public SignUpModel() {}

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public LogInModel getLogInModel() {
        return mLogInModel;
    }

    public void setLogInModel(LogInModel logInModel) {
        mLogInModel = logInModel;
    }

    public SignUpModel(String name, LogInModel logInModel) {
        mName = name;
        mLogInModel = logInModel;
    }
}
