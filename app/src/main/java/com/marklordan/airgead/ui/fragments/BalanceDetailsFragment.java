package com.marklordan.airgead.ui.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.marklordan.airgead.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BalanceDetailsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BalanceDetailsFragment extends Fragment {

    public static final String TAG = "BalanceDetailsFragment";

    private TextInputEditText mCurrentBalance;

    public BalanceDetailsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment.
     *
     * @return A new instance of fragment BalanceDetailsFragment.
     */
    public static BalanceDetailsFragment newInstance() {
        BalanceDetailsFragment fragment = new BalanceDetailsFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_balance_details, container, false);
        mCurrentBalance = (TextInputEditText) v.findViewById(R.id.balance_edit_text);
        return v;
    }

    public double getCurrentBalance() {
        double initialValue = 0;
        try{
            initialValue = Double.parseDouble(mCurrentBalance.getText().toString());
        } catch(NumberFormatException nbe){
            Log.e(TAG, nbe.getMessage());
        }
        return initialValue;
    }

}
