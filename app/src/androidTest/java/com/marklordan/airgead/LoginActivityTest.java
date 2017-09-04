package com.marklordan.airgead;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.marklordan.airgead.ui.LoginActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import dalvik.annotation.TestTargetClass;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.hasErrorText;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

/**
 * Created by Mark on 04/09/2017.
 */
@RunWith(AndroidJUnit4.class)
public class LoginActivityTest {

    @Rule
    public ActivityTestRule<LoginActivity> mLoginActivityActivityTestRule = new ActivityTestRule<LoginActivity>(LoginActivity.class);


    @Test
    public void checkTitleTextDisplayedCorrectly(){
        onView(withId(R.id.login_app_title)).check(matches(withText(R.string.app_name)));
    }

    @Test
    public void checkErrorIsDisplayedForBlankEmail(){
        onView(withId(R.id.email_sign_in_button)).perform(click());

        onView(withId(R.id.email)).check(matches(hasErrorText("Required")));
    }
}
