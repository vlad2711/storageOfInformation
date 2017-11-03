package com.kram.vlad.storageofinformation.sqlite.handlers;

import com.kram.vlad.storageofinformation.models.IslandModel;
import com.kram.vlad.storageofinformation.models.LogInModel;
import com.kram.vlad.storageofinformation.models.SignUpModel;

import java.util.ArrayList;

/**
 * Created by vlad on 23.10.17.
 */

public interface IDatabaseHandler {
     void signUp(SignUpModel signUp);
     void initIsland(ArrayList<String> islands);
     SignUpModel logIn(LogInModel logIn);
}
