package com.kram.vlad.storageofinformation.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.kram.vlad.storageofinformation.Constants;
import com.kram.vlad.storageofinformation.R;
import com.kram.vlad.storageofinformation.Utils;
import com.kram.vlad.storageofinformation.activitys.base.BaseActivity;
import com.kram.vlad.storageofinformation.models.LogInModel;
import com.kram.vlad.storageofinformation.mvp.model.web.pojo.RESTModels;
import com.kram.vlad.storageofinformation.mvp.presenters.LogInPresenter;
import com.kram.vlad.storageofinformation.mvp.view.LogInView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * MainActivity, also called from SignUpActivity
 */
public class LogInActivity extends BaseActivity implements LogInView.View {

    public static final String TAG = LogInActivity.class.getSimpleName();

    /** Views*/
    @BindView(R.id.toolbar3) Toolbar mToolbar;
    @BindView(R.id.mail) EditText mMail;
    @BindView(R.id.password) EditText mPassword;
    @BindView(R.id.login_button) ImageView mLoginButton;
    @BindView(R.id.sign_up_button) ImageView mSignUpButton;
    /** Views*/

    private LogInPresenter mLogInPresenter;//Current presenter

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        ButterKnife.bind(this);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("");

        mLogInPresenter = new LogInPresenter();
        mLogInPresenter.attachView(this);
        mLogInPresenter.viewIsReady();
        mLogInPresenter.checkLogin(this);
    }

    @OnClick({R.id.login_button, R.id.sign_up_button})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.login_button:
                mLogInPresenter.onLogIn(this, new LogInModel(Utils.EncodeEmail(mMail.getText().toString()), mPassword.getText().toString()));
                break;
            case R.id.sign_up_button:
                mLogInPresenter.onSignUp();
                break;
        }
    }


    /**
     * @return text from email EditText
     */
    @Override
    public String getTextEmail() {
        return String.valueOf(mMail.getText());
    }

    /**
     * @return text from password EditText
     */
    @Override
    public String getTextPassword() {
        return String.valueOf(mPassword.getText());
    }

    /**
     * clear all editTexts
     */
    @Override
    public void clearAll() {
        mMail.setText("");
        mPassword.setText("");
    }

    /**
     * Show toast for user
     * @param messageResId String resource id of message
     */
    @Override
    public void showMessage(int messageResId) {
        Toast.makeText(this, messageResId, Toast.LENGTH_SHORT).show();
    }

    /**
     * You must detach view and destroy presenter
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        mLogInPresenter.detachView();
        if (isFinishing()) {
            mLogInPresenter.destroy();
        }
    }

    /**
     * open SignUpActivity when user click button
     */
    @Override
    public void openSignUp() {
        startActivity(new Intent(LogInActivity.this, SignUpActivity.class));
    }

    /**
     * Open next Activity(NotationListActivity) and put login data
     */
    @Override
    public void next() {
        Intent i = new Intent(LogInActivity.this, NotationListActivity.class);
        i.putExtra("login", new Gson().toJson(new LogInModel(Utils.EncodeEmail(mMail.getText().toString()), mPassword.getText().toString())));
        startActivity(i);
    }

    /**
     * If user already login - login data stored at SharedPreferences and we just start next Activity(NotationListActivity)
     */
    @Override
    public void nextFromPreferences() {
        Intent i = new Intent(LogInActivity.this, NotationListActivity.class);
        startActivity(i);
    }

    /**
     * Close current Activity
     */
    @Override
    public void close() {
        finish();
    }
}
