package com.creditsaison.loansystem.viewmodel;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.View;

import com.creditsaison.loansystem.R;
import com.creditsaison.loansystem.fragments.PersonalReferenceFragment;

import androidx.lifecycle.ViewModel;
import androidx.navigation.Navigation;

public class PersonalReferenceViewModel extends ViewModel {

    SharedPreferences sharedpreferences;

    public PersonalReferenceViewModel() {

    }

    public void onButtonClick(View view) {
        Navigation.findNavController(view).navigate(R.id.action_personalReferenceFragment_to_loanApplicationFragment);
    }

}
