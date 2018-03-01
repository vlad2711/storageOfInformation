package com.kram.vlad.storageofinformation.activitys;

import android.content.pm.ActivityInfo;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;

import com.kram.vlad.storageofinformation.Constants;
import com.kram.vlad.storageofinformation.R;
import com.kram.vlad.storageofinformation.Utils;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.doesNotExist;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

/**
 * Created by vlad on 06.12.2017.
 */
public class LogInActivityTest {

    private static final int ACTIVITY_START_WAITING_MILLIS = 1000;
    private static final int LOG_IN_WAITING_MILLIS = 3000;

    @Rule
    public ActivityTestRule<LogInActivity> mActivityRule = new ActivityTestRule(LogInActivity.class);

    @Test
    public void testWrongOrEmptyPassword() throws Exception {
        onView(withId(R.id.login_button)).perform(click());

        onView(withId(R.id.mail)).perform(typeText("busya@cat.com"), closeSoftKeyboard());
        onView(withId(R.id.password)).perform(typeText("3232"), closeSoftKeyboard());

        onView(withId(R.id.login_button)).perform(click());

        onView(withText(R.string.wrong_password_message)).inRoot(new ToastMatcher())
                .check(matches(isDisplayed()));

        mActivityRule.getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        onView(withId(R.id.mail)).check(matches(withText("busya@cat.com")));
        onView(withId(R.id.password)).check(matches(withText("3232")));

        mActivityRule.getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }

    @Test
    public void testMenu() throws Exception{
        openActionBarOverflowOrOptionsMenu(InstrumentationRegistry.getTargetContext());
        onView(withText(R.string.sql)).perform(click());
        Assert.assertTrue(Utils.sCode == Constants.SQL_MODE);

        openActionBarOverflowOrOptionsMenu(InstrumentationRegistry.getTargetContext());
        onView(withText(R.string.firebase)).perform(click());
        Assert.assertTrue(Utils.sCode == Constants.FIREBASE_MODE);

        openActionBarOverflowOrOptionsMenu(InstrumentationRegistry.getTargetContext());
        onView(withText(R.string.rest)).perform(click());
        Assert.assertTrue(Utils.sCode == Constants.REST_MODE);
    }

    @Test
    public void testLogIn() throws Exception{

        onView(withId(R.id.sign_up_button)).perform(click());

        Thread.sleep(ACTIVITY_START_WAITING_MILLIS);

        onView(withId(R.id.name)).perform(typeText("masya"), closeSoftKeyboard());
        onView(withId(R.id.mail_sign)).perform(typeText("masya@cat.com"), closeSoftKeyboard());
        onView(withId(R.id.password_sign)).perform(typeText("1234"), closeSoftKeyboard());

        onView(withId(R.id.sign_button)).perform(click());

        Thread.sleep(LOG_IN_WAITING_MILLIS);

        onView(withId(R.id.mail)).perform(typeText("masya@cat.com"), closeSoftKeyboard());
        onView(withId(R.id.password)).perform(typeText("1234"), closeSoftKeyboard());
        onView(withId(R.id.login_button)).perform(click());

        Thread.sleep(LOG_IN_WAITING_MILLIS);

        onView(withId(R.id.logOut)).perform(click());
    }
}