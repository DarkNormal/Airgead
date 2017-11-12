package com.marklordan.airgead.ui;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.marklordan.airgead.R;
import com.marklordan.airgead.adapters.TransactionAdapter;
import com.marklordan.airgead.db.AirgeadContract;
import com.marklordan.airgead.model.AirgeadAccount;
import com.marklordan.airgead.model.Expense;
import com.marklordan.airgead.model.Income;
import com.marklordan.airgead.model.Transaction;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    private AirgeadAccount mAccount;
    private Button mAddExpenseButton, mDeleteExpenseButton;
    private TextView mAccountBalanceTextView;
    private RecyclerView mRecyclerView;

    private Transaction[] dummyTransactions = new Transaction[]{
            new Income(2500, Calendar.getInstance().getTime(), null),
            new Income(100, Calendar.getInstance().getTime(), null),
            new Income(1456, Calendar.getInstance().getTime(), null),
            new Income(350, Calendar.getInstance().getTime(), null),
            new Expense(400, Calendar.getInstance().getTime(), null)
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);




        mAccount = new AirgeadAccount();

        // TODO SETUP SEPARATE TRANSACTION (INCOME / EXPENSE) OPTIONS
        mAddExpenseButton = (Button) findViewById(R.id.button_add_expense);
        mAddExpenseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, TransactionActivity.class);
                startActivity(intent);
            }
        });
        mDeleteExpenseButton = (Button) findViewById(R.id.button_delete_expense);

        mAccountBalanceTextView = (TextView) findViewById(R.id.textview_account_balance);
        mAccountBalanceTextView.setText("Your balance is " + mAccount.getBalance());

        mRecyclerView = (RecyclerView) findViewById(R.id.recent_transaction_recyclerview);
        mRecyclerView.setAdapter(new TransactionAdapter(this, Arrays.asList(dummyTransactions)));
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false));



    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume: ");
        refreshBalance();
    }


    /**
     * Retrieves the balance of the account, and applies any transactions to it
     *
     *
     */
    private void refreshBalance() {

        String[] projection = {AirgeadContract.AccountTable.Cols.BALANCE};
        Cursor cursor = getContentResolver().query(
                AirgeadContract.AccountTable.CONTENT_URI,
                projection,
                null,
                null,
                null,
                null
                );

        if(cursor == null || cursor.getCount() <= 0){
            return;
        }
        try {
            int balanceIndex = cursor.getColumnIndex(AirgeadContract.AccountTable.Cols.BALANCE);

            cursor.moveToNext();
            double currentBalance = cursor.getDouble(balanceIndex);
            Log.d(TAG, "refreshBalance: " + currentBalance);

            mAccountBalanceTextView.setText(String.valueOf(currentBalance));
        }
        finally {
            cursor.close();
            //transactionCursor.close();
        }
    }

    public void setCurrentBalance(View v) {
        Intent intent = new Intent(MainActivity.this, SetupAccountActivity.class);
        startActivity(intent);
    }

    }

