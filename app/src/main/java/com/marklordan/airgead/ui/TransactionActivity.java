package com.marklordan.airgead.ui;

import android.content.ContentValues;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.marklordan.airgead.R;
import com.marklordan.airgead.db.AirgeadContract;
import com.marklordan.airgead.model.Expense;
import com.marklordan.airgead.model.Transaction;
import com.marklordan.airgead.model.TransactionCategory;
import com.marklordan.airgead.ui.addTransaction.DatePickerFragment;

import java.util.Calendar;
import java.util.Date;

public class TransactionActivity extends AppCompatActivity implements DatePickerFragment.OnDateSetListener{

    private EditText transactionDescriptionInput;
    private Spinner mCategorySpinner;
    private EditText mTransactionValueEditText;

    private Button addTransactionButton;

    private TextView mTransactionDateTextView;

    private Transaction mCurrentTransaction = new Expense();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction);

        transactionDescriptionInput = (EditText) findViewById(R.id.transaction_desc_input);

        mTransactionValueEditText = (EditText) findViewById(R.id.transaction_value_input);

        //TODO update this to use a DatePicker Dialog instead of entering manually
        mTransactionDateTextView = (TextView) findViewById(R.id.transaction_date_input);


        Button cancelButton = (Button) findViewById(R.id.cancel_transaction_button);
        cancelButton.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                finish();
                                            }
                                        });
                addTransactionButton = (Button) findViewById(R.id.save_transaction_button);
        addTransactionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String enteredAmount = mTransactionValueEditText.getText().toString();
                String enteredTitle = transactionDescriptionInput.getText().toString();
                if(enteredAmount != null && !enteredAmount.isEmpty() && enteredTitle != null && !enteredTitle.isEmpty()){
                    mCurrentTransaction.setAmount(Double.valueOf(enteredAmount));
                    mCurrentTransaction.setDescription(enteredTitle);
                    if(mCurrentTransaction.getDateOfTransaction() == null){
                        mCurrentTransaction.setDateOfTransaction(new Date());
                    }
                    insertTransactionToDb(mCurrentTransaction);
                    finish();
                }
                else{
                    Toast.makeText(TransactionActivity.this, "A transaction needs at least a name and a value!", Toast.LENGTH_SHORT).show();
                }

            }
        });

        mCategorySpinner = (Spinner) findViewById(R.id.transaction_category);
        mCategorySpinner.setAdapter(new ArrayAdapter<TransactionCategory>(this, android.R.layout.simple_list_item_1, TransactionCategory.values()));

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
        calendar.set(year, month,day);
        mCurrentTransaction.setDateOfTransaction(calendar.getTime());
    }
}
