package com.marklordan.airgead.ui.main;

import android.support.annotation.Nullable;
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
    private AirgeadAccount mAccount;

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

        //get transactions for the current month
        int month = Calendar.getInstance().get(Calendar.MONTH);
        mRepository.getTransactionsForMonth(month, this);
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
        mAccount = account;
        if(mMainView != null) {
            mMainView.displayBalance(mNumberFormat.format(account.getBalance()));
            mMainView.displaySavingsTarget(account.getSavingsTarget() + "%");


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

    @Override
    public void onMonthlyTransactionsLoaded(@Nullable List<Transaction> transactions) {
        if(mMainView != null){
            if(transactions == null) {
                return;
            }
            double monthlyTotal = 0;
            for (Transaction t : transactions){
                monthlyTotal += t.getAmount();
            }

            mMainView.displayMonthlyBalance(mNumberFormat.format(monthlyTotal));
            mAccount.setMonthlyBalance(monthlyTotal);
            mMainView.displayRemainingBudget(mNumberFormat.format(mAccount.getRemainingBudgetPerDay()) + " / day");
        }
    }
}
