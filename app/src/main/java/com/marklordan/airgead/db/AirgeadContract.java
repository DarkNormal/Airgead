package com.marklordan.airgead.db;

import android.provider.BaseColumns;

/**
 * Created by Mark on 18/06/2017.
 */

public class AirgeadContract {

    public static final class AccountTable{
        public static final String TABLE_NAME = "account";

        public static final class Cols implements BaseColumns{
            public static final String BALANCE = "balance";
            public static final String SAVINGS_TARGET = "target";
        }
    }

}
