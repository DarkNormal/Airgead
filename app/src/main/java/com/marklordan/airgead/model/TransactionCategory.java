package com.marklordan.airgead.model;

/**
 * Created by Mark on 09/11/2017.
 */

public enum TransactionCategory {
    GENERAL("General"),
    FOOD("Food"),
    DRINKS("Drinks"),
    TRANSPORT("Transportation");

    private String mFriendlyName;

    TransactionCategory(String friendlyName){
        this.mFriendlyName = friendlyName;
    }

    @Override
    public String toString() {
        return mFriendlyName;
    }
}
