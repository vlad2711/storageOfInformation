package com.kram.vlad.storageofinformation.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Button;

import com.google.gson.Gson;
import com.kram.vlad.storageofinformation.R;
import com.kram.vlad.storageofinformation.Utils;
import com.kram.vlad.storageofinformation.adapters.NotationRecyclerViewAdapter;
import com.kram.vlad.storageofinformation.callbacks.NotationsDownloadedCallack;
import com.kram.vlad.storageofinformation.models.LogInModel;
import com.kram.vlad.storageofinformation.mvp.presenters.NotationListPresenter;
import com.kram.vlad.storageofinformation.mvp.view.NotationListView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class NotationListActivity extends AppCompatActivity implements NotationListView.View, NotationsDownloadedCallack {

    public static final String TAG = NotationListActivity.class.getSimpleName();

    private NotationRecyclerViewAdapter mNotationRecyclerViewAdapter;
    private LogInModel mLogInModel;
    private NotationListPresenter mNotationListPresenter;

    @BindView(R.id.logOut) Button mLogOut;
    @BindView(R.id.floatingActionButton2) FloatingActionButton mFloatingActionButton;
    @BindView(R.id.recyclerView) RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hello);
        ButterKnife.bind(this);
        presenterInit();
        getLogInModelFromIntent();

        Utils.sNotations = new ArrayList<>();
        initializeRecyclerView();

        Log.d(TAG, String.valueOf(mLogInModel));

    }

    private void presenterInit(){
        mNotationListPresenter = new NotationListPresenter();
        mNotationListPresenter.attachView(this);
        mNotationListPresenter.viewIsReady();
    }

    @OnClick(R.id.logOut)
    public void onViewClicked() {
        mNotationListPresenter.pushIsLoginToPreferences(this, false);
        close();
    }

    @OnClick(R.id.floatingActionButton2)
    public void onActionButtonClicked() {
        next();
    }

    public void getLogInModelFromIntent() {
        mLogInModel = mNotationListPresenter.getLogIn(getIntent(), this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mNotationListPresenter.detachView();
        if (isFinishing()) {
            mNotationListPresenter.destroy();
        }
    }

    @Override
    public void next() {
        Intent i = new Intent(NotationListActivity.this, AddNotationsActivity.class);
        i.putExtra("login", new Gson().toJson(mLogInModel));

        startActivity(i);
        finish();
    }

    @Override
    public void close() {
        finish();
    }

    private void initializeRecyclerView() {
        mNotationListPresenter.downloadNotations(this,mLogInModel, this, 1, 10);
        mNotationRecyclerViewAdapter = new NotationRecyclerViewAdapter(this,mLogInModel,
                1, 10, this,
                mNotationListPresenter);

        mRecyclerView.setAdapter(mNotationRecyclerViewAdapter);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(llm);
    }

    @Override
    public void onNotationsDownLoaded() {
        mRecyclerView.getAdapter().notifyDataSetChanged();
    }
}
