package com.marklordan.airgead.model;

import android.content.ContentValues;
import android.location.Location;
import android.os.Parcel;
import android.os.Parcelable;

import com.marklordan.airgead.db.AirgeadContract;

import java.util.Date;

/**
 * Created by Mark on 20/12/2016.
 */

public class Income extends Transaction {

    public Income(int id,double amount, Date dateOfTransaction, Location locationOfTransaction, String title, int category) {
        super(id, amount, dateOfTransaction, locationOfTransaction, title);
        setCategory(TransactionCategory.fromInteger(category));
    }

    public Income(double amount, Date dateOfTransaction, Location locationOfTransaction, String title) {
        super(amount, dateOfTransaction, locationOfTransaction, title);
    }

    public Income() {

    }

    public Income(Parcel in){
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
        values.put(AirgeadContract.TransactionTable.Cols.TRANSACTION_TYPE, false); //true if expense, false if income
        values.put(AirgeadContract.TransactionTable.Cols.TRANSACTION_DATE, getDateOfTransaction().getTime() / 1000);
        values.put(AirgeadContract.TransactionTable.Cols.TRANSACTION_CATEGORY, getCategory().ordinal());
        return values;
    }

    @Override
    public boolean isAnExpense() {
        return false;
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
        public Income createFromParcel(Parcel in) {
            return new Income(in);
        }

        public Income[] newArray(int size) {
            return new Income[size];
        }
    };
}
