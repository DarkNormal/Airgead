package com.marklordan.airgead.ui.account_details;

/**
 * Created by Mark on 10/12/2017.
 */

public interface AccountDetailsPresenter {

    void onResume();

    void onSavingsTargetSelected(double savingsTarget);

    void onBalanceSet(double balance);

    void onDestroy();

    void calculateSavingsTarget(int percentage);

    void updateAccountDetails(double currentBalance, double monthlySalary);
}
