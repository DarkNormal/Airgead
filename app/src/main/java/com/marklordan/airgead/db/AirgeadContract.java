package com.marklordan.airgead.db;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by Mark on 18/06/2017.
 */

public class AirgeadContract {

    public static final String AUTHORITY = "com.marklordan.airgead";

    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + AUTHORITY);

    public static final String PATH_ACCOUNT = "airgeadAccount";

    public static final class AccountTable{
        public static final String TABLE_NAME = "account";

        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_ACCOUNT).build();

        public static final class Cols implements BaseColumns{
            public static final String ACCOUNT_ID = "accountId";
            public static final String BALANCE = "balance";
            public static final String SAVINGS_TARGET = "target";
        }
    }
    public static final class WalletTable{
        public static final String TABLE_NAME = "tbwallet";
        //TODO Separate wallet details from account table
        //once the basic content provider is working
    }

}
