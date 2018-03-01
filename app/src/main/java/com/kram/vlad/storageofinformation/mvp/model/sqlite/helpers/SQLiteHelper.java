package com.kram.vlad.storageofinformation.mvp.model.sqlite.helpers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.kram.vlad.storageofinformation.Utils;
import com.kram.vlad.storageofinformation.callbacks.NotationsDownloadedCallback;
import com.kram.vlad.storageofinformation.models.LogInModel;
import com.kram.vlad.storageofinformation.models.NotationsModel;
import com.kram.vlad.storageofinformation.models.SignUpModel;
import com.kram.vlad.storageofinformation.mvp.model.sqlite.handlers.IDatabaseHandler;

import java.util.ArrayList;
import java.util.Objects;

/**
 * Created by vlad on 16.10.17.
 * This class do all job with SQLite
 */

public class SQLiteHelper extends SQLiteOpenHelper implements IDatabaseHandler{

    private static final String TAG = SQLiteHelper.class.getSimpleName();

    private static final int DATABASE_VERSION = 7; // Current version of database

    //Names of tables, databases, columns
    private static final String DATABASE_NAME = "registrationManager";
    private static final String TABLE_REGISTRATION = "registrationForStorage";
    private static final String TABLE_NOTATIONS = "tableOfNotations";

    private static final String KEY_EMAIL = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_PASSWORD = "password";

    private static final String KEY_NOTATION_ID = "id";
    private static final String KEY_NOTATION = "notation";
    private static final String KEY_USER_EMAIL = "email";
    //Names of tables, databases, columns

    /**
     * Super constructor
     * @param context of current Activity
     * @param name Name of database
     * @param factory cursor factory
     * @param version current version of database
     */
    public SQLiteHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    /**
     * @param context of current Activity
     */
    public SQLiteHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /**
     * Create tables and add it to database
     * @param sqLiteDatabase SQLite database
     */
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String CREATE_REGISTRATION_TABLE = "CREATE TABLE " + TABLE_REGISTRATION + "("
                + KEY_EMAIL + " TEXT," + KEY_PASSWORD + " TEXT,"
                + KEY_NAME + " TEXT," + ")";
        sqLiteDatabase.execSQL(CREATE_REGISTRATION_TABLE);

        String CREATE_NOTATION_TABLE = "CREATE TABLE " + TABLE_NOTATIONS + "(" +
                KEY_NOTATION_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + KEY_USER_EMAIL + " TEXT," +
                KEY_NOTATION + " TEXT" + ")";
        sqLiteDatabase.execSQL(CREATE_NOTATION_TABLE);
    }

    /**
     * Update database
     */
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_REGISTRATION);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NOTATIONS);
        onCreate(sqLiteDatabase);
    }

    /**
     * Add new user to database
     * @param signUp user sign up data
     */
    @Override
    public void signUp(SignUpModel signUp) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_EMAIL, signUp.getLogInModel().getEmail());
        values.put(KEY_PASSWORD, signUp.getLogInModel().getPassword());
        values.put(KEY_NAME, signUp.getName());

        db.insert(TABLE_REGISTRATION, null, values);
        db.close();
    }

    /**
     * Add new user notation to notation table
     * @param notationsModel notation data
     */
    @Override
    public void addNotations(NotationsModel notationsModel) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(KEY_NOTATION_ID, 1);
        values.put(KEY_USER_EMAIL,  notationsModel.getLogInModel().getEmail());
        values.put(KEY_NOTATION, notationsModel.getNotations());

        db.insert(TABLE_NOTATIONS, null, values);
        db.close();
    }

    /**
     * Download notations from table with id in range between start param and finish param
     * @param logInModel login data
     * @param start function will return notations with id greater than start
     * @param finish function will return notations with id lower than finish
     */
    @Override
    public void downloadNotations(LogInModel logInModel, int start, int finish) {

        SQLiteDatabase db = this.getWritableDatabase();
        try {
            for (int i = start; i < finish; i++) {
                Cursor c = db.query(TABLE_NOTATIONS, new String[]{KEY_USER_EMAIL, KEY_NOTATION}, KEY_NOTATION_ID + "=?" ,
                        new String[]{String.valueOf(i)}, null, null, null, null);

                if (c != null && c.moveToFirst() && Objects.equals(logInModel.getEmail(), c.getString(0))) {
                    Utils. sNotations.add(c.getString(1));
                    Log.d(TAG, c.getString(1));
                }
            }

        } catch (SQLiteException e){
            e.printStackTrace();
        }
    }

    /**
     * Compare logIn data param with table of users
     * @param logIn logIn data
     * @return null if user don't register, logIn param if user register
     */
    @Override
    public LogInModel logIn(LogInModel logIn) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_REGISTRATION, new String[] { KEY_EMAIL,
                        KEY_PASSWORD, KEY_NAME}, KEY_EMAIL + "=?",
                new String[] { String.valueOf(logIn.getEmail()) }, null, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            SignUpModel signUpModel = new SignUpModel(cursor.getString(2), new LogInModel(cursor.getString(0), cursor.getString(1)));
            cursor.close();

            Log.d(TAG, signUpModel.getName());

            if (Objects.equals(logIn.getEmail(), signUpModel.getLogInModel().getEmail())
                && Objects.equals(logIn.getPassword(), signUpModel.getLogInModel().getPassword())) {
                Log.d(TAG, signUpModel.getLogInModel().getPassword());

                return logIn;
            }
        }
        return null;
    }
}
