package com.kram.vlad.storageofinformation.mvp.model.firebase;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.kram.vlad.storageofinformation.Utils;
import com.kram.vlad.storageofinformation.callbacks.LogInCallback;
import com.kram.vlad.storageofinformation.callbacks.NotationCountCallback;
import com.kram.vlad.storageofinformation.callbacks.NotationsDownloadedCallback;
import com.kram.vlad.storageofinformation.models.LogInModel;
import com.kram.vlad.storageofinformation.models.NotationsModel;
import com.kram.vlad.storageofinformation.models.SignUpModel;

import java.util.Objects;

/**
 * Created by vlad on 03.11.17.
 * In this class you can see all work of app with Firebase Database
 */

public class FirebaseHelper {
    private static final String TAG = FirebaseHelper.class.getSimpleName();

    /**
     * Add new user to Firebase Database
     * @param signUpModel User data that must e added
     */
    public void addNewUser(SignUpModel signUpModel){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("users");

        myRef.child(signUpModel.getLogInModel().getEmail()).setValue(signUpModel);
    }

    /**
     * Return count of Notations in database
     * @param logInModel user login data
     * @param notationCountCallback Callback, called when app get count of notations
     */
    public void getNotationCount(LogInModel logInModel, NotationCountCallback notationCountCallback){
        DatabaseReference database = FirebaseDatabase.getInstance().getReference();
        DatabaseReference ref = database.child("notations").child(logInModel.getEmail());

        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                notationCountCallback.onNotationCount(dataSnapshot.getChildrenCount());//Data downloaded, call callback
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {}
        });
    }

    /**
     * Log in by Firebase Database
     * @param logInModel user login data. In this function app compare it with logIn data from database
     * @param logInCallback Callback, called when app compare logIn data from database with param logInModel
     */
    public void getUser(LogInModel logInModel, LogInCallback logInCallback){
        DatabaseReference database = FirebaseDatabase.getInstance().getReference();
        DatabaseReference ref = database.child("users").child(logInModel.getEmail());

        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.d(TAG, dataSnapshot.toString());
                if(Objects.equals(dataSnapshot.child("logInModel").child("password").getValue(), logInModel.getPassword())) {
                    logInCallback.onLogInDataDownload(new LogInModel(String.valueOf(dataSnapshot.child("logInModel").child("email").getValue()),
                            String.valueOf(dataSnapshot.child("logInModel").child("password").getValue())));
                } else {
                    logInCallback.onLogInDataDownload(null);
                }
                ref.removeEventListener(this);
                ref.onDisconnect();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    /**
     * Return notations that id in range between startRange param and endRange param
     * @param logInModel user login data
     * @param notationsDownloadedCallback callback, called when notations downloaded from Firebase Database
     * @param startRange first notation id
     * @param endRange last notation id
     */
    public void getDataInRange(LogInModel logInModel, NotationsDownloadedCallback notationsDownloadedCallback,
                               int startRange, int endRange){
        DatabaseReference database = FirebaseDatabase.getInstance().getReference();
        DatabaseReference ref = database.child("notations").child(logInModel.getEmail());

        Query query = ref.orderByKey()
                .startAt(String.valueOf(startRange))
                .endAt(String.valueOf(endRange));

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.d(TAG, String.valueOf(dataSnapshot.toString()) + "download" + dataSnapshot.getChildrenCount());

                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Log.d(TAG, String.valueOf(postSnapshot.getValue()));
                    Utils.sNotations.add(String.valueOf(postSnapshot.getValue()));
                }

                notationsDownloadedCallback.onNotationsDownLoaded();
                ref.removeEventListener(this);
                ref.onDisconnect();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                 Log.e(TAG, "onCancelled", databaseError.toException());
            }
        });
    }

    /**
     * Add new notation to database
     * @param notationsModel notation data, that must be added
     */
    public void addNotation(NotationsModel notationsModel){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference().child("notations");

        myRef.child(notationsModel.getLogInModel().getEmail())
                .child(String.valueOf(notationsModel.getNumber()))
                .setValue(notationsModel.getNotations());
    }
}
