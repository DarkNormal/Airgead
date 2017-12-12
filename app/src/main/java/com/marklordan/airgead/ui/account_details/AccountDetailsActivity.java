package com.marklordan.airgead.ui.account_details;

import android.content.ContentValues;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;

import com.marklordan.airgead.R;
import com.marklordan.airgead.db.AirgeadContract;

public class AccountDetailsActivity extends AppCompatActivity implements AccountDetailsView {

    public static final String EXTRA_BALANCE = "extra_balance";
    private AccountDetailsPresenter mPresenter;

    private EditText mBalanceEditText, mSavingsTargetEditText;

    private double accountBalance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_details);

        accountBalance = getIntent().getDoubleExtra(EXTRA_BALANCE, 0);

        mBalanceEditText = (EditText) findViewById(R.id.balance_edit_text);
        mBalanceEditText.setText("€" + accountBalance);

        mSavingsTargetEditText = (EditText) findViewById(R.id.savings_target_edit_text);

        mPresenter = new AccountDetailsPresenterImpl();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.onResume();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_transaction, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void displaySavingsTarget(double savingsTarget) {
        //todo show savings target to user

    }

    @Override
    public void displayBalance(double balance) {
        //todo show balance to user
    }


    private void updateAccountDetails(){
        //TODO implement updating details here
        ContentValues values = new ContentValues();
        values.put(AirgeadContract.AccountTable.Cols._ID, 1);
        values.put(AirgeadContract.AccountTable.Cols.BALANCE, Double.parseDouble(mBalanceEditText.getText().toString()));
        values.put(AirgeadContract.AccountTable.Cols.SAVINGS_TARGET, mSavingsTargetEditText.getText().toString());
        getContentResolver().update(AirgeadContract.AccountTable.CONTENT_URI,values, AirgeadContract.AccountTable.Cols.ACCOUNT_ID + "= ?", new String[]{"1"});
    }
}
