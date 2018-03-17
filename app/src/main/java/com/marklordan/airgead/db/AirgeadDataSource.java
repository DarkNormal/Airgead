package com.marklordan.airgead.db;

import android.content.ContentValues;
import android.support.annotation.Nullable;

import com.marklordan.airgead.model.AirgeadAccount;
import com.marklordan.airgead.model.Transaction;

import java.util.Calendar;
import java.util.List;

/**
 * Created by Mark on 14/11/2017.
 */

public interface AirgeadDataSource {

    interface GetDataCallback{
        void onAccountLoaded(AirgeadAccount account);

        void onTransactionsLoaded(@Nullable List<Transaction> transactions);

        void onMonthlyTransactionsLoaded(@Nullable List<Transaction> transactions);

    }

    void getAccountDetails(GetDataCallback callback);

    void getTransactions(GetDataCallback callback);

    void removeTransaction(int transactionId);

    void updateAccountDetails(ContentValues values);

    void updateTransaction(ContentValues values);

    void getTransactionsForMonth(int month, GetDataCallback callback);
}
