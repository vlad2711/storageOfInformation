package com.kram.vlad.storageofinformation.mvp.presenters.base;

import com.kram.vlad.storageofinformation.mvp.view.MvpView;

/**
 * Created by vlad on 13.11.2017.
 */

public class BasePresenter <T extends MvpView> implements MvpPresenter<T> {
    private T view;

    @Override
    public void attachView(T mvpView) {
        view = mvpView;
    }

    @Override
    public void viewIsReady() {

    }

    @Override
    public void detachView() {
        view = null;
    }

    public T getView() {
        return view;
    }

    protected boolean isViewAttached() {
        return view != null;
    }

    @Override
    public void destroy() {

    }
}
