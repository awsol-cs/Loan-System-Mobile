package com.creditsaison.loansystem.viewmodel;

import android.view.View;

import com.creditsaison.loansystem.R;

import androidx.lifecycle.ViewModel;
import androidx.navigation.Navigation;

public class TermsConditionsViewModel extends ViewModel {

    public TermsConditionsViewModel() {

    }

    public void onButtonClick(View view) {
        Navigation.findNavController(view).navigate(R.id.action_termsConditionFragment_to_loanAgreementFragment);
    }
}
