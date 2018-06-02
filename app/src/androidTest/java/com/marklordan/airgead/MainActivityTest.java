package com.marklordan.airgead;


import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.view.View;

import com.marklordan.airgead.ui.account_details.AccountDetailsActivity;
import com.marklordan.airgead.ui.main.MainActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.clearText;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.swipeLeft;
import static android.support.test.espresso.action.ViewActions.typeText;
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
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


@RunWith(AndroidJUnit4.class)
public class MainActivityTest {
    @Rule public IntentsTestRule<MainActivity> mActivityTestRule = new IntentsTestRule<MainActivity>(MainActivity.class);

    @Test
    public void clickAddTransactionFABOpensAddTransactionUi(){
        onView(withId(R.id.add_transaction_fab)).perform(click());

        onView(withId(R.id.add_transaction_toolbar)).check(matches(isDisplayed()));

    }

//    @Test
//    public void onSwipeFirstItemInListRemovesItem(){
//        //when
//        onView(withId(R.id.recent_transaction_recyclerview)).perform(RecyclerViewActions.actionOnItemAtPosition(0, swipeLeft()));
//
//        //then
//        onView(withText("Transaction removed")).check(matches((withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE))));
//    }

    @Test
    public void ensureBalanceIsDisplayedOnLoadAndOpensAccountDetailsOnClick(){

        onView(withId(R.id.textview_account_balance)).check(matches(allOf(withText(any(String.class)),isDisplayed())));

        onView(withId(R.id.textview_account_balance)).perform(click());
        intended(hasComponent(hasClassName(AccountDetailsActivity.class.getName())));
    }

    @Test
    public void addTransactionToList(){
        /*  TODO delete all expenses before running this test
            milliseconds of current time used to avoid issue of multiple matches with matches()
            if the test runs too often, it will fail due to the item not being displayed
            (too many items in recyclerview - ordering on query?)
        */
        String testExpenseText = "Test Expense " +System.currentTimeMillis();
        onView(withId(R.id.add_transaction_fab)).perform(click());

        onView(withId(R.id.transaction_value_input)).perform(typeText("5"));
        onView(withId(R.id.transaction_desc_input)).perform(clearText());
        onView(withId(R.id.transaction_desc_input)).perform(typeText(testExpenseText));
        onView(withId(R.id.confirm_item)).perform(click());

        onView(withText(testExpenseText)).check(matches(isDisplayed()));
    }
}
