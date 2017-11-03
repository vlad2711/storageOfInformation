package com.kram.vlad.storageofinformation.models;

/**
 * Created by vlad on 24.10.17.
 */

public class IslandModel {
    private int mIslandId;
    private String mIslandName;

    public int getIslandId() {
        return mIslandId;
    }

    public void setIslandId(int islandId) {
        mIslandId = islandId;
    }

    public String getIslandName() {
        return mIslandName;
    }

    public void setIslandName(String islandName) {
        mIslandName = islandName;
    }

    public IslandModel(int islandId, String islandName) {
        mIslandId = islandId;
        mIslandName = islandName;
    }
}
