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
    public static final String PATH_TRANSACTION = "airgeadTransaction";

    public static final class AccountTable{
        public static final String TABLE_NAME = "tbaccount";

        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_ACCOUNT).build();

        public static final class Cols implements BaseColumns{
            public static final String ACCOUNT_ID = "accountId";
            public static final String BALANCE = "total_balance";
            public static final String PERIOD_BALANCE = "period_balance";
            public static final String SAVINGS_TARGET = "target";
            public static final String SAVINGS_TARGET_AMT = "target_amt";
        }
    }
    public static final class WalletTable{
        public static final String TABLE_NAME = "tbwallet";
        //TODO Separate wallet details from account table
        //once the basic content provider is working
    }

    public static final class TransactionTable{
        public static final String TABLE_NAME = "tbtransaction";

        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_TRANSACTION).build();

        public static class Cols implements BaseColumns{
            public static final String TRANSACTION_ID = "transactionId";
            public static final String TRANSACTION_AMOUNT = "amount";
            public static final String TRANSACTION_TYPE = "type";
            public static final String TRANSACTION_CATEGORY = "category";
            public static final String TRANSACTION_TITLE = "title";
            public static final String TRANSACTION_DATE = "date_occurred";
        }
    }

}
