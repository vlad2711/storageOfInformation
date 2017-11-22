package com.kram.vlad.storageofinformation.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.kram.vlad.storageofinformation.R;
import com.kram.vlad.storageofinformation.models.LogInModel;
import com.kram.vlad.storageofinformation.models.SignUpModel;
import com.kram.vlad.storageofinformation.mvp.presenters.SignUpPresenter;
import com.kram.vlad.storageofinformation.mvp.view.SignUpView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SignUpActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, SignUpView.View, CompoundButton.OnCheckedChangeListener{

    public static final String TAG = SignUpActivity.class.getSimpleName();

    private SignUpPresenter mPresenter;

    private boolean mIsSQL = true;
    private int mIslandId = 1;

    @BindView(R.id.name) EditText mName;
    @BindView(R.id.mail_sign) EditText mMailSign;
    @BindView(R.id.password_sign) EditText mPasswordSign;
    @BindView(R.id.sign_button) ImageView mSignButton;
    @BindView(R.id.log_button) ImageView mLogButton;
    @BindView(R.id.spinner) Spinner mSpinner;
    //@BindView(R.id.switchSQL) Switch mSwitch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);
        ButterKnife.bind(this);

        mPresenter = new SignUpPresenter();
        mPresenter.attachView(this);
        mPresenter.viewIsReady();

        spinnerInit();
    }

    @OnClick({R.id.sign_button, R.id.log_button})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.sign_button:
                mPresenter.onAdd(this, new SignUpModel(mName.getText().toString(),
                        new LogInModel(mMailSign.getText().toString(),
                                mPasswordSign.getText().toString()), mIslandId));
                break;
            case R.id.log_button:
                mPresenter.onLogIn();
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.switch_menu, menu);
    //     mSwitch.setOnCheckedChangeListener(this);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.detachView();
        if (isFinishing()) {
            mPresenter.destroy();
        }
    }

    private void spinnerInit() {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item,
                mPresenter.getSpinnerData(this));
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        mSpinner.setAdapter(adapter);
        mSpinner.setPrompt("Title");
        mSpinner.setSelection(1);
        mSpinner.setOnItemSelectedListener(this);
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

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        mIsSQL = b;
    }
}
