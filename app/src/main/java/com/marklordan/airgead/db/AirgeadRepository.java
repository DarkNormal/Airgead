package com.marklordan.airgead.db;

import com.marklordan.airgead.model.Transaction;

import java.util.List;

/**
 * Created by Mark on 14/11/2017.
 */

public class AirgeadRepository implements AirgeadDataSource {

    private final LocalDataSource mDataSource;

    public AirgeadRepository(LocalDataSource localDataSource){

        this.mDataSource = localDataSource;
    }


    @Override
    public void getAccountBalance(GetDataCallback callback) {
        mDataSource.getAccountBalance(callback);
    }

    @Override
    public List<Transaction> getTransactions() {
        return null;
    }

}
