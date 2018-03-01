package com.kram.vlad.storageofinformation.callbacks;

/**
 * Created by vlad on 19.11.2017.
 * Callback called when database return count of notations at database
 */

public interface NotationCountCallback {
    void onNotationCount(long count);
}
