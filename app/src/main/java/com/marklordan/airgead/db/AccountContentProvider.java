package com.marklordan.airgead.db;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.util.Log;

import com.marklordan.airgead.model.AirgeadAccount;

import java.util.Calendar;

public class AccountContentProvider extends ContentProvider {

    private static AccountDbHelper mDbHelper;
    public static final String TAG = AccountContentProvider.class.getSimpleName();

    public static final int ACCOUNT = 100;
    public static final int TRANSACTION = 101;

    //Uri matcher to differentiate logic for tables
    private static final UriMatcher sUriMatcher = buildUriMatcher();


    public AccountContentProvider() {

    }

    public static UriMatcher buildUriMatcher() {

        // Initialize a UriMatcher with no matches by passing in NO_MATCH to the constructor
        UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

        // Add custom Uri, in this case, 1 for each table
        uriMatcher.addURI(AirgeadContract.AUTHORITY, AirgeadContract.PATH_ACCOUNT, ACCOUNT);
        uriMatcher.addURI(AirgeadContract.AUTHORITY, AirgeadContract.PATH_TRANSACTION, TRANSACTION);

        return uriMatcher;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        // Implement this to handle requests to delete one or more rows.
        int match = sUriMatcher.match(uri);
        int deleteReturnValue;
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        switch(match){
            case TRANSACTION:
                deleteReturnValue = db.delete(AirgeadContract.TransactionTable.TABLE_NAME, "_id=?", selectionArgs);
                break;
            default:
                deleteReturnValue = 0;
        }
        return deleteReturnValue;
    }

    @Override
    public String getType(Uri uri) {
        // TODO: Implement this to handle requests for the MIME type of the data
        // at the given URI.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {


        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        int match = sUriMatcher.match(uri);
        long id = -1; //-1 if an error occurred or if the insert was not performed

        //switch for account or transaction insert
        switch(match){
            case ACCOUNT:
                id = db.insert(AirgeadContract.AccountTable.TABLE_NAME, null, values);
                break;
            case TRANSACTION:
                id = db.insert(AirgeadContract.TransactionTable.TABLE_NAME, null, values);
                double transactionAmount = values.getAsDouble(AirgeadContract.TransactionTable.Cols.TRANSACTION_AMOUNT);
                long dateOfTransaction = values.getAsLong(AirgeadContract.TransactionTable.Cols.TRANSACTION_DATE);
                long currentTime = Calendar.getInstance().getTimeInMillis();
                if(dateOfTransaction <= currentTime) {
                    applyTransactionToBalance(transactionAmount);
                }
                else{
                    //TODO upcoming expenses
                }

        }


        if(id < 0){
            Log.d(AccountContentProvider.class.getSimpleName(), "insert: failed");
        }
        else Log.d(AccountContentProvider.class.getSimpleName(), "insert: successful");

        return ContentUris.withAppendedId(AirgeadContract.AccountTable.CONTENT_URI, id);
    }

    @Override
    public boolean onCreate() {

        Context context = getContext();
        mDbHelper = new AccountDbHelper(context);
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        int match = sUriMatcher.match(uri);
        Cursor returnCursor = null;
        switch(match){
            case ACCOUNT:
                returnCursor = db.query(
                    AirgeadContract.AccountTable.TABLE_NAME,
                    projection,
                    selection,
                    selectionArgs,
                    null,
                    null,
                    sortOrder);
                break;
            case TRANSACTION:
                returnCursor = db.query(
                        AirgeadContract.TransactionTable.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder);
                break;
        }
        return returnCursor;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        int match = sUriMatcher.match(uri);
        int count;
        switch (match){
            case ACCOUNT:
                count = db.update(AirgeadContract.AccountTable.TABLE_NAME, values, selection, selectionArgs);
                break;
            case TRANSACTION:
                //TODO update transaction does not update account balance
                count = db.update(AirgeadContract.TransactionTable.TABLE_NAME, values, selection, selectionArgs);
                break;
             default:
                count = -1;
        }
        Log.d(TAG, "update: rows updated:" + count);
        return count;
    }


    private void applyTransactionToBalance(double transactionAmount){
        ContentValues values = new ContentValues();
        double currentBalance = 0;
        String[] projection = {AirgeadContract.AccountTable.Cols.BALANCE};
        Cursor cursor = query(
                AirgeadContract.AccountTable.CONTENT_URI,
                projection,
                null,
                null,
                null,
                null
        );
        while(cursor.moveToNext()){
            int balanceIndex = cursor.getColumnIndex(AirgeadContract.AccountTable.Cols.BALANCE);
            currentBalance = cursor.getDouble(balanceIndex);
        }
        cursor.close();
        //TODO changing balance here doesn't differentiate between income / expense
        double newBalance = currentBalance + transactionAmount;
        values.put(AirgeadContract.AccountTable.Cols.BALANCE, newBalance);
        update(AirgeadContract.AccountTable.CONTENT_URI,values, AirgeadContract.AccountTable.Cols.ACCOUNT_ID + "= ?", new String[]{"1"});

    }
}
