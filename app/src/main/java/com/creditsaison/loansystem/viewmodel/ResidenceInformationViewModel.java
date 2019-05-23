package com.creditsaison.loansystem.viewmodel;

import android.view.View;

import com.creditsaison.loansystem.R;
import com.creditsaison.loansystem.fragments.ResidenceInformationFragment;

import androidx.lifecycle.ViewModel;
import androidx.navigation.Navigation;

public class ResidenceInformationViewModel extends ViewModel{

    public ResidenceInformationViewModel() {

    }

    public void onButtonClick(View view) {
        ResidenceInformationFragment riFrag = new ResidenceInformationFragment();
        riFrag.storeData();

        Navigation.findNavController(view).navigate(R.id.action_residenceInformationFragment_to_employmentInformationFragment);
    }

}
