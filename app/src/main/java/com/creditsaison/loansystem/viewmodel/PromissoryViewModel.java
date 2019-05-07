package com.creditsaison.loansystem.viewmodel;

import android.view.View;

import com.creditsaison.loansystem.R;

import androidx.lifecycle.ViewModel;
import androidx.navigation.Navigation;

public class PromissoryViewModel extends ViewModel {

    public PromissoryViewModel() {

    }

    public void onButtonClick(View view) {
        Navigation.findNavController(view).navigate(R.id.action_promiNoteFragment_to_loanApplicationSentFragment);
    }
}
