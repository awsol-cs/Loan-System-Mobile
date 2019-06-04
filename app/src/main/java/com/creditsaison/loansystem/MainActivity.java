package com.creditsaison.loansystem;

import androidx.appcompat.app.AppCompatActivity;
import timber.log.Timber;

import android.os.Bundle;

import com.creditsaison.loansystem.dagger.DaggerManager;
import com.creditsaison.loansystem.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

//    public String final_url = "https://192.168.227.159/";
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
