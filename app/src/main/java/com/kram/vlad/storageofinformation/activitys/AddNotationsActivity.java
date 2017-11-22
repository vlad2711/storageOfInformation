package com.kram.vlad.storageofinformation.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.kram.vlad.storageofinformation.R;
import com.kram.vlad.storageofinformation.callbacks.NotationCountCallback;
import com.kram.vlad.storageofinformation.models.LogInModel;
import com.kram.vlad.storageofinformation.models.NotationsModel;
import com.kram.vlad.storageofinformation.mvp.presenters.AddNotationPresenter;
import com.kram.vlad.storageofinformation.mvp.view.AddNotationView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AddNotationsActivity extends AppCompatActivity implements AddNotationView.View, NotationCountCallback{

    private LogInModel mLogInModel;
    private AddNotationPresenter mAddNotationPresenter;

    @BindView(R.id.notations) EditText mNotations;
    @BindView(R.id.add) Button mAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_notations);
        ButterKnife.bind(this);
        presenterInit();
        getLogInModelFromIntent();

    }

    private void presenterInit(){
        mAddNotationPresenter = new AddNotationPresenter();
        mAddNotationPresenter.attachView(this);
        mAddNotationPresenter.viewIsReady();
    }

    private void getLogInModelFromIntent() {
        mLogInModel = new Gson().fromJson(getIntent().getStringExtra("login"), LogInModel.class);
    }

    @OnClick(R.id.add)
    public void onViewClicked() {
        if(mLogInModel != null)
        mAddNotationPresenter.getNotationCount(mLogInModel, this);
    }

    @Override
    public String getTextNotation() {
        return mNotations.getText().toString();
    }

    @Override
    public void clearAll() {
        mNotations.setText("");
    }

    @Override
    public void showMessage(int messageResId) {
        Toast.makeText(this, messageResId, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void next() {
        Intent i = new Intent(AddNotationsActivity.this, NotationListActivity.class);
        i.putExtra("login", new Gson().toJson(mLogInModel));
        startActivity(i);
    }

    @Override
    public void close() {
        finish();
    }

    @Override
    public void onNotationCount(long count) {
        if(mLogInModel != null) mAddNotationPresenter.onAdd(this,
                new NotationsModel(mLogInModel, mNotations.getText().toString(),
                    (count + 1)));

    }
}
