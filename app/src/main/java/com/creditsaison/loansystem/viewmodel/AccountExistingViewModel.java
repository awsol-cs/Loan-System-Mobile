package com.creditsaison.loansystem.viewmodel;

import android.view.View;

import com.creditsaison.loansystem.R;

import androidx.lifecycle.ViewModel;
import androidx.navigation.Navigation;

public class AccountExistingViewModel extends ViewModel {

    public AccountExistingViewModel() {

    }

    public void onButtonClick(View view) {
        switch (view.getId()){
            case R.id.btn_confirm:
                Navigation.findNavController(view).navigate(R.id.action_accountExistingFragment_to_coMakerFragment);
                break;
            case R.id.btn_register:
                Navigation.findNavController(view).navigate(R.id.action_accountExistingFragment_to_accountNewFragment);
                break;
        }
    }
}
