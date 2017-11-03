package com.kram.vlad.storageofinformation.activitys;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.kram.vlad.storageofinformation.Constants;
import com.kram.vlad.storageofinformation.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class HelloActivity extends AppCompatActivity {

    public static final String TAG = HelloActivity.class.getSimpleName();

    @BindView(R.id.logOut) Button mLogOut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hello);
        ButterKnife.bind(this);
        SharedPreferences sharedPreferences = getSharedPreferences(Constants.APP_PREFERENCES, Context.MODE_PRIVATE);
        sharedPreferences.edit().putBoolean("isLogin", true).apply();
    }

    @OnClick(R.id.logOut)
    public void onViewClicked() {
        SharedPreferences sharedPreferences = getSharedPreferences(Constants.APP_PREFERENCES, Context.MODE_PRIVATE);
        sharedPreferences.edit().putBoolean("isLogin", false).apply();
        finish();
    }
}
