package com.marklordan.airgead;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

import com.marklordan.airgead.model.AirgeadAccount;

public class MainActivity extends AppCompatActivity {

    private AirgeadAccount mAccount;
    private Button mAddExpenseButton, mDeleteExpenseButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAddExpenseButton = (Button) findViewById(R.id.button_add_expense);
        mDeleteExpenseButton = (Button) findViewById(R.id.button_delete_expense);

    }

    private void setBalance(double amount){
        mAccount.setBalance(amount);
    }
}
