package com.marklordan.airgead.ui.account_details;

import android.support.annotation.Nullable;

import com.marklordan.airgead.db.AirgeadDataSource;
import com.marklordan.airgead.db.AirgeadRepository;
import com.marklordan.airgead.model.AirgeadAccount;
import com.marklordan.airgead.model.Transaction;

import java.util.List;

public class AccountDetailsPresenterImpl implements AccountDetailsPresenter, AirgeadDataSource.GetDataCallback {

    private AccountDetailsView mDetailsView;
    private AirgeadRepository mRepository;

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
    public void onAccountLoaded(AirgeadAccount account) {
        mDetailsView.displayBalance(account.getBalance());
        mDetailsView.displaySavingsTarget(account.getSavingsTarget());
    }

    @Override
    public void onTransactionsLoaded(@Nullable List<Transaction> transactions) {
        //not used in this presenter
    }
}
