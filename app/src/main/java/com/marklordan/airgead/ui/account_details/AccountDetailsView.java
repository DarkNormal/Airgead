package com.marklordan.airgead.ui.account_details;

/**
 * Created by Mark on 10/12/2017.
 */

public interface AccountDetailsView {

    void displaySavingsTarget(String savingsTarget);

    void displayBalance(double balance);

    void displaySavingsTargetPercentage(double savingsTargetPercentage);

    void setMonthlyBalance(String format);
}
