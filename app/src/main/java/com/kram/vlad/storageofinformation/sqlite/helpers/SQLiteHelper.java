package com.kram.vlad.storageofinformation.sqlite.helpers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.kram.vlad.storageofinformation.models.IslandModel;
import com.kram.vlad.storageofinformation.models.LogInModel;
import com.kram.vlad.storageofinformation.models.SignUpModel;
import com.kram.vlad.storageofinformation.sqlite.handlers.IDatabaseHandler;

import java.util.ArrayList;
import java.util.Objects;

/**
 * Created by vlad on 16.10.17.
 */

public class SQLiteHelper extends SQLiteOpenHelper implements IDatabaseHandler{

    private static final String DATABASE_NAME = "registrationManager";
    private static final String TABLE_REGISTRATION = "registrationForStorage";
    private static final String TABLE_ISLANDS = "tableOfIslands";
    private static final String KEY_EMAIL = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_PASSWORD = "password";
    private static final String KEY_ISLAND_ID = "islandId";
    private static final String KEY_ISLAND_NAME = "islandName";

    public SQLiteHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public SQLiteHelper(Context context){
        super(context, DATABASE_NAME, null, 2);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String CREATE_REGISTRATION_TABLE = "CREATE TABLE " + TABLE_REGISTRATION + "("
                + KEY_EMAIL + " TEXT," + KEY_PASSWORD + " TEXT,"
                + KEY_NAME + " TEXT," + KEY_ISLAND_ID + " INTEGER" + ")";
        sqLiteDatabase.execSQL(CREATE_REGISTRATION_TABLE);

        String CREATE_ISLAND_TABLE =  "CREATE TABLE " + TABLE_ISLANDS + "("
                + KEY_ISLAND_ID + " INTEGER," + KEY_ISLAND_NAME + " TEXT" + ")";
        sqLiteDatabase.execSQL(CREATE_ISLAND_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_REGISTRATION);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_ISLANDS);
        onCreate(sqLiteDatabase);
    }

    @Override
    public void signUp(SignUpModel signUp) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_EMAIL, signUp.getLogInModel().getEmail());
        values.put(KEY_PASSWORD, signUp.getLogInModel().getPassword());
        values.put(KEY_NAME, signUp.getName());
        values.put(KEY_ISLAND_ID, signUp.getIslandId());
        db.insert(TABLE_REGISTRATION, null, values);

        db.close();
    }

    @Override
    public void initIsland(ArrayList<String> islands) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        for (int i = 0; i < islands.size(); i++) {
            values.put(KEY_ISLAND_ID, i);
            values.put(KEY_ISLAND_NAME, islands.get(i));
        }
        db.insert(TABLE_ISLANDS, null, values);

        db.close();
    }

    @Override
    public SignUpModel logIn(LogInModel logIn) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_REGISTRATION, new String[] { KEY_EMAIL,
                        KEY_PASSWORD, KEY_NAME, KEY_ISLAND_ID}, KEY_EMAIL + "=?",
                new String[] { String.valueOf(logIn.getEmail()) }, null, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            SignUpModel signUpModel = new SignUpModel(cursor.getString(2), new LogInModel(cursor.getString(0), cursor.getString(1)), cursor.getInt(3));
            cursor.close();

            if (Objects.equals(logIn.getEmail(), signUpModel.getLogInModel().getEmail())
                && Objects.equals(logIn.getPassword(), signUpModel.getLogInModel().getPassword())) {
                return signUpModel;
            }
        }
        return null;
    }
}
