package com.marklordan.airgead.ui.main;

import com.marklordan.airgead.model.AirgeadAccount;
import com.marklordan.airgead.model.Transaction;

import java.util.List;

public interface MainView {

    void setItems(List<Transaction> transactions);

    void showMessage(String message);

    void showRemovedMessage(String message, Transaction transaction, int position);

    void showProgress();

    void hideProgress();

    void displayBalance(String balanceAmount);

    void displayMonthlyBalance(String balanceAmount);

    void displaySavingsTarget(String savingsTargetAmount);

    void displayRemainingBudget(String remainingBudget);

    void showAccountDetails();

    void setAccount(AirgeadAccount account);

    void showTransactionDetails(Transaction t);
}
