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
import com.creditsaison.loansystem.databinding.FragmentPromiNoteBinding;
import com.creditsaison.loansystem.viewmodel.PromissoryViewModel;

public class PromissoryFragment extends Fragment {


    SharedPreferences sharedpreferences;
    ImageView signImage;
    Button btn_agree;

    private FragmentPromiNoteBinding binding;
    private PromissoryViewModel viewModel;

    public PromissoryFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        viewModel = ViewModelProviders.of(this).get(PromissoryViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentPromiNoteBinding.inflate(inflater, container, false);
        binding.setViewModel(viewModel);

        signImage = (ImageView) binding.getRoot().findViewById(R.id.signature);

        return binding.getRoot();
    }

    @Override
    public void onResume(){
        super.onResume();
        Log.v("log_tag", "uyNagResume");

        btn_agree = (Button) getActivity().findViewById(R.id.btn_agree3);

        sharedpreferences = getActivity().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString("currentSign", "promissoryNote");
        editor.commit();

        String restoredText = sharedpreferences.getString("promNoteImg", " ");

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
