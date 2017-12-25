package com.marklordan.airgead.db;

import android.support.annotation.Nullable;

import com.marklordan.airgead.model.AirgeadAccount;
import com.marklordan.airgead.model.Transaction;

import java.util.List;

/**
 * Created by Mark on 14/11/2017.
 */

public interface AirgeadDataSource {

    interface GetDataCallback{
        void onAccountLoaded(AirgeadAccount account);

        void onTransactionsLoaded(@Nullable List<Transaction> transactions);

    }

    void getAccountDetails(GetDataCallback callback);

    void getTransactions(GetDataCallback callback);

    void removeTransaction(int transactionId);
}
