package com.creditsaison.loansystem.fragments;


import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.creditsaison.loansystem.R;
import com.creditsaison.loansystem.databinding.FragmentLoanRepaymentDetailsBinding;
import com.creditsaison.loansystem.viewmodel.LoanRepaymentDetailsViewModel;

/**
 * A simple {@link Fragment} subclass.
 */
public class LoanRepaymentDetailsFragment extends Fragment {

    private FragmentLoanRepaymentDetailsBinding binding;
    private LoanRepaymentDetailsViewModel viewModel;


    public LoanRepaymentDetailsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        viewModel = ViewModelProviders.of(this).get(LoanRepaymentDetailsViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentLoanRepaymentDetailsBinding.inflate(inflater, container, false);
        binding.setViewModel(viewModel);

        return binding.getRoot();
    }

}
