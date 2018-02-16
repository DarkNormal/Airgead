package com.marklordan.airgead.ui.account_details;

import android.content.ContentValues;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;

import com.marklordan.airgead.R;
import com.marklordan.airgead.db.AirgeadContract;
import com.marklordan.airgead.db.AirgeadRepository;
import com.marklordan.airgead.db.LocalDataSource;

import java.text.DecimalFormat;

public class AccountDetailsActivity extends AppCompatActivity implements AccountDetailsView {

    private AccountDetailsPresenter mPresenter;

    private EditText mBalanceEditText;
    private SeekBar mSavingsTargetSeekBar;
    private TextView mSavingsAmount, mSavingsTargetValue;

    private static final String TAG = AccountDetailsActivity.class.getSimpleName();
    DecimalFormat mDecimalFormat = new DecimalFormat("#.00");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_details);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mBalanceEditText = (EditText) findViewById(R.id.balance_edit_text);
        mSavingsAmount = (TextView) findViewById(R.id.savings_target_amount);
        mSavingsTargetValue = (TextView) findViewById(R.id.savings_target_value);

        mSavingsTargetSeekBar = (SeekBar) findViewById(R.id.savingsTargetSeekBar);

        mSavingsTargetSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                Log.d(TAG, "onProgressChanged: " + progress);
                mPresenter.calculateSavingsTarget(progress);
                updateSavingsTargetPercentage(progress);

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

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
    public void displaySavingsTarget(String savingsTarget) {
        mSavingsAmount.setText("Target amount to save: " + savingsTarget);
    }

    @Override
    public void displayBalance(double balance) {
        mBalanceEditText.setText(mDecimalFormat.format(balance));
    }

    @Override
    public void displaySavingsTargetPercentage(double savingsTargetPercentage) {
        updateSavingsTargetPercentage(savingsTargetPercentage);
    }


    private void updateAccountDetails(){
        //TODO send this to the presenter task
        mPresenter.updateAccountDetails(Double.parseDouble(mBalanceEditText.getText().toString()));
        finish();
    }

    private void updateSavingsTargetPercentage(double savingsTarget){
        mSavingsTargetValue.setText((int)savingsTarget * 10 + "%");
        mSavingsTargetSeekBar.setProgress((int)savingsTarget);
    }
}
