package com.kram.vlad.storageofinformation.activitys;

import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import com.kram.vlad.storageofinformation.R;
import com.kram.vlad.storageofinformation.models.LogInModel;
import com.kram.vlad.storageofinformation.models.SignUpModel;
import com.kram.vlad.storageofinformation.sqlite.helpers.SQLiteHelper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SignUpActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    public static final String TAG = SignUpActivity.class.getSimpleName();
    private int mIslandId = 1;
    private SQLiteHelper mDatabaseHelper;

    @BindView(R.id.name) EditText mName;
    @BindView(R.id.mail_sign) EditText mMailSign;
    @BindView(R.id.password_sign) EditText mPasswordSign;
    @BindView(R.id.sign_button) ImageView mSignButton;
    @BindView(R.id.log_button) ImageView mLogButton;
    @BindView(R.id.spinner) Spinner mSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);
        ButterKnife.bind(this);

        try {
            spinnerInit();
        } catch (IOException e) {
            e.printStackTrace();
        }
        mDatabaseHelper = new SQLiteHelper(this);
    }

    @OnClick({R.id.sign_button, R.id.log_button})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.sign_button:
                mDatabaseHelper.signUp(new SignUpModel(String.valueOf(mName.getText()),
                        new LogInModel(String.valueOf(mMailSign.getText()), String.valueOf(mPasswordSign.getText())), mIslandId));
                startActivity(new Intent(SignUpActivity.this, LogInActivity.class));
                break;
            case R.id.log_button:
                Intent intent = new Intent(SignUpActivity.this, LogInActivity.class);
                startActivity(intent);
                break;
        }
    }

    private void spinnerInit() throws IOException {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, getIslandArrayList());
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        mSpinner.setAdapter(adapter);
        mSpinner.setPrompt("Title");
        mSpinner.setSelection(1);
        mSpinner.setOnItemSelectedListener(this);
    }

    private ArrayList<String> getIslandArrayList() throws IOException {
        String islands = getTextFileInString();
        ArrayList<String> arrayList = new ArrayList<>();
        char islandsChar[] = islands.toCharArray();
        String temp = "";
        for (char anIslandsChar : islandsChar) {
            if (anIslandsChar != ',') {
                temp += anIslandsChar;
            } else {
                arrayList.add(temp);
                temp = "";
            }
        }
        return arrayList;
    }

    private String getTextFileInString() throws IOException {
        InputStream islands = getAssets().open("islands.txt");
        BufferedReader in = new BufferedReader(new InputStreamReader(islands, "UTF-8"));
        String str = in.readLine().replace("�", "");
        in.close();

        return str;

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
         mIslandId = i;
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        mIslandId = 1;
    }
}
