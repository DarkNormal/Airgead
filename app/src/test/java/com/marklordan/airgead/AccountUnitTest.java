package com.marklordan.airgead;

import com.marklordan.airgead.model.AirgeadAccount;
import com.marklordan.airgead.model.Expense;
import com.marklordan.airgead.model.Income;


import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Calendar;
import java.util.Date;

import static com.marklordan.airgead.model.Currency.*;


/**
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 *
 */
public class AccountUnitTest {

    AirgeadAccount account;

    @Before
    public void setupBasicAccount(){
        account = new AirgeadAccount(0,0);
    }
    @Test
    public void createAccountWithSelectedCurrency(){
        account.setCurrency(EURO);
        Assert.assertEquals(EURO, account.getCurrency());
    }

    @Test
    public void setCurrentSavingsOnAccount() {
        account.setBalance(100);
        Assert.assertEquals(100, account.getBalance(), 0);
    }
    @Test
    public void addTransactionToAccount(){
        account.performTransaction(new Income(200, Calendar.getInstance().getTime(), null));
        Assert.assertTrue(account.getTransactions().size() > 0);
        Assert.assertEquals(200,account.getBalance(), 0);

    }
    @Test
    public void addTransactionCheckBalance(){
        account.performTransaction(new Expense(200, Calendar.getInstance().getTime(), null));
        Assert.assertEquals(-200,account.getBalance(), 0);
    }

    @Test
    public void checkSetSavingsTarget(){
        account.setSavingsTarget(1000);

        Assert.assertEquals(1000, account.getSavingsTarget(), 0);
    }


    //Returns the most recent transaction, based on date of occurrence
    @Test
    public void getMostRecentTransaction(){
        Expense recentTransaction = new Expense(200, Calendar.getInstance().getTime(), null);

        account.performTransaction(new Expense(145, generateDate(2017, 6, 1), null));
        account.performTransaction(new Income(52, generateDate(2017, 6, 6), null));
        account.performTransaction(recentTransaction);
        account.performTransaction(new Expense(947, generateDate(2017, 5, 15), null));


        Assert.assertEquals(recentTransaction, account.getLatestTransactionAdded());
    }


    /**
     * Helper Method for returning a Date
     *
     * @param year
     * @param month
     * @param day
     * @return Date object based on parameters
     */

    private Date generateDate(int year, int month, int day){
        Calendar dateOfTransaction = Calendar.getInstance();
        dateOfTransaction.set(Calendar.YEAR, year);
        dateOfTransaction.set(Calendar.MONTH, month);
        dateOfTransaction.set(Calendar.DAY_OF_MONTH, day);
        return dateOfTransaction.getTime();
    }
}