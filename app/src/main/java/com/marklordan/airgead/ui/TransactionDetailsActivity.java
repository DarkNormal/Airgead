package com.marklordan.airgead.ui;

import android.graphics.Color;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.marklordan.airgead.R;
import com.marklordan.airgead.db.AirgeadContract;
import com.marklordan.airgead.model.Transaction;
import com.marklordan.airgead.model.TransactionCategory;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;

public class TransactionDetailsActivity extends AppCompatActivity {

    private TabLayout mTabLayout;
    private EditText mAmountEditText, mDescriptionEditText;
    private TextView mDateTextView;
    private Spinner mCategorySpinner;
    DecimalFormat mDecimalFormat = new DecimalFormat("#0.00");
    private SimpleDateFormat mDateFormat = new SimpleDateFormat("dd/MM/yyyy");
    private Transaction mTransaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle extras = getIntent().getExtras();
        mTransaction = extras.getParcelable("TRANSACTION");
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

        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
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
                getContentResolver().update(AirgeadContract.TransactionTable.CONTENT_URI,
                        mTransaction.transactionToContentValues(),
                        AirgeadContract.TransactionTable.Cols._ID + "= ?",
                        new String[]{String.valueOf(mTransaction.getId())});
            default:
                return super.onOptionsItemSelected(item);
        }

    }
}
