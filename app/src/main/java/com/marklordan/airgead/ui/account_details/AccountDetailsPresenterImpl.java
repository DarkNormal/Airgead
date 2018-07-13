package com.marklordan.airgead.ui.account_details;

import android.content.ContentValues;
import android.support.annotation.Nullable;

import com.marklordan.airgead.db.AirgeadContract;
import com.marklordan.airgead.db.AirgeadDataSource;
import com.marklordan.airgead.db.AirgeadRepository;
import com.marklordan.airgead.model.AirgeadAccount;
import com.marklordan.airgead.model.Transaction;

import java.text.NumberFormat;
import java.util.Calendar;
import java.util.List;

public class AccountDetailsPresenterImpl implements AccountDetailsPresenter, AirgeadDataSource.GetDataCallback {

    private AccountDetailsView mDetailsView;
    private AirgeadRepository mRepository;
    private AirgeadAccount mAccount;
    NumberFormat nf = NumberFormat.getCurrencyInstance();

    public AccountDetailsPresenterImpl(AccountDetailsView detailsView, AirgeadRepository repository) {
        this.mDetailsView = detailsView;
        mRepository = repository;
    }

    @Override
    public void onResume() {
        mRepository.getAccountDetails(this);
        mRepository.getTransactionsForMonth(Calendar.getInstance().get(Calendar.MONTH), this);
    }

    @Override
    public void onSavingsTargetSelected(double savingsTarget) {

    }

    @Override
    public void onBalanceSet(double balance) {

    }

    @Override
    public void onDestroy() {

    }

    @Override
    public void calculateSavingsTarget(int percentage) {
        double savingsTargetAmount = ((mAccount.getMonthlyBalance() / 10) * percentage);
        mAccount.setSavingsTargetAmount(savingsTargetAmount);
        mAccount.setSavingsTarget(percentage * 10);
        mDetailsView.displaySavingsTarget(nf.format(savingsTargetAmount));

    }

    @Override
    public void updateAccountDetails(double currentBalance) {
        ContentValues values = new ContentValues();
        values.put(AirgeadContract.AccountTable.Cols._ID, 1);
        values.put(AirgeadContract.AccountTable.Cols.BALANCE, currentBalance);
        values.put(AirgeadContract.AccountTable.Cols.SAVINGS_TARGET, mAccount.getSavingsTarget());
        values.put(AirgeadContract.AccountTable.Cols.SAVINGS_TARGET_AMT, mAccount.getSavingsTargetAmount());
        mRepository.updateAccountDetails(values);
    }

    @Override
    public void onAccountLoaded(AirgeadAccount account) {
        mAccount = account;
        mDetailsView.displayBalance(account.getBalance());
        mDetailsView.displaySavingsTarget(nf.format(account.getSavingsTargetAmount()));
        mDetailsView.displaySavingsTargetPercentage(account.getSavingsTarget() / 10);
    }

    @Override
    public void onTransactionsLoaded(@Nullable List<Transaction> transactions) {
        //not used in this presenter
    }

    @Override
    public void onMonthlyTransactionsLoaded(@Nullable List<Transaction> transactions) {
        if(mDetailsView != null){
            if(transactions == null) {
                return;
            }
            double monthlyTotal = 0;
            for (Transaction t : transactions){
                monthlyTotal += t.getAmount();
            }

            mAccount.setMonthlyBalance(monthlyTotal);
            mDetailsView.setMonthlyBalance(nf.format(monthlyTotal));
        }
    }

    @Override
    public void onRecentTransactionsLoaded(@Nullable List<Transaction> transactions) {
        //not used
    }
}
