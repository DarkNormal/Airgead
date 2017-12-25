package com.marklordan.airgead;


import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.marklordan.airgead.ui.LoginActivity;
import com.marklordan.airgead.ui.account_details.AccountDetailsActivity;
import com.marklordan.airgead.ui.main.MainActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.swipeLeft;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.Intents.intending;
import static android.support.test.espresso.intent.matcher.ComponentNameMatchers.hasClassName;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withEffectiveVisibility;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.any;


@RunWith(AndroidJUnit4.class)
public class MainActivityTest {
    @Rule public IntentsTestRule<MainActivity> mActivityTestRule = new IntentsTestRule<MainActivity>(MainActivity.class);

    @Test
    public void onSwipeFirstItemInListRemovesItem(){
        //when
        onView(withId(R.id.recent_transaction_recyclerview)).perform(RecyclerViewActions.actionOnItemAtPosition(0, swipeLeft()));

        //then
        onView(withText("Transaction removed")).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)));
    }

    @Test
    public void ensureBalanceIsDisplayedOnLoadAndOpensAccountDetailsOnClick(){

        onView(withId(R.id.textview_account_balance)).check(matches(allOf(withText(any(String.class)),isDisplayed())));

        onView(withId(R.id.textview_account_balance)).perform(click());
        intended(hasComponent(hasClassName(AccountDetailsActivity.class.getName())));
    }
}
