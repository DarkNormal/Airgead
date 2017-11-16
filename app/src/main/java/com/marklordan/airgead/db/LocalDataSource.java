package com.marklordan.airgead.db;

import android.content.ContentResolver;
import android.database.Cursor;
import android.os.Handler;
import android.util.Log;

import com.marklordan.airgead.model.Transaction;

import java.util.List;

/**
 * Created by Mark on 16/11/2017.
 */

public class LocalDataSource implements AirgeadDataSource{

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
    public List<Transaction> getTransactions() {
        return null;
    }
}
