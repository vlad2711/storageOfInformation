package com.kram.vlad.storageofinformation.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.google.gson.Gson;
import com.kram.vlad.storageofinformation.R;
import com.kram.vlad.storageofinformation.Utils;
import com.kram.vlad.storageofinformation.activitys.base.BaseActivity;
import com.kram.vlad.storageofinformation.adapters.NotationRecyclerViewAdapter;
import com.kram.vlad.storageofinformation.callbacks.NotationsDownloadedCallback;
import com.kram.vlad.storageofinformation.models.LogInModel;
import com.kram.vlad.storageofinformation.mvp.presenters.NotationListPresenter;
import com.kram.vlad.storageofinformation.mvp.view.NotationListView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Started from LogInActivity when user log in. Show user notations
 */
public class NotationListActivity extends BaseActivity implements NotationListView.View, NotationsDownloadedCallback {

    public static final String TAG = NotationListActivity.class.getSimpleName();

    /** Views */
    @BindView(R.id.toolbar4) Toolbar mToolbar;
    @BindView(R.id.floatingActionButton2) FloatingActionButton mFloatingActionButton;
    @BindView(R.id.recyclerView) RecyclerView mRecyclerView;
    /** Views */

    private LogInModel mLogInModel; // Login data
    private  NotationListPresenter mNotationListPresenter; //Current presenter

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_list);
        ButterKnife.bind(this);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("");

        presenterInit();
        getLogInModelFromIntent();

        Utils.sNotations = new ArrayList<>();
        initializeRecyclerView();

        Log.d(TAG, String.valueOf(mLogInModel));
    }

    /**
     * Presenter initializer
     */
    private void presenterInit() {
        mNotationListPresenter = new NotationListPresenter(this);
        mNotationListPresenter.attachView(this);
        mNotationListPresenter.viewIsReady();
    }

    /**
     * Call when user click at log out button
     */
    @OnClick(R.id.logOut)
    public void onLogOutClicked() {
        mNotationListPresenter.pushIsLoginToPreferences(this, false);
        close();
    }

    /**
     * Call when user want to add new notations
     */
    @OnClick(R.id.floatingActionButton2)
    public void onActionButtonClicked() {
        next();
    }

    /**
     * Get LogIn data from previous activity
     */
    public void getLogInModelFromIntent() {
        mLogInModel = mNotationListPresenter.getLogIn(getIntent(), this);
    }

    /**
     * You must detach view and destroy presenter
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        mNotationListPresenter.detachView();
        if (isFinishing()) {
            mNotationListPresenter.destroy();
        }
    }

    /**
     * Create next activity
     */
    @Override
    public void next() {
        Intent i = new Intent(NotationListActivity.this, AddNotationsActivity.class);
        i.putExtra("login", new Gson().toJson(mLogInModel));

        startActivity(i);
        finish();
    }

    /**
     * Destroy current activity
     */
    @Override
    public void close() {
        finish();
    }

    /**
     * Initialize list of notations
     */
    private void initializeRecyclerView() {
        mNotationListPresenter.downloadNotations(this, mLogInModel, this, 1, 10);
        NotationRecyclerViewAdapter notationRecyclerViewAdapter = new NotationRecyclerViewAdapter(this, mLogInModel,
                1, 10, this,
                mNotationListPresenter);

        mRecyclerView.setAdapter(notationRecyclerViewAdapter);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(llm);
    }

    /**
     * Callback called when app get notations from database.
     * Update recyclerView
     */
    @Override
    public void onNotationsDownLoaded() {
        mRecyclerView.getAdapter().notifyDataSetChanged();
    }
}
