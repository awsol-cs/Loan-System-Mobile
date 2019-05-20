package com.creditsaison.loansystem.viewmodel;

import android.view.View;

import com.creditsaison.loansystem.R;

import androidx.lifecycle.ViewModel;
import androidx.navigation.Navigation;

public class TermsConditionsViewModel extends ViewModel {

    public TermsConditionsViewModel() {

    }

    public void onButtonClick(View view) {
        switch (view.getId()) {

            case R.id.getSign:
                Navigation.findNavController(view).navigate(R.id.action_termsConditionFragment_to_signatureFragment);

                break;

            case R.id.btn_agree:
                // do your code
                Navigation.findNavController(view).navigate(R.id.action_termsConditionFragment_to_loanAgreementFragment);

                break;

            case R.id.btn_disagree:
                // do your code
                Navigation.findNavController(view).navigate(R.id.action_termsConditionFragment_to_accountFragment);

                break;

            default:
                break;
        }
    }

}
