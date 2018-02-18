package com.marklordan.airgead.ui;

import android.graphics.Color;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TransactionDetailsActivity extends AppCompatActivity {

    private TabLayout mTabLayout;
    private EditText mAmountEditText, mDescriptionEditText;
    private TextView mDateTextView;
    private Spinner mCategorySpinner;
    DecimalFormat mDecimalFormat = new DecimalFormat("#0.00");
    private SimpleDateFormat mDateFormat = new SimpleDateFormat("dd/MM/yyyy");
    private Transaction mTransaction;
    private int mTransactionId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle extras = getIntent().getExtras();
        mTransaction = extras.getParcelable("TRANSACTION");
        mTransactionId = mTransaction.getId();
        setContentView(R.layout.activity_transaction);

        mDescriptionEditText = (EditText) findViewById(R.id.transaction_desc_input);
        mAmountEditText = (EditText) findViewById(R.id.transaction_value_input);
        mDateTextView = (TextView) findViewById(R.id.transaction_date_input);

        mCategorySpinner = (Spinner) findViewById(R.id.transaction_category);
        mCategorySpinner.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, TransactionCategory.values()));

        Toolbar toolbar = (Toolbar) findViewById(R.id.add_transaction_toolbar);
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setSubtitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        mTabLayout = (TabLayout) findViewById(R.id.tabs);
        mTabLayout.addTab(mTabLayout.newTab().setText("Expense"));
        mTabLayout.addTab(mTabLayout.newTab().setText("Income"));
        TabLayout.Tab incomeTab = mTabLayout.getTabAt(1);
        if(!mTransaction.isAnExpense()){
            incomeTab.select();
        }
        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()){
                    case 0:
                        mTransaction = new Expense();
                        break;
                    case 1:
                        mTransaction = new Income();
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

        bindData();
    }

    private void bindData(){
        mAmountEditText.setText(mDecimalFormat.format(mTransaction.getAmount()));
        mDescriptionEditText.setText(mTransaction.getDescription());
        mDateTextView.setText(mDateFormat.format(mTransaction.getDateOfTransaction()));
        int cat = TransactionCategory.fromCategory(mTransaction.getCategory());
        mCategorySpinner.setSelection(cat, true);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_transaction, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.confirm_item:
                //TODO update item
                String enteredAmount = mAmountEditText.getText().toString();
                String enteredTitle = mDescriptionEditText.getText().toString();
                if(!enteredAmount.isEmpty() && !enteredTitle.isEmpty()) {
                    mTransaction.setAmount(Double.valueOf(enteredAmount));
                    mTransaction.setDescription(enteredTitle);
                    mTransaction.setCategory(TransactionCategory.fromInteger(mCategorySpinner.getSelectedItemPosition()));
                    if (mTransaction.getDateOfTransaction() == null) {
                        mTransaction.setDateOfTransaction(new Date());
                    }
                    mTransaction.setId(mTransactionId);
                    getContentResolver().update(AirgeadContract.TransactionTable.CONTENT_URI,
                            mTransaction.transactionToContentValues(),
                            AirgeadContract.TransactionTable.Cols._ID + "= ?",
                            new String[]{String.valueOf(mTransaction.getId())});
                    finish();
                }
                else{
                    Toast.makeText(TransactionDetailsActivity.this, "A transaction needs at least a name and a value!", Toast.LENGTH_SHORT).show();
                }
            default:
                return super.onOptionsItemSelected(item);
        }

    }
}
