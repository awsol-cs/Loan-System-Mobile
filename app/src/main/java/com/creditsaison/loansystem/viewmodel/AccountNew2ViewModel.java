package com.creditsaison.loansystem.viewmodel;

import android.view.View;

import com.creditsaison.loansystem.R;

import androidx.lifecycle.ViewModel;
import androidx.navigation.Navigation;

public class AccountNew2ViewModel extends ViewModel {

    public AccountNew2ViewModel() {

    }

    public void onButtonClick(View view) {
        Navigation.findNavController(view).navigate(R.id.action_accountNewFragment2_to_loanApplicationFragment2);
    }


}
