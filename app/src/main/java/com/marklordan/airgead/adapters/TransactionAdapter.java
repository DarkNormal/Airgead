package com.marklordan.airgead.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.marklordan.airgead.R;
import com.marklordan.airgead.model.Transaction;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mark on 03/11/2017.
 */

public class TransactionAdapter extends RecyclerView.Adapter<TransactionAdapter.TransactionViewHolder>{

    private Context mContext;
    private List<Transaction> mTransactions;

    public TransactionAdapter(Context context, List<Transaction> transactions){
        mContext = context;
        mTransactions = transactions;
    }


    @Override
    public TransactionViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View v = inflater.inflate(R.layout.transaction_list_item, parent, false);
        return new TransactionViewHolder(v);
    }

    @Override
    public void onBindViewHolder(TransactionViewHolder holder, int position) {
        Transaction transactionToBind = mTransactions.get(position);

        holder.mTransactionAmount.setText("€" + transactionToBind.getAmount());
        //TODO bind transaction category once it is stored correctly in DB
        //also bind transaction description / title

    }

    @Override
    public int getItemCount() {
        return mTransactions.size();
    }

    public class TransactionViewHolder extends RecyclerView.ViewHolder{
        private TextView mTransactionAmount, mTransactionCategory, mTransactionDesc;

        public TransactionViewHolder(View itemView) {
            super(itemView);

            mTransactionAmount = (TextView) itemView.findViewById(R.id.transaction_amount);
            mTransactionCategory = (TextView) itemView.findViewById(R.id.transaction_category);
            mTransactionDesc = (TextView) itemView.findViewById(R.id.transaction_description);
        }

    }
}
