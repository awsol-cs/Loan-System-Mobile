package com.creditsaison.loansystem.fragments;


import android.content.Intent;
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
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;

import com.creditsaison.loansystem.R;
import com.creditsaison.loansystem.databinding.FragmentTermsConditionsBinding;
import com.creditsaison.loansystem.viewmodel.TermsConditionsViewModel;

import com.creditsaison.loansystem.SignatureActivity;

import java.io.FileNotFoundException;
import java.io.IOException;

public class TermsConditionsFragment extends Fragment {
    Button signatureButton;
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

        getActivity().setContentView(R.layout.fragment_terms_conditions);
        signatureButton = (Button) getActivity().findViewById(R.id.getSign);
        signImage = (ImageView) getActivity().findViewById(R.id.imageView1);
        signatureButton.setOnClickListener(onButtonClick);
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

        try {
            //to get imagepath from SignatureActivity and set it on ImageView

            String image_path = getActivity().getIntent().getStringExtra("imagePath");//getIntent is an activity class
            Bitmap bitmap = BitmapFactory.decodeFile(image_path);
            signImage.setImageBitmap(bitmap);

        } catch (Error e) {
            Log.v("log_tag", "nasa catch");

        } finally {
            Log.v("log_tag", "nasa final");

        }



        //viewModel = ViewModelProviders.of(this).get(TermsConditionsViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentTermsConditionsBinding.inflate(inflater, container, false);
        binding.setViewModel(viewModel);

        return binding.getRoot();
    }

    Button.OnClickListener onButtonClick = new Button.OnClickListener() {
        @Override
        public void onClick(View v) {
            // TODO Auto-generated method stub
            if (checkBox.isChecked()){
                Intent i = new Intent(TermsConditionsFragment.this.getActivity(), SignatureActivity.class);
                startActivity(i);
            }
            else {
                signatureButton.setEnabled(false);
            }

        }
    };

}
