package com.marklordan.airgead.model;

import java.util.List;

/**
 * Created by Mark on 20/12/2016.
 */

public class AirgeadAccount {
    private double mBalance;
    private List<Transaction> mTransactions;
    private double mSavingsTarget;

    public AirgeadAccount(double balance, List<Transaction> transactions, double savingsTarget) {
        mBalance = balance;
        mTransactions = transactions;
        mSavingsTarget = savingsTarget;
    }

    public double getBalance() {
        return mBalance;
    }

    public void setBalance(double balance) {
        mBalance = balance;
    }

    public List<Transaction> getTransactions() {
        return mTransactions;
    }

    public void setTransactions(List<Transaction> transactions) {
        mTransactions = transactions;
    }

    public double getSavingsTarget() {
        return mSavingsTarget;
    }

    public void setSavingsTarget(double savingsTarget) {
        mSavingsTarget = savingsTarget;
    }

    public boolean addTransaction(Transaction transaction){
        return true;
    }
}
