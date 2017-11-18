package com.marklordan.airgead;

import com.marklordan.airgead.db.AirgeadRepository;
import com.marklordan.airgead.model.Transaction;
import com.marklordan.airgead.ui.main.MainPresenterImpl;
import com.marklordan.airgead.ui.main.MainView;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;

/**
 * Created by Mark on 17/11/2017.
 */

public class MainPresenterTest {

    @Mock
    private MainView mMainView;

    @Mock
    private AirgeadRepository mRepository;

    private MainPresenterImpl mMainPresenter;


    @Before
    public void setupPresenter(){

        MockitoAnnotations.initMocks(this);

        mMainPresenter = new MainPresenterImpl(mMainView, mRepository);
    }


    @Test
    public void showProgressBarInitially(){
        mMainPresenter.onResume();

        verify(mMainView).showProgress();
    }

    @Test
    public void onItemClickedDisplayToast(){
        mMainPresenter.onItemClicked(0);

        verify(mMainView).showMessage(any(String.class));
    }

    @Test
    public void onBalanceLoadedUpdateUI(){
        mMainPresenter.onBalanceLoaded(any(Double.class));

        verify(mMainView).displayBalance(any(Double.class));
    }

    @Test
    public void onTransactionsLoadedUpdateUI(){
        mMainPresenter.onTransactionsLoaded(new ArrayList<Transaction>());

        verify(mMainView).hideProgress();
        verify(mMainView).setItems(new ArrayList<Transaction>());
    }

}
