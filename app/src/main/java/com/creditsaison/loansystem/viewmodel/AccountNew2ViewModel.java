package com.creditsaison.loansystem.viewmodel;

import android.util.Log;
import android.view.View;

import com.creditsaison.loansystem.R;

import androidx.lifecycle.ViewModel;
import androidx.navigation.Navigation;

public class AccountNew2ViewModel extends ViewModel {

    public AccountNew2ViewModel() {

    }

    public void onButtonClick(View view) {
        switch (view.getId()) {
            case R.id.btn_submit:
                Log.v("log_tag", "nag case sya");
                break;

            default:
                Log.v("log_tag", "default");
                break;

        }
        Log.v("log_tag", "para lang =malaman ang view");
        Log.v("log_tag", view.toString());
        Log.v("log_tag", "para lang malaman ang view");
//        Navigation.findNavController(view).navigate(R.id.action_accountNewFragment2_to_loanApplicationFragment);
    }


}
