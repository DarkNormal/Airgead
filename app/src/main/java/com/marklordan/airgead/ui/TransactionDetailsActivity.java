package com.marklordan.airgead.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.marklordan.airgead.R;
import com.marklordan.airgead.model.Transaction;

public class TransactionDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle extras = getIntent().getExtras();
        Transaction t = extras.getParcelable("TRANSACTION");
        setContentView(R.layout.activity_transaction_details);
    }
}
