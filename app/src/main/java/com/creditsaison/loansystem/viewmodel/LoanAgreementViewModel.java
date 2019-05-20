package com.creditsaison.loansystem.viewmodel;

import android.view.View;

import com.creditsaison.loansystem.R;

import androidx.lifecycle.ViewModel;
import androidx.navigation.Navigation;

public class LoanAgreementViewModel extends ViewModel {

    public LoanAgreementViewModel() {

    }



    public void onButtonClick(View view) {
        switch (view.getId()) {

            case R.id.getSign:
                Navigation.findNavController(view).navigate(R.id.action_loanAgreementFragment_to_signatureFragment);

                break;

            case R.id.btn_agree2:
                // do your code
                Navigation.findNavController(view).navigate(R.id.action_loanAgreementFragment_to_promiNoteFragment);

                break;

            case R.id.btn_disagree:
                // do your code
                Navigation.findNavController(view).navigate(R.id.action_loanAgreementFragment_to_accountFragment);

                break;

            default:
                break;
        }
    }
}
