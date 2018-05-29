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

    public static int fromCategory(TransactionCategory t){
        switch (t){
            case GENERAL:
                return 0;
            case FOOD:
                return 1;
            case DRINKS:
                return 2;
            case TRANSPORT:
                return 3;
            case HOME:
                return 4;
        }
        return 0;
    }
}
