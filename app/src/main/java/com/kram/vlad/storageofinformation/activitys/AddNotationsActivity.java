package com.kram.vlad.storageofinformation.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.kram.vlad.storageofinformation.Constants;
import com.kram.vlad.storageofinformation.R;
import com.kram.vlad.storageofinformation.Utils;
import com.kram.vlad.storageofinformation.callbacks.NotationCountCallback;
import com.kram.vlad.storageofinformation.models.LogInModel;
import com.kram.vlad.storageofinformation.models.NotationsModel;
import com.kram.vlad.storageofinformation.mvp.presenters.AddNotationPresenter;
import com.kram.vlad.storageofinformation.mvp.view.AddNotationView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * This activity started from NotationListActivity and from this activity user can add new notations
 */
public class AddNotationsActivity extends AppCompatActivity implements AddNotationView.View, NotationCountCallback {

    /** Views */
    @BindView(R.id.add) ImageView mAdd;
    @BindView(R.id.toolbar2) Toolbar mToolbar;
    @BindView(R.id.notations) EditText mNotations;
    /** Views */

    private LogInModel mLogInModel; //login data
    private AddNotationPresenter mAddNotationPresenter;// current presenter

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_notations);
        ButterKnife.bind(this);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("");

        presenterInit();
        getLogInModelFromIntent();

    }

    /**
     * Initialize presenter for this view
     */
    private void presenterInit() {
        mAddNotationPresenter = new AddNotationPresenter();
        mAddNotationPresenter.attachView(this);
        mAddNotationPresenter.viewIsReady();
    }

    /**
     * Get LogIn data from previous activity
     */
    private void getLogInModelFromIntent() {
        mLogInModel = new Gson().fromJson(getIntent().getStringExtra("login"), LogInModel.class);
    }

    /**
     *@return text of new notation
     */
    @Override
    public String getTextNotation() {
        return mNotations.getText().toString();
    }

    /**
     * clear text of new notation at textView
     */
    @Override
    public void clearAll() {
        mNotations.setText("");
    }

    /**
     * show Toast for user
     * @param messageResId Id of string Resource to show
     */
    @Override
    public void showMessage(int messageResId) {
        Toast.makeText(this, messageResId, Toast.LENGTH_SHORT).show();
    }

    /**
     * Open next activity and put LogIn data
     */
    @Override
    public void next() {
        Intent i = new Intent(AddNotationsActivity.this, NotationListActivity.class);
        i.putExtra("login", new Gson().toJson(mLogInModel));
        startActivity(i);
    }

    /**
     * Close current activity
     */
    @Override
    public void close() {
        finish();
    }

    /**
     * Callback that return count of notations at database
     *
     * @param count of notations
     */
    @Override
    public void onNotationCount(long count) {
        if (mLogInModel != null) mAddNotationPresenter.onAdd(this,
                new NotationsModel(mLogInModel, mNotations.getText().toString(),
                        (count + 1)));

        close();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mAddNotationPresenter.detachView();
        if (isFinishing()) {
            mAddNotationPresenter.destroy();
        }
    }

    @OnClick(R.id.add)
    public void onAddClicked() {
        if(Constants.FIREBASE_MODE == Utils.sCode) {
            mAddNotationPresenter.getNotationCount(mLogInModel, this);
        } else {
            mAddNotationPresenter.onAdd(this, new NotationsModel(mLogInModel, mNotations.getText().toString(), 0));
        }
    }
}
