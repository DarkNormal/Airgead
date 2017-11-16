package com.marklordan.airgead.ui.main;

import com.marklordan.airgead.db.AirgeadDataSource;
import com.marklordan.airgead.db.AirgeadRepository;
import com.marklordan.airgead.model.Transaction;

import java.util.List;

/**
 * Created by Mark on 12/11/2017.
 */

public class MainPresenterImpl implements MainPresenter, AirgeadDataSource.GetDataCallback{

    private MainView mMainView;
    private AirgeadRepository mRepository;

    public MainPresenterImpl(MainView mainView, AirgeadRepository repository) {
        this.mMainView = mainView;
        mRepository = repository;
    }

    @Override
    public void onResume() {
        if(mMainView != null){
            //do something here
            mMainView.showProgress();
        }

        mRepository.getAccountBalance(this);
        mRepository.getTransactions();
    }

    @Override
    public void onItemClicked(int position) {
        if(mMainView != null){
            mMainView.showMessage(String.format("Position %d clicked!", position +1));
        }

    }

    @Override
    public void onDestroy() {
        mMainView = null;

    }

    @Override
    public void onBalanceLoaded(double balance) {
        mMainView.displayBalance(balance);
    }

    @Override
    public void onTransactionsLoaded(List<Transaction> transactions) {
        if(mMainView != null){
            mMainView.setItems(transactions);
            mMainView.hideProgress();
        }
    }
}