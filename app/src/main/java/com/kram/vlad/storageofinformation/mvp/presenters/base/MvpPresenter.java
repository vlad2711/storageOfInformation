package com.kram.vlad.storageofinformation.mvp.presenters.base;

import com.kram.vlad.storageofinformation.mvp.view.MvpView;

public interface MvpPresenter<V extends MvpView> {
 
    void attachView(V mvpView);
 
    void viewIsReady();
 
    void detachView();
 
    void destroy();
}