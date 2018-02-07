package com.marklordan.airgead.model;

/**
 * Created by Mark on 09/11/2017.
 */

public enum TransactionCategory {
    GENERAL("General"),
    FOOD("Food"),
    DRINKS("Drinks"),
    TRANSPORT("Transportation"),
    HOME("Home/Rent");

    private String mFriendlyName;

    TransactionCategory(String friendlyName){
        this.mFriendlyName = friendlyName;
    }

    @Override
    public String toString() {
        return mFriendlyName;
    }

    public static TransactionCategory fromInteger(int x){
        switch(x){
            case 0:
                return GENERAL;
            case 1:
                return FOOD;
            case 2:
                return DRINKS;
            case 3:
                return TRANSPORT;
            case 4:
                return HOME;
        }
        return null;
    }
}
