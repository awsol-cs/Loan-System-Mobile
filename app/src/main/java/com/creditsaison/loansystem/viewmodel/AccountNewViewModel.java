package com.creditsaison.loansystem.viewmodel;

import android.view.View;

import com.creditsaison.loansystem.R;

import androidx.lifecycle.ViewModel;
import androidx.navigation.Navigation;

public class AccountNewViewModel extends ViewModel {

    public AccountNewViewModel() {

    }

    public void onButtonClick(View view) {
        Navigation.findNavController(view).navigate(R.id.action_accountNewFragment_to_accountNewFragment2);
    }


}
