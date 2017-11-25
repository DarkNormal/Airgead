package com.marklordan.airgead.db;

import android.content.ContentResolver;
import android.database.Cursor;
import android.os.Handler;
import android.util.Log;

import com.marklordan.airgead.model.Expense;
import com.marklordan.airgead.model.Income;
import com.marklordan.airgead.model.Transaction;

import java.util.Arrays;
import java.util.Calendar;
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
    public void getAccountBalance(final GetDataCallback callback) {

        double currentBalance = 0;
        String[] projection = {AirgeadContract.AccountTable.Cols.BALANCE};
        Cursor cursor = mContentResolver.query(AirgeadContract.AccountTable.CONTENT_URI,
                projection,
                null,
                null,
                null
        );
        if(cursor == null || cursor.getCount() <= 0){
            return;
        }
        try {
            int balanceIndex = cursor.getColumnIndex(AirgeadContract.AccountTable.Cols.BALANCE);

            cursor.moveToNext();
            currentBalance = cursor.getDouble(balanceIndex);
            Log.d("TransactionInteractorIm", "refreshBalance: " + currentBalance);

        }
        finally {
            cursor.close();
            //transactionCursor.close();
        }

        final double finalCurrentBalance = currentBalance;
        new Handler().postDelayed(new Runnable() {
            @Override public void run() {
                callback.onBalanceLoaded(finalCurrentBalance);
            }
        }, 2000);
    }

    @Override
    public void getTransactions(final GetDataCallback callback) {
        Cursor cursor = mContentResolver.query(AirgeadContract.TransactionTable.CONTENT_URI,
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

        try {
            int amountIndex = cursor.getColumnIndex(AirgeadContract.TransactionTable.Cols.TRANSACTION_AMOUNT);

            while(cursor.moveToNext()){
                Log.i(TAG, "getTransactions: Transaction amount is: " + cursor.getDouble(amountIndex));
            }
        }finally {
            cursor.close();
        }

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                callback.onTransactionsLoaded(createArrayList());
            }
        }, 2000);


    }


    private List<Transaction> createArrayList() {
        return Arrays.asList(
                new Income(2500, Calendar.getInstance().getTime(), null),
                new Income(100, Calendar.getInstance().getTime(), null),
                new Income(1456, Calendar.getInstance().getTime(), null),
                new Income(350, Calendar.getInstance().getTime(), null),
                new Expense(400, Calendar.getInstance().getTime(), null));
    }


}
