package com.marklordan.airgead.model;

import android.content.ContentValues;
import android.location.Location;
import android.os.Parcelable;

import com.marklordan.airgead.db.AirgeadContract;

import java.util.Date;

/**
 * Created by Mark on 20/12/2016.
 */

public abstract class Transaction implements Parcelable{


    private int mId;
    private double mAmount;
    private Date mDateOfTransaction;
    private Location mLocationOfTransaction;
    private TransactionCategory mCategory;
    private String mDescription;

    public Transaction(){};


    public Transaction(int id, double amount, Date dateOfTransaction, Location locationOfTransaction, String description) {
        mId = id;
        mAmount = amount;
        mDateOfTransaction = dateOfTransaction;
        mLocationOfTransaction = locationOfTransaction;
        mDescription = description;
        mCategory = TransactionCategory.GENERAL;
    }

    public Transaction(double amount, Date dateOfTransaction, Location locationOfTransaction, String description) {
        mAmount = amount;
        mDateOfTransaction = dateOfTransaction;
        mLocationOfTransaction = locationOfTransaction;
        mDescription = description;
        mCategory = TransactionCategory.GENERAL;
    }

    public int getId() {
        return mId;
    }

    public double getAmount() {
        return mAmount;
    }

    public void setAmount(double amount) {
        mAmount = amount;
    }

    public Date getDateOfTransaction() {
        return mDateOfTransaction;
    }

    public void setDateOfTransaction(Date dateOfTransaction) {
        mDateOfTransaction = dateOfTransaction;
    }

    public void setDateOfTransaction(long dateOfTransaction) {
        mDateOfTransaction = new Date(dateOfTransaction);
    }

    public Location getLocationOfTransaction() {
        return mLocationOfTransaction;
    }

    public void setLocationOfTransaction(Location locationOfTransaction) {
        mLocationOfTransaction = locationOfTransaction;
    }

    public TransactionCategory getCategory() {
        return mCategory;
    }

    public void setCategory(TransactionCategory category) {
        mCategory = category;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        mDescription = description;
    }

    public abstract ContentValues transactionToContentValues();

    public abstract boolean isAnExpense();
}
