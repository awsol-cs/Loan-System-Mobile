package com.creditsaison.loansystem.viewmodel;

import android.view.View;

import com.creditsaison.loansystem.R;

import androidx.lifecycle.ViewModel;
import androidx.navigation.Navigation;

public class LoanApplicationViewModel extends ViewModel {

    public LoanApplicationViewModel() {

    }

    public void onButtonClick(View view) {
        switch (view.getId()){
            case R.id.btn_repayment:
                Navigation.findNavController(view).navigate(R.id.action_loanApplicationFragment_to_loanRepaymentDetailsFragment2);
                break;
            case R.id.btn_submit:
                Navigation.findNavController(view).navigate(R.id.action_loanApplicationFragment_to_coMakerFragment);
                break;
        }
    }
}
