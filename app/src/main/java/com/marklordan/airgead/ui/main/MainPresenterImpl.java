package com.marklordan.airgead.ui.main;

import android.util.Log;

import com.marklordan.airgead.db.AirgeadDataSource;
import com.marklordan.airgead.db.AirgeadRepository;
import com.marklordan.airgead.model.AirgeadAccount;
import com.marklordan.airgead.model.Income;
import com.marklordan.airgead.model.Transaction;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by Mark on 12/11/2017.
 */

public class MainPresenterImpl implements MainPresenter, AirgeadDataSource.GetDataCallback{

    private MainView mMainView;
    private AirgeadRepository mRepository;
    private List<Transaction> mTransactionList;
    private static final String TAG = MainPresenterImpl.class.getSimpleName();
    private NumberFormat mNumberFormat = NumberFormat.getCurrencyInstance();

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

        mRepository.getAccountDetails(this);
        mRepository.getTransactions(this);
    }

    @Override
    public void onItemClicked(int position) {
        if(mMainView != null){
            //mMainView.showMessage(String.format("Position %d clicked!", position +1));
            mMainView.showTransactionDetails(mTransactionList.get(position));
        }

    }

    @Override
    public void onDestroy() {
        mMainView = null;

    }

    @Override
    public void onItemRemoved(int position) {
        if(mMainView != null){
            mMainView.showRemovedMessage("Transaction removed", mTransactionList.get(position), position);
        }
    }

    @Override
    public void removeItemFromDb(int transactionId) {
        //todo repository call to delete transaction
        Log.d(TAG, "removeItemFromDb: request received from view to delete item");
        mRepository.removeTransaction(transactionId);

    }

    @Override
    public void onAccountLoaded(AirgeadAccount account) {
        if(mMainView != null) {
            mMainView.setAccount(account);
            mMainView.displayBalance(mNumberFormat.format(account.getBalance()), mNumberFormat.format(account.getMonthlyBalance()));
            mMainView.displaySavingsTarget(account.getSavingsTarget() + "%");
            mMainView.displayRemainingBudget(mNumberFormat.format(account.getRemainingBudgetPerDay()) + " / day");
        }
    }

    @Override
    public void onTransactionsLoaded(List<Transaction> transactions) {
        if(mMainView != null){
            if(transactions == null) {
                transactions = new ArrayList<>();
                transactions.add(new Income(0, Calendar.getInstance().getTime(), null, "Sample Income"));

            }
            mTransactionList = transactions;
            mMainView.setItems(transactions);
            mMainView.hideProgress();
        }
    }
}
