package com.marklordan.airgead.ui.main;

import com.marklordan.airgead.model.Transaction;

import java.util.List;

/**
 * Created by Mark on 12/11/2017.
 */

public interface MainView {

    void setItems(List<Transaction> transactions);

    void showMessage(String message);

    void showProgress();

    void hideProgress();

    void displayBalance(double balanceAmount);
}
