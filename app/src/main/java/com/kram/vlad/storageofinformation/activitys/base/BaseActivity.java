package com.kram.vlad.storageofinformation.activitys.base;

import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.kram.vlad.storageofinformation.Constants;
import com.kram.vlad.storageofinformation.R;
import com.kram.vlad.storageofinformation.Utils;

/**
 * Created by vlad on 09.03.2018.
 * BaseActivity of app
 */


public class BaseActivity extends AppCompatActivity {

    private Menu mMenu;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.switch_menu, menu);
        mMenu = menu;
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int index = Utils.sCode;

        switch (item.getItemId()) {
            case R.id.sql:
                Utils.sCode = Constants.SQL_MODE;
                index = Constants.SQL_MODE;
                break;
            case R.id.firebase:
                Utils.sCode = Constants.FIREBASE_MODE;
                index = Constants.FIREBASE_MODE;
                break;
            case R.id.rest:
                Utils.sCode = Constants.REST_MODE;
                index = Constants.REST_MODE;
                break;
        }

        for (int i = 0; i < Constants.COUNT_OF_MENU_ITEMS; i++) {
            if(i != index){
                mMenu.getItem(i).setEnabled(true);
            } else {
                mMenu.getItem(i).setEnabled(false);
            }
        }
        return super.onOptionsItemSelected(item);
    }
}
