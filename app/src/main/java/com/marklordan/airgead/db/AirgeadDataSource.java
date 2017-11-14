package com.marklordan.airgead.db;

import com.marklordan.airgead.model.Transaction;

import java.util.List;

/**
 * Created by Mark on 14/11/2017.
 */

public interface AirgeadDataSource {

    void getAccountBalance();

    List<Transaction> getTransactions();
}
