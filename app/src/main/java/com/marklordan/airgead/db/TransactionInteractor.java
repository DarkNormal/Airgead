package com.marklordan.airgead.db;

import android.content.ContentResolver;

import com.marklordan.airgead.model.Transaction;

import java.util.List;

/**
 * Created by Mark on 12/11/2017.
 */

public interface TransactionInteractor {

    interface OnFinishedListener{
        void onFinished(List<Transaction> transactions, double balance);
    }

    void findItems(OnFinishedListener listener, ContentResolver contentResolver);
}
