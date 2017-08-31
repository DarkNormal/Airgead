package com.marklordan.airgead.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Mark on 18/06/2017.
 */

public class AccountDbHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Airgead.db";

    public AccountDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + AirgeadContract.AccountTable.TABLE_NAME + "(" +
                AirgeadContract.AccountTable.Cols._ID + " INTEGER PRIMARY KEY," +
                AirgeadContract.AccountTable.Cols.ACCOUNT_ID + " INTEGER NOT NULL," +
                AirgeadContract.AccountTable.Cols.SAVINGS_TARGET + " NUMBER," +
                AirgeadContract.AccountTable.Cols.BALANCE + " NUMBER)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
