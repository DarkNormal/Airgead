package com.marklordan.airgead;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.Intent;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.intent.Intents;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.marklordan.airgead.ui.LoginActivity;
import com.marklordan.airgead.ui.MainActivity;
import com.marklordan.airgead.ui.SetupAccountActivity;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import dalvik.annotation.TestTargetClass;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.Intents.intending;
import static android.support.test.espresso.intent.matcher.ComponentNameMatchers.hasShortClassName;
import static android.support.test.espresso.intent.matcher.IntentMatchers.anyIntent;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.intent.matcher.IntentMatchers.isInternal;
import static android.support.test.espresso.matcher.ViewMatchers.hasErrorText;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

/**
 * Created by Mark on 04/09/2017.
 */
@RunWith(AndroidJUnit4.class)
public class LoginActivityTest {

    private LoginActivityIdlingResource mIdlingResource;

    @Rule
    public IntentsTestRule<LoginActivity> mLoginActivityActivityTestRule = new IntentsTestRule<>(LoginActivity.class);

    @Before
    public void setUp() {
        if (mIdlingResource != null) {
            Espresso.unregisterIdlingResources(mIdlingResource);
        }

        // Register Activity as idling resource
        mIdlingResource = new LoginActivityIdlingResource(mLoginActivityActivityTestRule.getActivity());
        Espresso.registerIdlingResources(mIdlingResource);
    }

    @Test
    public void checkTitleTextDisplayedCorrectly(){
        onView(withId(R.id.login_app_title)).check(matches(withText(R.string.app_name)));
    }

    @Test
    public void checkErrorIsDisplayedForBlankEmail(){
        onView(withId(R.id.email_sign_in_button)).perform(click());

        onView(withId(R.id.email)).check(matches(hasErrorText("Required")));
        onView(withId(R.id.password)).check(matches(hasErrorText("Required")));
    }

    /**
     * onSignInWithExistingAccount
     *
     * This test is to check the Firebase Login functionality is working as it should
     * with an existing account. The MainActivity should be launched via an Intent.
     *
     * IdlingResource is used for this test for Espresso to wait for the Firebase request.
     *
     * If the result is a success, the MainActivity is launched.
     *
     * @throws InterruptedException - This is to allow the soft keyboard to close
     */


    @Test
    public void onSignInWithExistingAccount() throws InterruptedException {
        Intent intent = new Intent();
        Instrumentation.ActivityResult intentResult = new Instrumentation.ActivityResult(Activity.RESULT_OK,intent);

        intending(isInternal()).respondWith(intentResult);

        onView(withId(R.id.email)).perform(typeText("testairgeadaccount@marklordan.com"));
        onView(withId(R.id.password)).perform(typeText("testpassword"), closeSoftKeyboard());

        Thread.sleep(500);

        onView(withId(R.id.email_sign_in_button)).perform(click());

        intended(allOf(hasComponent(MainActivity.class.getName())));

    }

    @After
    public void tearDown() {
        if (mIdlingResource != null) {
            Espresso.unregisterIdlingResources(mIdlingResource);
        }
    }
}
