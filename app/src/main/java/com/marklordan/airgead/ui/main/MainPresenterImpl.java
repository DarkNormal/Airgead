package com.marklordan.airgead.ui.main;

import android.content.ContentResolver;

import com.marklordan.airgead.db.TransactionInteractor;
import com.marklordan.airgead.model.Transaction;

import java.util.List;

/**
 * Created by Mark on 12/11/2017.
 */

public class MainPresenterImpl implements MainPresenter, TransactionInteractor.OnFinishedListener {

    private MainView mMainView;
    private TransactionInteractor mInteractor;
    private ContentResolver mContentResolver;

    public MainPresenterImpl(MainView mainView, TransactionInteractor transactionInteractor, ContentResolver contentResolver) {
        this.mMainView = mainView;
        this.mInteractor = transactionInteractor;
        mContentResolver = contentResolver;
    }

    @Override
    public void onResume() {
        if(mMainView != null){
            //do something here
            mMainView.showProgress();
        }

        mInteractor.findItems(this, mContentResolver);
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
