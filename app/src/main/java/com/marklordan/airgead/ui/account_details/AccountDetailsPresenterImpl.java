package com.marklordan.airgead.ui.account_details;

import android.content.ContentValues;
import android.support.annotation.Nullable;

import com.marklordan.airgead.db.AirgeadContract;
import com.marklordan.airgead.db.AirgeadDataSource;
import com.marklordan.airgead.db.AirgeadRepository;
import com.marklordan.airgead.model.AirgeadAccount;
import com.marklordan.airgead.model.Transaction;

import java.text.NumberFormat;
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
        double savingsTargetAmount = ((mAccount.getBalance() / 10) * percentage);
        mAccount.setSavingsTarget(savingsTargetAmount);
        mDetailsView.displaySavingsTarget(nf.format(savingsTargetAmount));

    }

    @Override
    public void updateAccountDetails(double currentBalance) {
        ContentValues values = new ContentValues();
        values.put(AirgeadContract.AccountTable.Cols._ID, 1);
        values.put(AirgeadContract.AccountTable.Cols.BALANCE, currentBalance);
        values.put(AirgeadContract.AccountTable.Cols.SAVINGS_TARGET, mAccount.getSavingsTarget());
        mRepository.updateAccountDetails(values);
    }

    @Override
    public void onAccountLoaded(AirgeadAccount account) {
        mAccount = account;
        mDetailsView.displayBalance(account.getBalance());
        mDetailsView.displaySavingsTarget(nf.format(account.getSavingsTarget()));
    }

    @Override
    public void onTransactionsLoaded(@Nullable List<Transaction> transactions) {
        //not used in this presenter
    }
}
