package com.creditsaison.loansystem;

import androidx.appcompat.app.AppCompatActivity;
import timber.log.Timber;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.creditsaison.loansystem.dagger.DaggerManager;
import com.creditsaison.loansystem.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final String MyPREFERENCES = "MyPrefs" ;

        SharedPreferences sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);


        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.clear();

        String signImgDef = "/storage/emulated/0/UserSignature/20190507_025519.png";
        editor.putString("currentSign", "termsCondition");
        editor.putString("termsConditionImg", signImgDef);
        editor.putString("loanAgreementImg", signImgDef);
        editor.putString("promNoteImg", signImgDef);
        editor.commit();

        //enable debug logs
        Timber.plant(new Timber.DebugTree());

        ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        DaggerManager.getInstance().buildComponentAndInject(this);

    }
}
