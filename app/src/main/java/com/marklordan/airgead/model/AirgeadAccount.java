package com.marklordan.airgead.model;

import java.util.ArrayList;
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
        if(transactions != null) mTransactions = transactions;
        else mTransactions = new ArrayList<>();
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
        mTransactions.add(transaction);
        if(Income.class.isInstance(transaction)){
            logIncome(transaction.getAmount());
        }
        else{
            logExpense(transaction.getAmount());
        }
        return true;
    }
    private void logIncome(double amount){
        mBalance+=amount;
    }
    private void logExpense(double amount){
        mBalance-=amount;
    }
}
