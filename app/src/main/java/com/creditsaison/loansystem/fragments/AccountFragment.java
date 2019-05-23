package com.creditsaison.loansystem.fragments;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.creditsaison.loansystem.R;
import com.creditsaison.loansystem.databinding.FragmentAccountBinding;
import com.creditsaison.loansystem.viewmodel.AccountViewModel;

/**
 * A simple {@link Fragment} subclass.
 */
public class AccountFragment extends Fragment {

    private FragmentAccountBinding binding;
    private AccountViewModel viewModel;


    public AccountFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        viewModel = ViewModelProviders.of(this).get(AccountViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentAccountBinding.inflate(inflater, container, false);
        binding.setViewModel(viewModel);

        final String MyPREFERENCES = "MyPrefs" ;

        SharedPreferences sharedpreferences = getActivity().getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);


        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.clear();

//        String signImgDef = "/storage/emulated/0/UserSignature/20190507_025519.png";drawable://
        String signImgDef = "default";

        editor.putString("currentSign", "termsCondition");
        editor.putString("termsConditionImg", signImgDef);
        editor.putString("loanAgreementImg", signImgDef);
        editor.putString("promNoteImg", signImgDef);
        editor.putString("createWhat", "client");
        editor.commit();

        return binding.getRoot();
    }

}
