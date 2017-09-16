package com.marklordan.airgead.db;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.util.Log;

public class AccountContentProvider extends ContentProvider {

    private static AccountDbHelper mDbHelper;
    public static final String TAG = AccountContentProvider.class.getSimpleName();


    public AccountContentProvider() {
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        // Implement this to handle requests to delete one or more rows.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public String getType(Uri uri) {
        // TODO: Implement this to handle requests for the MIME type of the data
        // at the given URI.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {

        Uri returnUri;
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        long id = db.insert(AirgeadContract.AccountTable.TABLE_NAME,null, values);

        returnUri = ContentUris.withAppendedId(AirgeadContract.AccountTable.CONTENT_URI, id);
        if(id > 0 == false){
            Log.d(AccountContentProvider.class.getSimpleName(), "insert: failed");
        }
        else Log.d(AccountContentProvider.class.getSimpleName(), "insert: successful");

        return returnUri;
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

        Cursor returnCursor = db.query(
                AirgeadContract.AccountTable.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                sortOrder);

        return returnCursor;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        int count = db.update(AirgeadContract.AccountTable.TABLE_NAME, values, selection, selectionArgs);
        Log.d(TAG, "update: rows updated:" + count);
        return count;
    }
}
