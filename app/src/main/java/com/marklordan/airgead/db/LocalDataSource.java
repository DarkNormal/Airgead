package com.marklordan.airgead.db;

import android.content.ContentResolver;
import android.database.Cursor;
import android.os.Handler;
import android.util.Log;

import com.marklordan.airgead.model.AirgeadAccount;
import com.marklordan.airgead.model.Expense;
import com.marklordan.airgead.model.Income;
import com.marklordan.airgead.model.Transaction;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by Mark on 16/11/2017.
 */

public class LocalDataSource implements AirgeadDataSource{

    private static final String TAG = LocalDataSource.class.getSimpleName();

    private ContentResolver mContentResolver;

    public LocalDataSource(ContentResolver contentResolver){
        this.mContentResolver = contentResolver;
    }

    @Override
    public void getAccountDetails(final GetDataCallback callback) {

        final AirgeadAccount account;
        Cursor cursor = mContentResolver.query(AirgeadContract.AccountTable.CONTENT_URI,
                null,
                null,
                null,
                null
        );
        if(cursor == null || cursor.getCount() <= 0){
            return;
        }
        try {
            int balanceIndex = cursor.getColumnIndex(AirgeadContract.AccountTable.Cols.BALANCE);
            int savingstargetIndex = cursor.getColumnIndex(AirgeadContract.AccountTable.Cols.SAVINGS_TARGET);

            cursor.moveToNext();
            account = new AirgeadAccount(cursor.getDouble(balanceIndex), cursor.getDouble(savingstargetIndex));

        }
        finally {
            cursor.close();
            //transactionCursor.close();
        }

        callback.onAccountLoaded(account);
    }

    @Override
    public void getTransactions(final GetDataCallback callback) {
        final Cursor cursor = mContentResolver.query(AirgeadContract.TransactionTable.CONTENT_URI,
                null,
                null,
                null,
                null);

        if(cursor == null || cursor.getCount() <= 0){
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    callback.onTransactionsLoaded(null);
                }
            }, 2000);

            return;
        }

        callback.onTransactionsLoaded(createArrayList(cursor));


    }


    private List<Transaction> createArrayList(Cursor cursor) {
        ArrayList<Transaction> transactionList = new ArrayList<>();
        int idIndex = cursor.getColumnIndex(AirgeadContract.TransactionTable.Cols._ID);
        int amountIndex = cursor.getColumnIndex(AirgeadContract.TransactionTable.Cols.TRANSACTION_AMOUNT);
        int titleIndex = cursor.getColumnIndex(AirgeadContract.TransactionTable.Cols.TRANSACTION_TITLE);
        int dateIndex = cursor.getColumnIndex(AirgeadContract.TransactionTable.Cols.TRANSACTION_DATE);
        int typeIndex = cursor.getColumnIndex(AirgeadContract.TransactionTable.Cols.TRANSACTION_CATEGORY);

        boolean isAnExpense;

        while(cursor.moveToNext()){
            double amount = cursor.getDouble(amountIndex);
            int id = cursor.getInt(idIndex);
            String title = cursor.getString(titleIndex);
            long date = cursor.getLong(dateIndex) * 1000;
            int type = cursor.getInt(typeIndex);
            isAnExpense = amount < 0;
            Transaction transaction;
            if(isAnExpense)
                transaction = new Expense(amount, new Date(date), null, title, type);
            else
                transaction = new Income(amount, new Date(date), null, title);

            transactionList.add(transaction);
        }
        cursor.close();
        return transactionList;
    }


}
