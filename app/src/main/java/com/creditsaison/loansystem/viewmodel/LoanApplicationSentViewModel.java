package com.creditsaison.loansystem.viewmodel;

import android.view.View;

import com.creditsaison.loansystem.R;

import androidx.lifecycle.ViewModel;
import androidx.navigation.Navigation;

public class LoanApplicationSentViewModel extends ViewModel {

    public LoanApplicationSentViewModel() {

    }

    public void onButtonClick(View view) {
        Navigation.findNavController(view).navigate(R.id.action_loanApplicationSentFragment_to_accountFragment);
    }
}
