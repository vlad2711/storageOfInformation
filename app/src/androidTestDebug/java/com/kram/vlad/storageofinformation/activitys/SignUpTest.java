package com.kram.vlad.storageofinformation.activitys;

import android.content.pm.ActivityInfo;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;

import com.kram.vlad.storageofinformation.R;
import com.kram.vlad.storageofinformation.Utils;

import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

/**
 * Created by vlad on 06.12.2017.
 */

public class SignUpTest {
    public static final int START_NEW_ACTIVITY_WAITING_MILLIS = 1000;
    @Rule
    public ActivityTestRule<LogInActivity> mActivityRule = new ActivityTestRule(LogInActivity.class);

    @Test
    public void testEmpty() throws Exception {
        onView(withId(R.id.sign_up_button)).perform(click());
        Thread.sleep(START_NEW_ACTIVITY_WAITING_MILLIS);

        onView(withId(R.id.sign_button)).perform(click());

        onView(withText(R.string.empty_password_message)).inRoot(new ToastMatcher())
                .check(matches(isDisplayed()));

    }

    @Test
    public void testMenu() throws Exception{
        onView(withId(R.id.sign_up_button)).perform(click());

        Thread.sleep(START_NEW_ACTIVITY_WAITING_MILLIS);

        openActionBarOverflowOrOptionsMenu(InstrumentationRegistry.getTargetContext());
        onView(withText(R.string.sql)).perform(click());
        Assert.assertTrue(Utils.isSQL);

        openActionBarOverflowOrOptionsMenu(InstrumentationRegistry.getTargetContext());
        onView(withText(R.string.firebase)).perform(click());
        Assert.assertFalse(Utils.isSQL);

        onView(withId(R.id.log_button)).perform(click());
        Thread.sleep(START_NEW_ACTIVITY_WAITING_MILLIS);

    }

    @Test
    public void testOrientation() throws Exception{
        onView(withId(R.id.sign_up_button)).perform(click());

        Thread.sleep(START_NEW_ACTIVITY_WAITING_MILLIS);

        onView(withId(R.id.name)).perform(typeText("Masya"), closeSoftKeyboard());
        onView(withId(R.id.mail_sign)).perform(typeText("masya@cat.com"), closeSoftKeyboard());
        onView(withId(R.id.password_sign)).perform(typeText("1234"), closeSoftKeyboard());

        mActivityRule.getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        onView(withId(R.id.name)).check(matches(withText("Masya")));
        onView(withId(R.id.mail_sign)).check(matches(withText("masya@cat.com")));
        onView(withId(R.id.password_sign)).check(matches(withText("1234")));

        mActivityRule.getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        onView(withId(R.id.name)).check(matches(withText("Masya")));
        onView(withId(R.id.mail_sign)).check(matches(withText("masya@cat.com")));
        onView(withId(R.id.password_sign)).check(matches(withText("1234")));

        onView(withId(R.id.log_button)).perform(click());
        Thread.sleep(START_NEW_ACTIVITY_WAITING_MILLIS);

    }
}
