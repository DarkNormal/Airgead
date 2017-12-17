package com.marklordan.airgead.ui.main;

import com.marklordan.airgead.db.AirgeadDataSource;
import com.marklordan.airgead.db.AirgeadRepository;
import com.marklordan.airgead.model.AirgeadAccount;
import com.marklordan.airgead.model.Income;
import com.marklordan.airgead.model.Transaction;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Mark on 12/11/2017.
 */

public class MainPresenterImpl implements MainPresenter, AirgeadDataSource.GetDataCallback{

    private MainView mMainView;
    private AirgeadRepository mRepository;
    private List<Transaction> mTransactionList;

    public MainPresenterImpl(MainView mainView, AirgeadRepository repository) {
        this.mMainView = mainView;
        mRepository = repository;
    }

    @Override
    public void onResume() {
        if(mMainView != null){
            //do something here
            mMainView.showProgress();
        }

        mRepository.getAccountDetails(this);
        mRepository.getTransactions(this);
    }

    @Override
    public void onItemClicked(int position) {
        if(mMainView != null){
            mMainView.showMessage(String.format("Position %d clicked!", position +1));
        }

    }

    @Override
    public void onDestroy() {
        mMainView = null;

    }

    @Override
    public void onItemRemoved(int position) {
        if(mMainView != null){
            mMainView.showRemovedMessage("Transaction removed", mTransactionList.get(position), position);
        }
    }

    @Override
    public void removeItemFromDb(int position) {
        //todo repository call to delete transaction
    }

    @Override
    public void onAccountLoaded(AirgeadAccount account) {
        if(mMainView != null) {
            mMainView.setAccount(account);
            mMainView.displayBalance(account.getBalance());
        }
    }

    @Override
    public void onTransactionsLoaded(List<Transaction> transactions) {
        if(mMainView != null){
            if(transactions == null) {
                transactions = new ArrayList<>();
                transactions.add(new Income(0, new Date(), null, "Sample Income"));
            }
                mTransactionList = transactions;
                //only set items in RecyclerView if there are some there, otherwise skip
                mMainView.setItems(transactions);

            //TODO show 'No transactions yet' or similar message if transaction list is empty
            mMainView.hideProgress();
        }
    }
}
