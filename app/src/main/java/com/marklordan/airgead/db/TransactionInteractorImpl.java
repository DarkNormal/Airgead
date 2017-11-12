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
 * Created by Mark on 12/11/2017.
 */

public class TransactionInteractorImpl implements TransactionInteractor {


    @Override
    public void findItems(final OnFinishedListener listener, ContentResolver contentResolver) {
        double currentBalance = 0;
        String[] projection = {AirgeadContract.AccountTable.Cols.BALANCE};
        Cursor cursor = contentResolver.query(AirgeadContract.AccountTable.CONTENT_URI,
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
                listener.onFinished(createArrayList(), finalCurrentBalance);
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
