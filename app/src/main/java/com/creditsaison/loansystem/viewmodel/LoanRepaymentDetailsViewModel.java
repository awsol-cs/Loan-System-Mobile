package com.creditsaison.loansystem.viewmodel;

import android.view.View;

import com.creditsaison.loansystem.R;

import androidx.lifecycle.ViewModel;
import androidx.navigation.Navigation;

public class LoanRepaymentDetailsViewModel extends ViewModel {

    public LoanRepaymentDetailsViewModel() {

    }

    public void onButtonClick(View view) {
        Navigation.findNavController(view).navigate((R.id.action_loanRepaymentDetailsFragment_to_loanApplicationFragment));
    }
}
