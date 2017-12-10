package com.marklordan.airgead.model;

import android.content.ContentValues;
import android.location.Location;
import android.util.Log;

import com.marklordan.airgead.db.AirgeadContract;

import java.util.Date;

public class Expense extends Transaction {

    public Expense(){}

    @Override
    public ContentValues transactionToContentValues() {
        ContentValues values = new ContentValues();
        values.put(AirgeadContract.TransactionTable.Cols.TRANSACTION_AMOUNT, getAmount());
        values.put(AirgeadContract.TransactionTable.Cols.TRANSACTION_TITLE, getDescription());
        values.put(AirgeadContract.TransactionTable.Cols.TRANSACTION_TYPE, true); //true if expense, false if income
        values.put(AirgeadContract.TransactionTable.Cols.TRANSACTION_DATE, getDateOfTransaction().getTime() / 1000);
        values.put(AirgeadContract.TransactionTable.Cols.TRANSACTION_CATEGORY, getCategory().ordinal());
        return values;
    }

    @Override
    public boolean isAnExpense() {
        return true;
    }


    @Override
    public void setAmount(double amount) {
        super.setAmount(amount * -1);
    }

    public Expense(double amount, Date dateOfTransaction, Location locationOfTransaction, String title, int type) {
        super(amount, dateOfTransaction, locationOfTransaction, title);
        setCategory(TransactionCategory.fromInteger(type));

    }
}
