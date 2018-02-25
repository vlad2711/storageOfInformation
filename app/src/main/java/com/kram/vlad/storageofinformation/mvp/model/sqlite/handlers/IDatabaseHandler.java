package com.kram.vlad.storageofinformation.mvp.model.sqlite.handlers;

import com.kram.vlad.storageofinformation.callbacks.NotationsDownloadedCallback;
import com.kram.vlad.storageofinformation.models.LogInModel;
import com.kram.vlad.storageofinformation.models.NotationsModel;
import com.kram.vlad.storageofinformation.models.SignUpModel;

import java.util.ArrayList;

/**
 * Created by vlad on 23.10.17.
 */

public interface IDatabaseHandler {
     void signUp(SignUpModel signUp);
     void addNotations(LogInModel logInModel, NotationsModel notationsModel);
     void initIsland(ArrayList<String> islands);
     void downloadNotations(LogInModel logInModel, NotationsDownloadedCallback notationsDownloadedCallback,
                            int start, int finish);
     LogInModel logIn(LogInModel logIn);
}
