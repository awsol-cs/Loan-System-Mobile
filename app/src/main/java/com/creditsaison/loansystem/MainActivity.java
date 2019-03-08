package com.creditsaison.loansystem;

import androidx.appcompat.app.AppCompatActivity;
import timber.log.Timber;

import android.os.Bundle;

import com.creditsaison.loansystem.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}
