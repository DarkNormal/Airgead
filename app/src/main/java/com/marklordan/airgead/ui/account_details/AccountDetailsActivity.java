package com.marklordan.airgead.ui.account_details;

import android.content.ContentValues;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;

import com.marklordan.airgead.R;
import com.marklordan.airgead.db.AirgeadContract;
import com.marklordan.airgead.db.AirgeadRepository;
import com.marklordan.airgead.db.LocalDataSource;

public class AccountDetailsActivity extends AppCompatActivity implements AccountDetailsView {

    private AccountDetailsPresenter mPresenter;

    private EditText mBalanceEditText, mSavingsTargetEditText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_details);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mBalanceEditText = (EditText) findViewById(R.id.balance_edit_text);

        mSavingsTargetEditText = (EditText) findViewById(R.id.savings_target_edit_text);

        mPresenter = new AccountDetailsPresenterImpl(this, new AirgeadRepository(new LocalDataSource(getContentResolver())));
        mPresenter.onResume();
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
        switch(item.getItemId()){
            case R.id.confirm_item:
                updateAccountDetails();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void displaySavingsTarget(double savingsTarget) {
        mSavingsTargetEditText.setText(String.valueOf(savingsTarget));

    }

    @Override
    public void displayBalance(double balance) {
        mBalanceEditText.setText(String.valueOf(balance));
    }


    private void updateAccountDetails(){
        //TODO implement updating details here
        ContentValues values = new ContentValues();
        values.put(AirgeadContract.AccountTable.Cols._ID, 1);
        values.put(AirgeadContract.AccountTable.Cols.BALANCE, Double.parseDouble(mBalanceEditText.getText().toString()));
        values.put(AirgeadContract.AccountTable.Cols.SAVINGS_TARGET, Double.parseDouble(mSavingsTargetEditText.getText().toString()));
        getContentResolver().update(AirgeadContract.AccountTable.CONTENT_URI,values, AirgeadContract.AccountTable.Cols.ACCOUNT_ID + "= ?", new String[]{"1"});

        finish();
    }
}
