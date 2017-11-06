package com.marklordan.airgead.ui;

import android.content.ContentValues;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.marklordan.airgead.R;
import com.marklordan.airgead.db.AirgeadContract;

public class TransactionActivity extends AppCompatActivity {

    private EditText transactionDescriptionInput;
    private Boolean isAnExpense;
    private EditText transactionValueTextView;
    private double transactionValue;
    private String transactionTitle;
    private Switch transactionTypeSwitch;

    private Button addTransactionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction);

        transactionDescriptionInput = (EditText) findViewById(R.id.transaction_desc_input);

        boolean isExpense = getIntent().getBooleanExtra(getString(R.string.is_expense_transaction), false);
        displayExpenseOrIncomeTransactionDetails(isExpense);

        transactionTypeSwitch = (Switch) findViewById(R.id.transaction_type_switch);
        transactionTypeSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    isAnExpense = true;
                }
                else
                    isAnExpense = false;
            }
        });

        transactionValueTextView = (EditText) findViewById(R.id.transaction_value_input);

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
                String enteredAmount = transactionValueTextView.getText().toString();
                String enteredTitle = transactionDescriptionInput.getText().toString();
                if(enteredAmount != null && !enteredAmount.isEmpty() && enteredTitle != null && !enteredTitle.isEmpty()){
                    transactionValue = Double.valueOf(enteredAmount);
                    transactionTitle = enteredTitle;
                    insertTransactionToDb();
                }
                else{
                    Toast.makeText(TransactionActivity.this, "A transaction needs at least a name and a value!", Toast.LENGTH_SHORT).show();
                }

            }
        });



        //TODO save transaction to DB table - need to create table too
        //TODO apply transaction to account balance
        // info for transaction - type (enum?), name, date incurred, amount



    }

    private void displayExpenseOrIncomeTransactionDetails(boolean isExpense){
        String expenseTypeToDisplay;
        if(isExpense){
            expenseTypeToDisplay = "Expense ";
        }
        else{
            expenseTypeToDisplay = "Income ";
        }

        transactionDescriptionInput.setHint(String.format(getString(R.string.transaction_description), expenseTypeToDisplay));
    }

    /**
     * Insert an Expense or an Income to the local database via Content Provider
     *
     */
    private void insertTransactionToDb(){
        //TODO method on model should do this POJO -> ContentValues work
        ContentValues values = new ContentValues();
        values.put(AirgeadContract.TransactionTable.Cols.TRANSACTION_AMOUNT, transactionValue);
        values.put(AirgeadContract.TransactionTable.Cols.TRANSACTION_TITLE, transactionTitle);
        values.put(AirgeadContract.TransactionTable.Cols.TRANSACTION_TYPE, isAnExpense);

        getContentResolver().insert(AirgeadContract.TransactionTable.CONTENT_URI, values);
    }
}
