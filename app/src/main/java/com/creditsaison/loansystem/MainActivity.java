package com.creditsaison.loansystem;

import androidx.appcompat.app.AppCompatActivity;
import timber.log.Timber;

import android.os.Bundle;

import com.creditsaison.loansystem.dagger.DaggerManager;
import com.creditsaison.loansystem.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    // ip url declared here
    // check specs to have a working database connection
    public String final_url = "https://100.26.101.219/";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //enable debug logs
        Timber.plant(new Timber.DebugTree());

        ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        DaggerManager.getInstance().buildComponentAndInject(this);

    }
}
