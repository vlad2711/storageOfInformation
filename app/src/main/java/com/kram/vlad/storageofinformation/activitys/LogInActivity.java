package com.kram.vlad.storageofinformation.activitys;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.kram.vlad.storageofinformation.R;
import com.kram.vlad.storageofinformation.models.LogInModel;
import com.kram.vlad.storageofinformation.models.SignUpModel;
import com.kram.vlad.storageofinformation.sqlite.helpers.SQLiteHelper;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.kram.vlad.storageofinformation.Constants.APP_PREFERENCES;

public class LogInActivity extends AppCompatActivity {

    public static final String TAG = LogInActivity.class.getSimpleName();

    private SQLiteHelper mSQLiteHelper;
    private SharedPreferences mSharedPreferences;

    @BindView(R.id.mail) EditText mMail;
    @BindView(R.id.password) EditText mPassword;
    @BindView(R.id.login_button) ImageView mLoginButton;
    @BindView(R.id.sign_up_button) ImageView mSignUpButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        ButterKnife.bind(this);
        mSQLiteHelper = new SQLiteHelper(this);
        mSharedPreferences = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);

        if(mSharedPreferences.contains("isLogin")){
            boolean isLogin = mSharedPreferences.getBoolean("isLogin", false);
            Log.d(TAG, String.valueOf(isLogin));
            if(isLogin){
                Intent intent = new Intent(LogInActivity.this, HelloActivity.class);
                startActivity(intent);
            }
        }
    }

    @OnClick({R.id.login_button, R.id.sign_up_button})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.login_button:
                SignUpModel model = mSQLiteHelper.logIn(new LogInModel(String.valueOf(mMail.getText()),
                        String.valueOf(mPassword.getText())));
                if(model != null){
                  startActivity(new Intent(LogInActivity.this, HelloActivity.class));
                } else {
                    Toast.makeText(this,"Wrong password or email",Toast.LENGTH_SHORT).show();
                }

                break;
            case R.id.sign_up_button:
                Intent intent = new Intent(LogInActivity.this, SignUpActivity.class);
                startActivity(intent);
                break;
        }
    }
}
