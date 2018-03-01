package com.kram.vlad.storageofinformation.models;

/**
 * Created by vlad on 05.11.17.
 * Notations data model
 */

public class NotationsModel {
    private LogInModel mLogInModel;
    private String mNotations;
    private long mNumber;

    public long getNumber() {
        return mNumber;
    }


    /**
     * @param logInModel login data
     * @param notations notation text
     * @param number id of notation
     */
    public NotationsModel(LogInModel logInModel, String notations, long number) {
        mLogInModel = logInModel;
        mNotations = notations;
        mNumber = number;
    }

    public LogInModel getLogInModel() {
        return mLogInModel;
    }

    public String getNotations() {
        return mNotations;
    }
}
