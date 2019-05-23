package com.creditsaison.loansystem.viewmodel;

import android.view.View;

import com.creditsaison.loansystem.R;

import androidx.lifecycle.ViewModel;
import androidx.navigation.Navigation;

public class EmploymentInfoViewModel extends ViewModel{

    public EmploymentInfoViewModel() {

    }

    public void onButtonClick(View view) {
        Navigation.findNavController(view).navigate(R.id.action_employmentInformationFragment_to_personalReferenceFragment);
    }

}
