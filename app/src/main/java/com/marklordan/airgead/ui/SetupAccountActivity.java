package com.marklordan.airgead.ui;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;

import com.github.paolorotolo.appintro.AppIntro;
import com.marklordan.airgead.ui.fragments.BalanceDetailsFragment;
import com.marklordan.airgead.ui.fragments.CurrencyFragment;
import com.marklordan.airgead.ui.fragments.IntroFragment;

public class SetupAccountActivity extends AppIntro implements IntroFragment.OnFragmentInteractionListener, CurrencyFragment.OnCurrencySelectedListener{

    public static final String TAG = "SetupAccountActivity";
    private String currencyOfChoice;
    private double bankAccountValues;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        addSlide(new IntroFragment());
        addSlide(new CurrencyFragment());
        addSlide(new BalanceDetailsFragment());

        showSkipButton(false);

    }

    @Override
    public void onDonePressed(Fragment currentFragment) {
        super.onDonePressed(currentFragment);
        bankAccountValues = ((BalanceDetailsFragment)currentFragment).getCurrentBalance();
    }

    @Override
    public void onSlideChanged(@Nullable Fragment oldFragment, @Nullable Fragment newFragment) {
        super.onSlideChanged(oldFragment, newFragment);
        if(oldFragment instanceof BalanceDetailsFragment){
            Log.d(TAG, "Balance Fragment");
        }

    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void onCurrencySelected(String selectedCurrency) {
        Log.d(TAG, selectedCurrency);
        currencyOfChoice = selectedCurrency;
    }
}
