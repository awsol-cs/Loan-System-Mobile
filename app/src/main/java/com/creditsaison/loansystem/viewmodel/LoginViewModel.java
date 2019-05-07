package com.creditsaison.loansystem.viewmodel;

import android.view.View;

import com.creditsaison.loansystem.R;
import com.creditsaison.loansystem.dagger.DaggerManager;
import com.creditsaison.loansystem.model.Credentials;
import com.creditsaison.loansystem.repositories.LoanSystemRemoteRepository;

import javax.inject.Inject;

import androidx.lifecycle.ViewModel;
import androidx.navigation.Navigation;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

public class LoginViewModel extends ViewModel {

    public Credentials credentials = new Credentials();
    @Inject
    LoanSystemRemoteRepository repository;
    @Inject
    CompositeDisposable compositeDisposable;

    public LoginViewModel() {
        DaggerManager.loanSystemComponent().injectLoginViewModel(this);
    }

    public void onLoginClick(View view) {
        Timber.d("onLoginClick --> credentials: %s", credentials.toString());
        Navigation.findNavController(view).navigate(R.id.action_loginFragment_to_accountFragment);

//        //TODO add validation of username and password
//        compositeDisposable.add(repository.login(credentials.getUsername(), credentials.getPassword())
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(user -> {
//                    if (user != null) {
//                        Timber.d("authentication --> user: %s", user.toString());
//                        Navigation.findNavController(view).navigate(R.id.action_loginFragment_to_accountFragment);
//                    }
//                }, Timber::e));
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        compositeDisposable.clear();
    }


}
