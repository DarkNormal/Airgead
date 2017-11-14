package com.marklordan.airgead.ui.main;

import com.marklordan.airgead.db.TransactionInteractor;
import com.marklordan.airgead.db.AirgeadRepository;
import com.marklordan.airgead.model.Transaction;

import java.util.List;

/**
 * Created by Mark on 12/11/2017.
 */

public class MainPresenterImpl implements MainPresenter, TransactionInteractor.OnFinishedListener {

    private MainView mMainView;
    private TransactionInteractor mInteractor;
    private AirgeadRepository mRepository;

    public MainPresenterImpl(MainView mainView, TransactionInteractor transactionInteractor, AirgeadRepository repository) {
        this.mMainView = mainView;
        this.mInteractor = transactionInteractor;
        mRepository = repository;
    }

    @Override
    public void onResume() {
        if(mMainView != null){
            //do something here
            mMainView.showProgress();
        }

        //TODO move content resovler usage to repository, continue working on this issue
        //mInteractor.findItems(this, mContentResolver);
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
    public void onFinished(List<Transaction> transactions, double balance) {
        if(mMainView != null){
            mMainView.setItems(transactions);
            mMainView.hideProgress();
            mMainView.displayBalance(balance);
        }
    }
}
