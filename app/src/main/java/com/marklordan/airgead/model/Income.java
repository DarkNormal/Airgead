package com.marklordan.airgead.model;

import android.content.ContentValues;
import android.location.Location;

import java.util.Date;

/**
 * Created by Mark on 20/12/2016.
 */

public class Income extends Transaction {
    public Income(double amount, Date dateOfTransaction, Location locationOfTransaction, String title) {
        super(amount, dateOfTransaction, locationOfTransaction, title);
    }

    @Override
    public ContentValues transactionToContentValues() {
        return null;
    }
}
