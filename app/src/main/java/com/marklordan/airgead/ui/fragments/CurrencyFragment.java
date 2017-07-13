package com.marklordan.airgead.ui.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;

import com.github.paolorotolo.appintro.ISlidePolicy;
import com.marklordan.airgead.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link CurrencyFragment.OnCurrencySelectedListener} interface
 * to handle interaction events.
 * Use the {@link CurrencyFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CurrencyFragment extends Fragment implements ISlidePolicy{

    public static final String TAG = "CurrencyFragment";
    private RadioGroup mRadioGroup;

    private OnCurrencySelectedListener mListener;
    private RecyclerView currencyRecyclerView;

    public CurrencyFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment.
     *
     * @return A new instance of fragment CurrencyFragment.
     */
    public static CurrencyFragment newInstance() {
        CurrencyFragment fragment = new CurrencyFragment();
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_currency, container, false);
        mRadioGroup = (RadioGroup) v.findViewById(R.id.currency_radio_group);
        mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                Log.d(TAG, checkedId + "");
                onRadioButtonSelected(checkedId);
            }
        });
        return v;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onRadioButtonSelected(@IdRes int checkId) {
        if (mListener != null) {
            String chosenCurrency = "";
            switch(checkId){
                case R.id.euro_option:
                    chosenCurrency = "euro";
                    break;
                case R.id.us_dollar_option:
                    chosenCurrency = "us_dollar";
                    break;
                case R.id.pound_option:
                    chosenCurrency = "pound";
                    break;
                case R.id.can_dollar_dollar:
                    chosenCurrency = "can_dollar";
                    break;
            }
            mListener.onCurrencySelected(chosenCurrency);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnCurrencySelectedListener) {
            mListener = (OnCurrencySelectedListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public boolean isPolicyRespected() {
        if(mRadioGroup.getCheckedRadioButtonId() == -1){
            return false;
        }
        else{
            return true;
        }
    }

    @Override
    public void onUserIllegallyRequestedNextPage() {
        Log.d(TAG, "currency not selected");

    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     */
    public interface OnCurrencySelectedListener {
        // TODO: Update argument type and name
        void onCurrencySelected(String selectedCurrency);
    }

}
