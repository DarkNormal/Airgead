package com.marklordan.airgead;

import com.marklordan.airgead.model.AirgeadAccount;
import com.marklordan.airgead.model.Expense;
import com.marklordan.airgead.model.Income;


import org.junit.Assert;
import org.junit.Test;

import java.util.Calendar;

import static com.marklordan.airgead.model.Currency.*;


/**
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 *
 */
public class AccountUnitTest {
    @Test
    public void createAccountWithInputtedOptions(){
        AirgeadAccount account = new AirgeadAccount();
        account.setCurrency(EURO);
        Assert.assertEquals(EURO, account.getCurrency());
    }

    @Test
    public void checkAccountBalanceIsSet() {
        AirgeadAccount account = new AirgeadAccount(0,0);
        account.setBalance(100);
        Assert.assertEquals(100, account.getBalance(), 0);
    }
    @Test
    public void addTransactionToAccount(){
        AirgeadAccount account = new AirgeadAccount(0,0);
        account.addTransaction(new Income(200, Calendar.getInstance().getTime(), null));
        Assert.assertTrue(account.getTransactions().size() > 0);
        Assert.assertEquals(200,account.getBalance(), 0);

    }
    @Test
    public void addTransactionCheckBalance(){
        AirgeadAccount account = new AirgeadAccount(0,0);
        account.addTransaction(new Expense(200, Calendar.getInstance().getTime(), null));
        Assert.assertEquals(-200,account.getBalance(), 0);
    }

    @Test
    public void checkSetSavingsTarget(){
        AirgeadAccount account = new AirgeadAccount();
        account.setSavingsTarget(1000);

        Assert.assertEquals(1000, account.getSavingsTarget(), 0);
    }
}