package com.marklordan.airgead.ui;

import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.AutoCompleteTextView;

import com.marklordan.airgead.R;

public class TransactionActivity extends AppCompatActivity {

    private TextInputLayout transactionDescriptionInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction);

        transactionDescriptionInput = (TextInputLayout) findViewById(R.id.transaction_desc_input_wrapper);

        boolean isExpense = getIntent().getBooleanExtra(getString(R.string.is_expense_transaction), false);
        displayExpenseOrIncomeTransactionDetails(isExpense);

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
}
