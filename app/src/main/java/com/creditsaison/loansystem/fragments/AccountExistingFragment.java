package com.creditsaison.loansystem.fragments;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.creditsaison.loansystem.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class AccountExistingFragment extends Fragment {


    public AccountExistingFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_account_existing, container, false);
    }

}
