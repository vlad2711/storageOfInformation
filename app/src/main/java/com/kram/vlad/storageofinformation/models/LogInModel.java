package com.kram.vlad.storageofinformation.models;

/**
 * Created by vlad on 23.10.17.
 * Login data Model
 */

public class LogInModel {
    private String mEmail;
    private String mPassword;

    public LogInModel(String email, String password) {
        mEmail = email;
        mPassword = password;
    }

    public LogInModel() {
    }

    public String getEmail() {
        return mEmail;
    }

    public void setEmail(String email) {
        mEmail = email;
    }

    public String getPassword() {
        return mPassword;
    }

    public void setPassword(String password) {
        mPassword = password;
    }
}
