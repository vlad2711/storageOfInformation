package com.kram.vlad.storageofinformation.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.kram.vlad.storageofinformation.Constants;
import com.kram.vlad.storageofinformation.R;
import com.kram.vlad.storageofinformation.Utils;
import com.kram.vlad.storageofinformation.activitys.base.BaseActivity;
import com.kram.vlad.storageofinformation.models.LogInModel;
import com.kram.vlad.storageofinformation.models.SignUpModel;
import com.kram.vlad.storageofinformation.mvp.presenters.SignUpPresenter;
import com.kram.vlad.storageofinformation.mvp.view.SignUpView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SignUpActivity extends BaseActivity implements SignUpView.View{

    public static final String TAG = SignUpActivity.class.getSimpleName();


    @BindView(R.id.name) EditText mName;
    @BindView(R.id.mail_sign) EditText mMailSign;
    @BindView(R.id.password_sign) EditText mPasswordSign;
    @BindView(R.id.sign_button) ImageView mSignButton;
    @BindView(R.id.log_button) ImageView mLogButton;
    @BindView(R.id.toolbar5) Toolbar mToolbar;

    private SignUpPresenter mPresenter;//Current presenter

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);
        ButterKnife.bind(this);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("");

        mPresenter = new SignUpPresenter();
        mPresenter.attachView(this);
        mPresenter.viewIsReady();
    }

    @OnClick({R.id.sign_button, R.id.log_button})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.sign_button:
                mPresenter.onAdd(this, new SignUpModel(mName.getText().toString(),
                        new LogInModel(Utils.EncodeEmail(mMailSign.getText().toString()),
                                mPasswordSign.getText().toString())));
                break;
            case R.id.log_button:
                mPresenter.onLogIn();
                break;
        }
    }


    /**
     * You must detach view and destroy presenter
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.detachView();
        if (isFinishing()) {
            mPresenter.destroy();
        }
    }

    /**
     * @return user name from EditText
     */
    @Override
    public String getTextName() {
        return mName.getText().toString();
    }

    /**
     * @return user email from EditText
     */
    @Override
    public String getTextEmail() {
        return mMailSign.getText().toString();
    }

    /**
     * @return password from EditText
     */
    @Override
    public String getTextPassword() {
        return mPasswordSign.getText().toString();
    }

    /**
     * Clear all EditTexts
     */
    @Override
    public void clearAll() {
        mName.setText("");
        mMailSign.setText("");
        mPasswordSign.setText("");
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
     * Start next activity(LogInActivity)
     */
    @Override
    public void next() {
        startActivity(new Intent(SignUpActivity.this, LogInActivity.class));
    }

    /**
     * Close current Activity
     */
    @Override
    public void close() {
        finish();
    }
}
