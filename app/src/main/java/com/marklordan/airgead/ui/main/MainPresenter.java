package com.marklordan.airgead.ui.main;

import com.marklordan.airgead.model.Transaction;

/**
 * Created by Mark on 12/11/2017.
 */

public interface MainPresenter {

    void onResume();

    void onItemClicked(int position);

    void onDestroy();

    void onItemRemoved(int position);

    void removeItemFromDb(int position);
}
