package com.marklordan.airgead.ui;

import android.content.ContentValues;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.marklordan.airgead.R;
import com.marklordan.airgead.db.AirgeadContract;

public class SetupAccountActivity extends AppCompatActivity {

    EditText currentBalanceEditText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup_account);

        currentBalanceEditText = (EditText) findViewById(R.id.incomeEditText);
    }


    public void confirmBalanceDetails(View v){
        ContentValues values = new ContentValues();
        values.put(AirgeadContract.AccountTable.Cols.BALANCE, Double.parseDouble(currentBalanceEditText.getText().toString()));
        values.put(AirgeadContract.AccountTable.Cols.SAVINGS_TARGET, 0);
        getContentResolver().insert(AirgeadContract.AccountTable.CONTENT_URI,values);
        finish();
    }
}
