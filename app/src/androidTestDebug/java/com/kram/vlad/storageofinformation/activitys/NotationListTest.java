package com.kram.vlad.storageofinformation.activitys;

import android.support.test.rule.ActivityTestRule;

import com.kram.vlad.storageofinformation.R;
import com.kram.vlad.storageofinformation.Utils;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.util.Log;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

/**
 * Created by vlad on 06.12.2017.
 */

public class NotationListTest {
    
    public static final String TAG = NotationListTest.class.getSimpleName();
    public static final int NETWORK_WAITING_MILLIS = 5000;
    public static final int START_NEW_ACTIVITY_WAITING_MILLIS = 1000;

    @Rule
    public ActivityTestRule<LogInActivity> mActivityRule = new ActivityTestRule(LogInActivity.class);

    @Test
    public void addNotations() throws Exception {
        Log.d(TAG, "start add");

        onView(withId(R.id.mail)).perform(typeText("vlad@gmail.com"));
        onView(withId(R.id.password)).perform(typeText("1234"));
        onView(withId(R.id.login_button)).perform(click());

        Thread.sleep(START_NEW_ACTIVITY_WAITING_MILLIS);

        onView(withId(R.id.floatingActionButton2)).perform(click());
        onView(withId(R.id.notations)).perform(typeText("Very big and cool note"));
        onView(withId(R.id.add)).perform(click());
        Log.d(TAG, "finish add");

        onView(withId(R.id.logOut)).perform(click());
    }

    @Test
    public void swipe() throws Exception {

        onView(withId(R.id.mail)).perform(typeText("vlad@gmail.com"));
        onView(withId(R.id.password)).perform(typeText("1234"));
        onView(withId(R.id.login_button)).perform(click());

        Thread.sleep(START_NEW_ACTIVITY_WAITING_MILLIS);


        Log.d(TAG, "start swipe");
        int oldSize = Utils.sNotations.size();

        for (int i = 0; i < 3; i++) {
            onView(withId(R.id.recyclerView)).perform(RecyclerViewActions.scrollToPosition(Utils.sNotations.size() - 1));
            Thread.sleep(NETWORK_WAITING_MILLIS);
            Assert.assertNotEquals(oldSize, Utils.sNotations.size());
            oldSize = Utils.sNotations.size();
        }

        onView(withId(R.id.logOut)).perform(click());
        Log.d(TAG, "finish swipe");
    }
}

