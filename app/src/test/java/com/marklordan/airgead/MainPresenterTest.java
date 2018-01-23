package com.marklordan.airgead;

import android.util.Log;

import com.marklordan.airgead.db.AirgeadDataSource;
import com.marklordan.airgead.db.AirgeadRepository;
import com.marklordan.airgead.model.AirgeadAccount;
import com.marklordan.airgead.model.Expense;
import com.marklordan.airgead.model.Income;
import com.marklordan.airgead.model.Transaction;
import com.marklordan.airgead.ui.main.MainPresenterImpl;
import com.marklordan.airgead.ui.main.MainView;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;

/**
 * Created by Mark on 17/11/2017.
 */

@RunWith(PowerMockRunner.class)
@PrepareForTest(Log.class)
public class MainPresenterTest {

    private static Transaction[] mRawTransactions = new Transaction[2];
    private static ArrayList<Transaction> mTransactions;


    @Mock
    private MainView mMainView;

    @Mock
    private AirgeadRepository mRepository;

    private MainPresenterImpl mMainPresenter;

    @Captor
    private ArgumentCaptor<AirgeadDataSource.GetDataCallback> mGetTransactionsCallbackCaptor;


    @Before
    public void setupPresenter(){

        MockitoAnnotations.initMocks(this);

        mMainPresenter = new MainPresenterImpl(mMainView, mRepository);

        PowerMockito.mockStatic(Log.class);

        mRawTransactions = new Transaction[]{new Income(2500, Calendar.getInstance().getTime(), null, "Test"),
                new Expense(100, Calendar.getInstance().getTime(), null, "Test", 0)};
        mTransactions = new ArrayList<>(Arrays.asList(mRawTransactions));
    }


    @Test
    public void showProgressBarWhenLoadingTransactions(){
        //given
        mMainPresenter.onResume();

        //when
        verify(mRepository).getTransactions(mGetTransactionsCallbackCaptor.capture());
        mGetTransactionsCallbackCaptor.getValue().onTransactionsLoaded(mTransactions);

        //then
        InOrder inOrder = Mockito.inOrder(mMainView);
        inOrder.verify(mMainView).showProgress();
        inOrder.verify(mMainView).hideProgress();
        verify(mMainView).setItems(mTransactions);
        verify(mMainView).showProgress();
    }

    @Test
    public void onItemClickedDisplayToast(){
        mMainPresenter.onResume();

        verify(mRepository).getTransactions(mGetTransactionsCallbackCaptor.capture());
        mGetTransactionsCallbackCaptor.getValue().onTransactionsLoaded(mTransactions);

        mMainPresenter.onItemClicked(0);
        verify(mMainView).showTransactionDetails(mTransactions.get(0));
    }

    @Test
    public void onBalanceLoadedUpdateUI(){
        mMainPresenter.onAccountLoaded(new AirgeadAccount(5000, 3000));

        verify(mMainView).displayBalance(any(String.class));
    }

    @Test
    public void onTransactionsLoadedUpdateUI(){

        //given / when
        mMainPresenter.onTransactionsLoaded(mTransactions);

        //then
        verify(mMainView).hideProgress();
        verify(mMainView).setItems(mTransactions);
    }

    @Test
    public void onItemSwipeToDeleteSendMessageToView(){
        int position = 0;

        //given
        mMainPresenter.onTransactionsLoaded(mTransactions);

        //when
        mMainPresenter.onItemRemoved(position);

        //then
        verify(mMainView).showRemovedMessage("Transaction removed", mTransactions.get(position), position);
    }

}
