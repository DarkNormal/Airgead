package com.marklordan.airgead.ui.account_details;

import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;

import com.marklordan.airgead.R;

public class AccountDetailsActivity extends AppCompatActivity implements AccountDetailsView {

    public static final String EXTRA_BALANCE = "extra_balance";
    private AccountDetailsPresenter mPresenter;

    private EditText mBalanceEditText;

    private double accountBalance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_details);

        accountBalance = getIntent().getDoubleExtra(EXTRA_BALANCE, 0);

        mBalanceEditText = (EditText) findViewById(R.id.balance_edit_text);
        mBalanceEditText.setText("€" + accountBalance);

        mPresenter = new AccountDetailsPresenterImpl();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.onResume();
    }

    @Override
    public void displaySavingsTarget(double savingsTarget) {
        //todo show savings target to user

    }

    @Override
    public void displayBalance(double balance) {
        //todo show balance to user
    }
}
