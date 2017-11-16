package com.marklordan.airgead.db;

import com.marklordan.airgead.model.Transaction;

import java.util.List;

/**
 * Created by Mark on 14/11/2017.
 */

public interface AirgeadDataSource {

    interface GetDataCallback{
        void onBalanceLoaded(double balance);

        void onTransactionsLoaded(List<Transaction> transactions);
    }

    void getAccountBalance(GetDataCallback callback);

    List<Transaction> getTransactions();
}
