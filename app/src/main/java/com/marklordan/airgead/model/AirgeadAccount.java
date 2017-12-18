package com.marklordan.airgead.model;

import java.util.ArrayList;
import java.util.List;

public class AirgeadAccount {
    private double mBalance;
    private List<Transaction> mTransactions;
    private double mSavingsTarget;
    private Currency mChosenCurrency;

    public AirgeadAccount() {
        mTransactions = new ArrayList<>();
    }

    public AirgeadAccount(double balance, double savingsTarget) {
        this();
        mBalance = balance;
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

    public boolean performTransaction(Transaction transaction){
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

    public Currency getCurrency(){
        return mChosenCurrency;
    }

    public void setCurrency(Currency chosenCurrency){
        mChosenCurrency = chosenCurrency;

    }

    public double getRemainingBudget(){
        return getBalance() - getSavingsTarget();
    }
    
    public Transaction getLatestTransactionAdded() {
        List<Transaction> transactionList = getTransactions();
        Transaction latestTransaction = transactionList.get(0);
        for (int i = 1; i < transactionList.size(); i++) {
            if (transactionList.get(i).getDateOfTransaction().after(latestTransaction.getDateOfTransaction())){
                latestTransaction = transactionList.get(i);
            }
        }
        return latestTransaction;
    }
}
