package com.marklordan.airgead.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.marklordan.airgead.R;
import com.marklordan.airgead.model.Transaction;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mark on 03/11/2017.
 */

public class TransactionAdapter extends RecyclerView.Adapter<TransactionAdapter.TransactionViewHolder>{

    private Context mContext;
    private List<Transaction> mTransactions;
    private TransactionClickListener mListener;
    private SimpleDateFormat mDateFormat = new SimpleDateFormat("dd/MM/yyyy");
    private int expenseColor, incomeColor;
    private NumberFormat mNumberFormat = NumberFormat.getCurrencyInstance();

    public interface TransactionClickListener{
        void onItemClicked(int itemPosition);
    }

    public TransactionAdapter(Context context, List<Transaction> transactions, TransactionClickListener listener){
        mContext = context;
        mTransactions = transactions;
        mListener = listener;
        expenseColor = mContext.getResources().getColor(R.color.amount_expense);
        incomeColor = mContext.getResources().getColor(R.color.amount_income);
    }

    public void removeItem(int position){
        //TODO does not remove transaction from DB, just from Adapter list
        mTransactions.remove(position);
        notifyItemRemoved(position);
    }

    public void restoreItem(Transaction transaction, int position){
        mTransactions.add(position, transaction);
        notifyItemInserted(position);
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

        holder.mTransactionAmount.setText(mNumberFormat.format(transactionToBind.getAmount()));
        //TODO bind transaction category once it is stored correctly in DB
        //also bind transaction description / title
        holder.mTransactionDesc.setText(transactionToBind.getDescription());
        holder.mTransactionCategory.setText(transactionToBind.getCategory().toString());
        holder.mTransactionDate.setText(mDateFormat.format(transactionToBind.getDateOfTransaction()));

        if(transactionToBind.isAnExpense())
            holder.mTransactionAmount.setTextColor(expenseColor);
        else
            holder.mTransactionAmount.setTextColor(incomeColor);

    }

    @Override
    public int getItemCount() {
        return mTransactions.size();
    }

    public class TransactionViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView mTransactionAmount, mTransactionCategory, mTransactionDesc, mTransactionDate;

        public TransactionViewHolder(View itemView) {
            super(itemView);

            mTransactionAmount = (TextView) itemView.findViewById(R.id.transaction_amount);
            mTransactionCategory = (TextView) itemView.findViewById(R.id.transaction_category);
            mTransactionDesc = (TextView) itemView.findViewById(R.id.transaction_description);
            mTransactionDate = (TextView) itemView.findViewById(R.id.transaction_date);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            mListener.onItemClicked(getAdapterPosition());
        }
    }
}
