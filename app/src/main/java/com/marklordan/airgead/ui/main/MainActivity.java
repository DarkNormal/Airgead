package com.marklordan.airgead.ui.main;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.marklordan.airgead.R;
import com.marklordan.airgead.adapters.TransactionAdapter;
import com.marklordan.airgead.db.AirgeadRepository;
import com.marklordan.airgead.db.LocalDataSource;
import com.marklordan.airgead.model.AirgeadAccount;
import com.marklordan.airgead.model.Transaction;
import com.marklordan.airgead.ui.SetupAccountActivity;
import com.marklordan.airgead.ui.TransactionActivity;

import java.util.List;

public class MainActivity extends AppCompatActivity implements MainView{

    private static final String TAG = MainActivity.class.getSimpleName();
    private AirgeadAccount mAccount;
    private Button mAddExpenseButton, mDeleteExpenseButton;
    private TextView mAccountBalanceTextView;
    private RecyclerView mRecyclerView;
    private ProgressBar mProgressBar;

    private MainPresenter mPresenter;


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

        mRecyclerView = (RecyclerView) findViewById(R.id.recent_transaction_recyclerview);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false));

        mProgressBar = (ProgressBar) findViewById(R.id.transaction_list_progress_bar);

        mPresenter = new MainPresenterImpl(this, new AirgeadRepository(new LocalDataSource(getContentResolver())));


    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume: ");
        mPresenter.onResume();
    }

    @Override
    protected void onDestroy() {
        mPresenter.onDestroy();
        super.onDestroy();

    }

    public void setCurrentBalance(View v) {
        Intent intent = new Intent(MainActivity.this, SetupAccountActivity.class);
        startActivity(intent);
    }

    @Override
    public void setItems(List<Transaction> transactions) {
        mRecyclerView.setAdapter(new TransactionAdapter(this, transactions));
    }

    @Override
    public void showMessage(String message) {

    }

    @Override
    public void showProgress() {
        mProgressBar.setVisibility(View.VISIBLE);
        mRecyclerView.setVisibility(View.INVISIBLE);
    }

    @Override
    public void hideProgress() {
        mProgressBar.setVisibility(View.INVISIBLE);
        mRecyclerView.setVisibility(View.VISIBLE);
    }

    @Override
    public void displayBalance(double balanceAmount) {
        mAccountBalanceTextView.setText(String.valueOf(balanceAmount));
    }
}

