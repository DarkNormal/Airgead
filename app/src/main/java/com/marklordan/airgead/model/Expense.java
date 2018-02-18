package com.marklordan.airgead.model;

import android.content.ContentValues;
import android.location.Location;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import com.marklordan.airgead.db.AirgeadContract;

import java.util.Date;

public class Expense extends Transaction {

    public Expense(){}

    public Expense(Parcel in){
        setId(in.readInt());
        setAmount(in.readDouble());
        setDescription(in.readString());
        setDateOfTransaction(in.readLong());
        setCategory(TransactionCategory.fromInteger(in.readInt()));
    }

    @Override
    public ContentValues transactionToContentValues() {
        ContentValues values = new ContentValues();
        values.put(AirgeadContract.TransactionTable.Cols.TRANSACTION_ID, getId());
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
        if(amount < 0){
            super.setAmount(amount);
        }
        else {
            super.setAmount(amount * -1);
        }
    }

    public Expense(int id, double amount, Date dateOfTransaction, Location locationOfTransaction, String title, int type) {
        super(id, amount, dateOfTransaction, locationOfTransaction, title);
        setCategory(TransactionCategory.fromInteger(type));

    }

    public Expense(double amount, Date dateOfTransaction, Location locationOfTransaction, String title, int type) {
        super(amount, dateOfTransaction, locationOfTransaction, title);
        setCategory(TransactionCategory.fromInteger(type));

    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(getId());
        parcel.writeDouble(getAmount());
        parcel.writeString(getDescription());
        parcel.writeLong(getDateOfTransaction().getTime());
        parcel.writeInt(TransactionCategory.fromCategory(getCategory()));
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public Expense createFromParcel(Parcel in) {
            return new Expense(in);
        }

        public Expense[] newArray(int size) {
            return new Expense[size];
        }
    };
}
