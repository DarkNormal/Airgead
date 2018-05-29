package com.marklordan.airgead;

import android.util.Log;

import com.marklordan.airgead.db.AirgeadDataSource;
import com.marklordan.airgead.db.AirgeadRepository;
import com.marklordan.airgead.model.AirgeadAccount;
import com.marklordan.airgead.ui.account_details.AccountDetailsPresenter;
import com.marklordan.airgead.ui.account_details.AccountDetailsPresenterImpl;
import com.marklordan.airgead.ui.account_details.AccountDetailsView;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.text.NumberFormat;

import static org.mockito.Mockito.verify;

/**
 * Created by Mark on 16/12/2017.
 */

@RunWith(PowerMockRunner.class)
@PrepareForTest(Log.class)
public class AccountDetailsPresenterTest {

    @Mock
    private AccountDetailsView mDetailsView;

    @Mock
    private AirgeadRepository mRepository;

    private AccountDetailsPresenter mDetailsPresenter;

    @Mock
    private AirgeadAccount mAccount;

    @Captor
    private ArgumentCaptor<AirgeadDataSource.GetDataCallback> mGetDataCallbackArgumentCaptor;

    @Before
    public void setupPresenter(){
        mDetailsPresenter = new AccountDetailsPresenterImpl(mDetailsView, mRepository);
        Mockito.when(mAccount.getBalance()).thenReturn(5000.0);
        Mockito.when(mAccount.getSavingsTarget()).thenReturn(30);
        Mockito.when(mAccount.getSavingsTargetAmount()).thenReturn(1500.0);

    }

    @Test
    public void displayAccountBalanceAndSavingsTargetOnLoad(){
        mDetailsPresenter.onResume();
        NumberFormat nf = NumberFormat.getCurrencyInstance();
        verify(mRepository).getAccountDetails(mGetDataCallbackArgumentCaptor.capture());
        mGetDataCallbackArgumentCaptor.getValue().onAccountLoaded(mAccount);
        verify(mDetailsView).displayBalance(mAccount.getBalance());
        verify(mDetailsView).displaySavingsTarget(nf.format(mAccount.getSavingsTargetAmount()));
    }
}
