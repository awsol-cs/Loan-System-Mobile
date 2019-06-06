package com.creditsaison.loansystem.viewmodel;

import android.view.View;

import com.creditsaison.loansystem.R;

import androidx.lifecycle.ViewModel;
import androidx.navigation.Navigation;

public class PromissoryViewModel extends ViewModel {

    public PromissoryViewModel() {

    }

    public void onButtonClick(View view) {
        switch (view.getId()) {

            case R.id.getSign:
                Navigation.findNavController(view).navigate(R.id.action_promiNoteFragment_to_signatureFragment);

                break;

//            case R.id.btn_agree3:
//                // do your code
//                Navigation.findNavController(view).navigate(R.id.action_promiNoteFragment_to_loanApplicationSentFragment);
//
//                break;

            case R.id.btn_disagree:
                // do your code
                Navigation.findNavController(view).navigate(R.id.action_promiNoteFragment_to_accountFragment);

                break;

            default:
                break;
        }
    }
}
