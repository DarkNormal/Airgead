package com.marklordan.airgead.db;

import android.content.ContentValues;

/**
 * Created by Mark on 14/11/2017.
 */

public class AirgeadRepository implements AirgeadDataSource {

    private final LocalDataSource mDataSource;

    public AirgeadRepository(LocalDataSource localDataSource){

        this.mDataSource = localDataSource;
    }


    @Override
    public void getAccountDetails(GetDataCallback callback) {
        mDataSource.getAccountDetails(callback);
    }

    @Override
    public void getTransactions(GetDataCallback callback) {
        mDataSource.getTransactions(callback);
    }

    @Override
    public void getRecentTransactions(int numTransactionsToRetrieve, GetDataCallback callback) {
        mDataSource.getRecentTransactions(numTransactionsToRetrieve, callback);
    }

    @Override
    public void removeTransaction(int transactionId) {
        mDataSource.removeTransaction(transactionId);
    }

    @Override
    public void updateAccountDetails(ContentValues values) {
        mDataSource.updateAccountDetails(values);
    }

    @Override
    public void updateTransaction(ContentValues values) {
        mDataSource.updateTransaction(values);
    }

    @Override
    public void getTransactionsForMonth(int month, GetDataCallback callback) {
        mDataSource.getTransactionsForMonth(month, callback);
    }

}
