package com.marklordan.airgead.ui;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.marklordan.airgead.R;
import com.marklordan.airgead.db.AccountDbHelper;
import com.marklordan.airgead.model.AirgeadAccount;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    private AirgeadAccount mAccount;
    private Button mAddExpenseButton, mDeleteExpenseButton, mSetBalanceButton;
    private TextView mAccountBalanceTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mAccount = new AirgeadAccount();

        mAddExpenseButton = (Button) findViewById(R.id.button_add_expense);
        mAddExpenseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                
            }
        });
        mDeleteExpenseButton = (Button) findViewById(R.id.button_delete_expense);

        mAccountBalanceTextView = (TextView) findViewById(R.id.textview_account_balance);
        mAccountBalanceTextView.setText("Your balance is " + mAccount.getBalance());

        mSetBalanceButton = (Button) findViewById(R.id.button_set_balance);


        SQLiteDatabase mDatabase = new AccountDbHelper(getApplicationContext()).getWritableDatabase();


    }

    @Override
    protected void onResume() {
        super.onResume();

        Log.d(TAG, "onResume: ");
    }

    private void setBalance(double amount){
        mAccount.setBalance(amount);
    }

    public void setCurrentBalance(View v) {
        Intent intent = new Intent(MainActivity.this, SetupAccountActivity.class);
        startActivity(intent);
    }

    }

