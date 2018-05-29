package com.marklordan.airgead.ui;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.marklordan.airgead.R;
import com.marklordan.airgead.db.AirgeadContract;
import com.marklordan.airgead.model.Expense;
import com.marklordan.airgead.model.Income;
import com.marklordan.airgead.model.Transaction;
import com.marklordan.airgead.model.TransactionCategory;
import com.marklordan.airgead.ui.addTransaction.DatePickerFragment;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TransactionActivity extends AppCompatActivity implements DatePickerFragment.OnDateSetListener{

    private static final String TAG = "TransactionActivity";

    private EditText transactionDescriptionInput;
    private Spinner mCategorySpinner;
    private EditText mTransactionValueEditText;
    private TextView mTransactionDateTextView;
    private SimpleDateFormat mDateFormat = new SimpleDateFormat("dd/MM/yyyy");

    private Transaction mCurrentTransaction = new Expense();

    private TabLayout mTabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction);

        Toolbar toolbar = (Toolbar) findViewById(R.id.add_transaction_toolbar);
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setSubtitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        transactionDescriptionInput = (EditText) findViewById(R.id.transaction_desc_input);

        mTransactionValueEditText = (EditText) findViewById(R.id.transaction_value_input);
        mTransactionDateTextView = (TextView) findViewById(R.id.transaction_date_input);


        mCategorySpinner = (Spinner) findViewById(R.id.transaction_category);
        mCategorySpinner.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, TransactionCategory.values()));

        mTabLayout = (TabLayout) findViewById(R.id.tabs);
        mTabLayout.addTab(mTabLayout.newTab().setText("Expense"));
        mTabLayout.addTab(mTabLayout.newTab().setText("Income"));

        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                Log.d(TAG, "onTabSelected: " + tab.getPosition());
                switch (tab.getPosition()){
                    case 0:
                        mCurrentTransaction = new Expense();
                        break;
                    case 1:
                        mCurrentTransaction = new Income();
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_transaction, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case android.R.id.home:
                setResult(RESULT_CANCELED);
                finish();
                return false;
            case R.id.confirm_item:
                addTransaction();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void addTransaction(){
        String enteredAmount = mTransactionValueEditText.getText().toString();
        String enteredTitle = transactionDescriptionInput.getText().toString();
        if(enteredAmount != null && !enteredAmount.isEmpty() && enteredTitle != null && !enteredTitle.isEmpty()){
            mCurrentTransaction.setAmount(Double.valueOf(enteredAmount));
            mCurrentTransaction.setDescription(enteredTitle);
            mCurrentTransaction.setCategory(TransactionCategory.fromInteger(mCategorySpinner.getSelectedItemPosition()));
            if(mCurrentTransaction.getDateOfTransaction() == null){
                mCurrentTransaction.setDateOfTransaction(new Date());
            }
            insertTransactionToDb(mCurrentTransaction);
            setResult(RESULT_OK);
            finish();
        }
        else{
            Toast.makeText(TransactionActivity.this, "A transaction needs at least a name and a value!", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Insert an Expense or an Income to the local database via Content Provider
     *
     */
    private void insertTransactionToDb(Transaction transaction){
        getContentResolver().insert(AirgeadContract.TransactionTable.CONTENT_URI, transaction.transactionToContentValues());
    }

    public void showDateDialog(View v) {
        DialogFragment dateFragment = new DatePickerFragment();
        dateFragment.show(getSupportFragmentManager(), "datepicker");
    }

    @Override
    public void onDateSet(int year, int month, int day) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day);
        mCurrentTransaction.setDateOfTransaction(calendar.getTime());
        mTransactionDateTextView.setText(mDateFormat.format(calendar.getTime()));
    }
}
