package com.creditsaison.loansystem.viewmodel;

import android.view.View;

import com.creditsaison.loansystem.R;
import com.creditsaison.loansystem.model.User;

import androidx.lifecycle.ViewModel;
import androidx.navigation.Navigation;
import timber.log.Timber;

public class LoginViewModel extends ViewModel {

    public User user = new User();

    public LoginViewModel() {

    }

    public void onLoginClick(View view) {
        Timber.d("onLoginClick --> user: %s", user.toString());
        Navigation.findNavController(view).navigate(R.id.action_loginFragment_to_accountFragment);
    }


}
