package com.marklordan.airgead.model;

import android.location.Location;

import java.util.Date;

/**
 * Created by Mark on 20/12/2016.
 */

public abstract class Transaction {

    private double mAmount;
    private Date mDateOfTransaction;
    private Location mLocationOfTransaction;

    public Transaction(double amount, Date dateOfTransaction, Location locationOfTransaction) {
        mAmount = amount;
        mDateOfTransaction = dateOfTransaction;
        mLocationOfTransaction = locationOfTransaction;
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

    public Location getLocationOfTransaction() {
        return mLocationOfTransaction;
    }

    public void setLocationOfTransaction(Location locationOfTransaction) {
        mLocationOfTransaction = locationOfTransaction;
    }

    //TODO contentValues method for easier use in application
}
