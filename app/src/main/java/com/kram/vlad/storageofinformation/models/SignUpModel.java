package com.kram.vlad.storageofinformation.models;

/**
 * Created by vlad on 23.10.17.
 */

public class SignUpModel {

    private String mName;
    private LogInModel mLogInModel;

    public SignUpModel() {
    }

    public int getIslandId() {
        return mIslandId;
    }

    public void setIslandId(int islandId) {
        mIslandId = islandId;
    }

    private int mIslandId;

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

    public SignUpModel(String name, LogInModel logInModel, int islandId) {
        mName = name;
        mLogInModel = logInModel;
        mIslandId = islandId;
    }
}
