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
import com.kram.vlad.storageofinformation.models.LogInModel;
import com.kram.vlad.storageofinformation.models.SignUpModel;
import com.kram.vlad.storageofinformation.mvp.presenters.SignUpPresenter;
import com.kram.vlad.storageofinformation.mvp.view.SignUpView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SignUpActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, SignUpView.View{

    public static final String TAG = SignUpActivity.class.getSimpleName();

    private SignUpPresenter mPresenter;

    private int mIslandId = 1;

    @BindView(R.id.name) EditText mName;
    @BindView(R.id.mail_sign) EditText mMailSign;
    @BindView(R.id.password_sign) EditText mPasswordSign;
    @BindView(R.id.sign_button) ImageView mSignButton;
    @BindView(R.id.log_button) ImageView mLogButton;
    @BindView(R.id.toolbar5) Toolbar mToolbar;

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
                                mPasswordSign.getText().toString()), mIslandId));
                break;
            case R.id.log_button:
                mPresenter.onLogIn();
                break;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.sql:
                Utils.sCode = Constants.SQL_MODE;
                break;
            case R.id.firebase:
                Utils.sCode = Constants.FIREBASE_MODE;
                break;
            case R.id.rest:
                Utils.sCode = Constants.REST_MODE;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.switch_menu, menu);
        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.detachView();
        if (isFinishing()) {
            mPresenter.destroy();
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
         mIslandId = i;
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        mIslandId = 1;
    }


    @Override
    public String getTextName() {
        return mName.getText().toString();
    }

    @Override
    public String getTextEmail() {
        return mMailSign.getText().toString();
    }

    @Override
    public String getTextPassword() {
        return mPasswordSign.getText().toString();
    }

    @Override
    public void clearAll() {
        mName.setText("");
        mMailSign.setText("");
        mPasswordSign.setText("");
    }

    @Override
    public void showMessage(int messageResId) {
        Toast.makeText(this, messageResId, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void next() {
        startActivity(new Intent(SignUpActivity.this, LogInActivity.class));
    }

    @Override
    public void close() {
        finish();
    }
}
