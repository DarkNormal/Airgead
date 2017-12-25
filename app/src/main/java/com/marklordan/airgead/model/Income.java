package com.marklordan.airgead.model;

import android.content.ContentValues;
import android.location.Location;

import com.marklordan.airgead.db.AirgeadContract;

import java.util.Date;

/**
 * Created by Mark on 20/12/2016.
 */

public class Income extends Transaction {

    public Income(int id,double amount, Date dateOfTransaction, Location locationOfTransaction, String title) {
        super(id, amount, dateOfTransaction, locationOfTransaction, title);
    }

    public Income(double amount, Date dateOfTransaction, Location locationOfTransaction, String title) {
        super(amount, dateOfTransaction, locationOfTransaction, title);
    }

    public Income() {

    }

    @Override
    public ContentValues transactionToContentValues() {
        ContentValues values = new ContentValues();
        values.put(AirgeadContract.TransactionTable.Cols.TRANSACTION_AMOUNT, getAmount());
        values.put(AirgeadContract.TransactionTable.Cols.TRANSACTION_TITLE, getDescription());
        values.put(AirgeadContract.TransactionTable.Cols.TRANSACTION_TYPE, false); //true if expense, false if income
        values.put(AirgeadContract.TransactionTable.Cols.TRANSACTION_DATE, getDateOfTransaction().getTime() / 1000);
        values.put(AirgeadContract.TransactionTable.Cols.TRANSACTION_CATEGORY, getCategory().ordinal());
        return values;
    }

    @Override
    public boolean isAnExpense() {
        return false;
    }
}
