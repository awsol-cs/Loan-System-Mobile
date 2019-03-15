package com.creditsaison.loansystem.dagger.components;

import com.creditsaison.loansystem.dagger.modules.LoanSystemModule;
import com.creditsaison.loansystem.dagger.modules.RepositoryModule;
import com.creditsaison.loansystem.dagger.scopes.LoanSystemApplicationScope;
import com.creditsaison.loansystem.viewmodel.LoginViewModel;

import dagger.Component;

@LoanSystemApplicationScope
@Component(modules = {LoanSystemModule.class, RepositoryModule.class})
public interface LoanSystemComponent {

    void injectLoginViewModel(LoginViewModel loginViewModel);
}
