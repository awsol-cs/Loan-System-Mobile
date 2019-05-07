package com.creditsaison.loansystem.fragments;


import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.content.Intent;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.ImageView;

import com.creditsaison.loansystem.R;
import com.creditsaison.loansystem.databinding.FragmentAccountNew2Binding;
import com.creditsaison.loansystem.viewmodel.AccountNew2ViewModel;

/**
 * A simple {@link Fragment} subclass.
 */
public class AccountNew2Fragment extends Fragment {


    private FragmentAccountNew2Binding binding;
    private AccountNew2ViewModel viewModel;

    public AccountNew2Fragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        viewModel = ViewModelProviders.of(this).get(AccountNew2ViewModel.class);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentAccountNew2Binding.inflate(inflater, container, false);
        binding.setViewModel(viewModel);

        return binding.getRoot();
    }


}
