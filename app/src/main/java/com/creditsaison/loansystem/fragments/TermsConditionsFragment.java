package com.creditsaison.loansystem.fragments;


import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.creditsaison.loansystem.R;
import com.creditsaison.loansystem.databinding.FragmentTermsConditionsBinding;
import com.creditsaison.loansystem.viewmodel.TermsConditionsViewModel;


public class TermsConditionsFragment extends Fragment {

    SharedPreferences sharedpreferences;
    ImageView signImage;
    Button btn_agree;

    private FragmentTermsConditionsBinding binding;
    private TermsConditionsViewModel viewModel;

    public TermsConditionsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        Log.v("log_tag", "onCreate");

        viewModel = ViewModelProviders.of(this).get(TermsConditionsViewModel.class);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Log.v("log_tag", "onCreateView andito ba?");


        binding = FragmentTermsConditionsBinding.inflate(inflater, container, false);
        binding.setViewModel(viewModel);

        signImage = (ImageView) binding.getRoot().findViewById(R.id.signature);

        return binding.getRoot();

    }

    @Override
    public void onResume(){
        super.onResume();
        Log.v("log_tag", "uyNagResume");


        btn_agree = (Button) getActivity().findViewById(R.id.btn_agree);

        sharedpreferences = getActivity().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString("currentSign", "termsCondition");
        editor.commit();

        String restoredText = sharedpreferences.getString("termsConditionImg", " ");


        if (restoredText != "default"){
            String image_path = restoredText;
            Bitmap bitmap = BitmapFactory.decodeFile(image_path);
            signImage.setImageBitmap(bitmap);
            btn_agree.setEnabled(true);
        } else {
            signImage.setImageResource(R.drawable.signature_logo);
            btn_agree.setEnabled(false);
        }

    }

}
