package com.creditsaison.loansystem.fragments;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.creditsaison.loansystem.R;
import com.creditsaison.loansystem.databinding.FragmentLoanAgreementBinding;
import com.creditsaison.loansystem.viewmodel.LoanAgreementViewModel;

public class LoanAgreementFragment extends Fragment {

    private FragmentLoanAgreementBinding binding;
    private LoanAgreementViewModel viewModel;

    public LoanAgreementFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        viewModel = ViewModelProviders.of(this).get(LoanAgreementViewModel.class);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentLoanAgreementBinding.inflate(inflater, container, false);
        binding.setViewModel(viewModel);

        return binding.getRoot();


    }
}
