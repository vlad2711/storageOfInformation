package com.kram.vlad.storageofinformation.callbacks;

import com.kram.vlad.storageofinformation.models.LogInModel;

/**
 * Created by vlad on 15.11.2017.
 * Callback, called when user login data downloaded
 */

public interface LogInCallback {
    void onLogInDataDownload(LogInModel model);
}
