package com.marklordan.airgead.ui.main;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.SQLException;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.NotificationCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.marklordan.airgead.R;
import com.marklordan.airgead.adapter.TransactionAdapter;
import com.marklordan.airgead.db.AirgeadContract;
import com.marklordan.airgead.db.AirgeadRepository;
import com.marklordan.airgead.db.LocalDataSource;
import com.marklordan.airgead.model.Transaction;
import com.marklordan.airgead.ui.SettingsActivity;
import com.marklordan.airgead.ui.SwipeToDelete;
import com.marklordan.airgead.ui.TransactionActivity;
import com.marklordan.airgead.ui.TransactionDetailsActivity;
import com.marklordan.airgead.ui.account_details.AccountDetailsActivity;

import java.util.List;

public class MainActivity extends AppCompatActivity implements MainView, TransactionAdapter.TransactionClickListener{

    private static final String TAG = MainActivity.class.getSimpleName();
    private FloatingActionButton mAddTransactionBtn;
    private TextView mAccountBalanceTextView, mSavingsTargetTextView, mRemainingBudgetTextView, mMonthlyBalanceTextView;
    private CardView mAccountBalanceCardView;
    private RecyclerView mRecyclerView;
    private ProgressBar mProgressBar;
    private DividerItemDecoration mDividerItemDecoration;
    private LinearLayoutManager mLinearLayoutManager;

    private MainPresenter mPresenter;
    private TransactionAdapter mAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAddTransactionBtn = (FloatingActionButton) findViewById(R.id.add_transaction_fab);
        mAddTransactionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, TransactionActivity.class);
                startActivityForResult(intent, 101);
            }
        });

        mAccountBalanceTextView = (TextView) findViewById(R.id.textview_account_balance);
        mMonthlyBalanceTextView = (TextView) findViewById(R.id.textview_account_monthly_balance);

        mAccountBalanceCardView = (CardView) findViewById(R.id.balance_cardview);
        mAccountBalanceCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAccountDetails();
            }
        });

        mSavingsTargetTextView = (TextView) findViewById(R.id.savings_target_value_textview);
        mRemainingBudgetTextView = (TextView) findViewById(R.id.remaining_balance_value_textview);

        mRecyclerView = (RecyclerView) findViewById(R.id.recent_transaction_recyclerview);
        mLinearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false);
        mDividerItemDecoration = new DividerItemDecoration(mRecyclerView.getContext(), mLinearLayoutManager.getOrientation());
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        mRecyclerView.addItemDecoration(mDividerItemDecoration);

        SwipeToDelete swipeHelper = new SwipeToDelete(this){
            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();
                mPresenter.onItemRemoved(position);
                mAdapter.removeItem(position);

            }
        };
        new ItemTouchHelper(swipeHelper).attachToRecyclerView(mRecyclerView);

        mProgressBar = (ProgressBar) findViewById(R.id.transaction_list_progress_bar);
        mProgressBar.setVisibility(View.GONE);

        mPresenter = new MainPresenterImpl(this, new AirgeadRepository(new LocalDataSource(getContentResolver())));

        SharedPreferences preferences = this.getPreferences(Context.MODE_PRIVATE);
        boolean hasAccountBeenCreated = preferences.getBoolean(getString(R.string.account_initialised_key), false);
        if(!hasAccountBeenCreated) {
            Log.d(TAG, "account has not been initialised yet, creating account..");
            initialiseAccount();
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume: ");
        mPresenter.onResume();
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop: called");
    }

    @Override
    protected void onDestroy() {
        mPresenter.onDestroy();
        super.onDestroy();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.settings:
                Intent intent = new Intent(this, SettingsActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void setItems(List<Transaction> transactions) {
        mAdapter = new TransactionAdapter(this, transactions, this);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showRemovedMessage(String message, final Transaction transaction, final int position) {
        Snackbar snackbar = Snackbar.make(findViewById(R.id.main_coordinator_layout), message, Snackbar.LENGTH_SHORT);
        snackbar.setAction("Undo", new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                mAdapter.restoreItem(transaction, position);
            }
        }).addCallback(new Snackbar.Callback(){
            @Override
            public void onDismissed(Snackbar transientBottomBar, int event) {
                mPresenter.removeItemFromDb(transaction.getId());
            }
        });
        snackbar.show();
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
    public void displayBalance(String balanceAmount) {
        mAccountBalanceTextView.setText(balanceAmount);

    }

    @Override
    public void displayMonthlyBalance(String balanceAmount) {
        mMonthlyBalanceTextView.setText(balanceAmount);
    }

    @Override
    public void displaySavingsTarget(String savingsTargetAmount) {
        mSavingsTargetTextView.setText(savingsTargetAmount);
    }

    @Override
    public void displayRemainingBudget(String remainingBudget) {
        mRemainingBudgetTextView.setText(remainingBudget);
    }

    @Override
    public void showAccountDetails() {
        Intent intent = new Intent(this, AccountDetailsActivity.class);
        startActivity(intent);
    }

    @Override
    public void showTransactionDetails(Transaction t) {
        Intent i = new Intent(this, TransactionDetailsActivity.class);
        i.putExtra("TRANSACTION", t);
        startActivity(i);
    }

    @Override
    public void onItemClicked(int itemPosition) {
        mPresenter.onItemClicked(itemPosition);
    }

    private void initialiseAccount() {
        ContentValues values = new ContentValues();
        values.put(AirgeadContract.AccountTable.Cols._ID, 1);
        values.put(AirgeadContract.AccountTable.Cols.ACCOUNT_ID, 1);
        values.put(AirgeadContract.AccountTable.Cols.BALANCE, 0);
        values.put(AirgeadContract.AccountTable.Cols.SAVINGS_TARGET, 0);
        try {
            getContentResolver().insert(AirgeadContract.AccountTable.CONTENT_URI, values);
            SharedPreferences preferences = this.getPreferences(Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putBoolean(getString(R.string.account_initialised_key), true);
            editor.apply();
            Log.i(TAG, "account_initialised; writing value to shared preferences");
        }
        catch(SQLException ex){
            Log.d(TAG, "initialiseUSerAccountOnDb: " + ex.getMessage());
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == Activity.RESULT_OK){
            Log.d(TAG, "onActivityResult from : RESULT OK");

            final Snackbar snackbar = Snackbar.make(findViewById(R.id.main_coordinator_layout), "Transaction added", Snackbar.LENGTH_SHORT);
            snackbar.setAction("DISMISS", new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    //no implementation needed here
                }
            });
            snackbar.show();
        }


    }
}

