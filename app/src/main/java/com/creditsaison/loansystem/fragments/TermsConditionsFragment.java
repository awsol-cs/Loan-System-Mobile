package com.creditsaison.loansystem.fragments;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;

import com.creditsaison.loansystem.LoanAgreementActivity;
import com.creditsaison.loansystem.R;
import com.creditsaison.loansystem.databinding.FragmentTermsConditionsBinding;
import com.creditsaison.loansystem.viewmodel.LoanAgreementViewModel;
import com.creditsaison.loansystem.viewmodel.TermsConditionsViewModel;

import com.creditsaison.loansystem.SignatureActivity;

import java.io.FileNotFoundException;
import java.io.IOException;

public class TermsConditionsFragment extends Fragment implements View.OnClickListener {

    SharedPreferences sharedpreferences;

    Button signatureButton, agreeButton, disagreeButton;
    ImageView signImage;
    CheckBox checkBox;

    private FragmentTermsConditionsBinding binding;
    private TermsConditionsViewModel viewModel;

    public TermsConditionsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        Log.v("log_tag", "onCreate");


        getActivity().setContentView(R.layout.fragment_terms_conditions);
        signImage = (ImageView) getActivity().findViewById(R.id.imageView1);

        signatureButton = (Button) getActivity().findViewById(R.id.getSign);
        signatureButton.setOnClickListener(this);
        agreeButton = (Button) getActivity().findViewById(R.id.btn_agree);
        agreeButton.setOnClickListener(this);
        disagreeButton = (Button) getActivity().findViewById(R.id.btn_disagree);
        disagreeButton.setOnClickListener(this);

        checkBox = (CheckBox) getActivity().findViewById(R.id.checkbox);
        //disable button if checkbox is not checked else enable button
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {
                if ( isChecked )
                {
                    Log.v("log_tag", "MarkIF");
                    signatureButton.setEnabled(true);
                }
                else{
                    Log.v("log_tag", "MarkELSE");
                    signatureButton.setEnabled(false);
                }
            }
        });



        Log.v("log_tag", "kuha image");

        //to get imagepath from SignatureActivity and set it on ImageView


//        String image_path = getActivity().getIntent().getStringExtra("imagePath");//getIntent is an activity class
        //String image_path = args.getString("imagePath", "");//String text

        viewModel = ViewModelProviders.of(this).get(TermsConditionsViewModel.class);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Log.v("log_tag", "onCreateView andito ba?");

//        binding = FragmentTermsConditionsBinding.inflate(inflater, container, false);
//        binding.setViewModel(viewModel);

        Log.v("log_tag", "onCreateView");

//        return binding.getRoot();

        return inflater.inflate(R.layout.fragment_terms_conditions, container, false);
    }

    @Override
    public void onResume(){
        super.onResume();
        Log.v("log_tag", "uyNagResume");

        sharedpreferences = getActivity().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString("currentSign", "termsCondition");

        String restoredText = sharedpreferences.getString("termsConditionImg", " ");

        String image_path = restoredText;
        Bitmap bitmap = BitmapFactory.decodeFile(image_path);
        signImage.setImageBitmap(bitmap);
//        getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
    }

    public void onButtonClick(View view) {
        Navigation.findNavController(view).navigate(R.id.action_termsConditionFragment_to_loanAgreementFragment);
    }

//    Button.OnClickListener onButtonClick = new Button.OnClickListener() {
//        @Override
//        public void onClick(View v) {
//            // TODO Auto-generated method stub
//
//
//        }
//    };

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.getSign:
                // do your code
                if (checkBox.isChecked()){
                    Intent i = new Intent(TermsConditionsFragment.this.getActivity(), SignatureActivity.class);
                    startActivity(i);
                }
                else {
                    signatureButton.setEnabled(false);
                }

                break;

            case R.id.btn_agree:
                // do your code
                Intent i = new Intent(TermsConditionsFragment.this.getActivity(), LoanAgreementActivity.class);
                startActivity(i);

                break;

            case R.id.btn_disagree:
                // do your code
                Navigation.findNavController(v).navigate(R.id.action_termsConditionFragment_to_loanAgreementFragment);

                break;

            default:
                break;
        }
    }
}
