package com.marklordan.airgead;

import com.marklordan.airgead.model.AirgeadAccount;
import com.marklordan.airgead.model.Expense;
import com.marklordan.airgead.model.Income;
import com.marklordan.airgead.model.Transaction;

import org.junit.Assert;
import org.junit.Test;

import java.util.Calendar;


/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void checkAccountBalanceIsSet() {
        AirgeadAccount account = new AirgeadAccount(0,null,0);
        account.setBalance(100);
        Assert.assertEquals(100, account.getBalance(), 0);
    }
    @Test
    public void addTransactionToAccount(){
        AirgeadAccount account = new AirgeadAccount(0,null,0);
        account.addTransaction(new Income(200, Calendar.getInstance().getTime(), null));
        Assert.assertTrue(account.getTransactions().size() > 0);
    }
    @Test
    public void addTransactionCheckBalance(){
        AirgeadAccount account = new AirgeadAccount(0,null,0);
        account.addTransaction(new Expense(200, Calendar.getInstance().getTime(), null));
        Assert.assertEquals(-200,account.getBalance(), 0);
    }

}