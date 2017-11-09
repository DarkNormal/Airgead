package com.marklordan.airgead.model;

import android.location.Location;

import java.util.Date;

/**
 * Created by Mark on 20/12/2016.
 */

public class Expense extends Transaction {

    public Expense(){};

    public Expense(double amount, Date dateOfTransaction, Location locationOfTransaction) {
        super(amount, dateOfTransaction, locationOfTransaction, null);

    }
}
