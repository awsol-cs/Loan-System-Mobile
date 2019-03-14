package com.creditsaison.loansystem.viewmodel;

import android.view.View;

import com.creditsaison.loansystem.R;

import androidx.lifecycle.ViewModel;
import androidx.navigation.Navigation;

public class AccountViewModel extends ViewModel {

    public AccountViewModel() {

    }

    public void onButtonClick(View view) {
        switch (view.getId()){
            case R.id.btn_yes:
                Navigation.findNavController(view).navigate(R.id.action_accountFragment_to_accountExistingFragment);
                break;
            case R.id.btn_no:
                Navigation.findNavController(view).navigate(R.id.action_accountFragment_to_accountNewFragment);
                break;
        }
    }
}
