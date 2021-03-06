package com.marklordan.airgead.model;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class AirgeadAccount {
    private double mBalance;
    private List<Transaction> mTransactions;
    private int mSavingsTarget;
    private double mSavingsTargetAmount;
    private double mMonthlyBalance;
    private Currency mChosenCurrency;
    private boolean mIsNewAccount;

    public AirgeadAccount() {
        mTransactions = new ArrayList<>();
        mIsNewAccount = true;
    }

    public AirgeadAccount(double balance,double monthlyBalance, int savingsTarget, double savingsTargetAmount) {
        this();
        mBalance = balance;
        mSavingsTarget = savingsTarget;
        mMonthlyBalance = monthlyBalance;
        mSavingsTargetAmount = savingsTargetAmount;

    }

    public double getBalance() {
        return mBalance;
    }

    public void setBalance(double balance) {
        mBalance = balance;
    }
    public double getMonthlyBalance() {
        return mMonthlyBalance;
    }

    public void setMonthlyBalance(double monthlyBalance) {
        mMonthlyBalance = monthlyBalance;
    }

    public List<Transaction> getTransactions() {
        return mTransactions;
    }

    public void setTransactions(List<Transaction> transactions) {
        mTransactions = transactions;
    }


    public int getSavingsTarget() {
        return mSavingsTarget;
    }

    public void setSavingsTarget(int savingsTarget) {
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
        return getMonthlyBalance() - getSavingsTargetAmount();
    }

    public double getRemainingBudgetPerDay(){
        Calendar cal = Calendar.getInstance();
        int daysRemainingInMonth = cal.getActualMaximum(Calendar.DAY_OF_MONTH) - cal.get(Calendar.DAY_OF_MONTH);
        return  getRemainingBudget() / daysRemainingInMonth;
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

    public boolean isNewAccount(){
        return mIsNewAccount;
        //todo - to be used for a new user
    }

    public double getSavingsTargetAmount() {
        return mSavingsTargetAmount;
    }

    public void setSavingsTargetAmount(double savingsTargetAmount) {
        mSavingsTargetAmount = savingsTargetAmount;
    }
}
