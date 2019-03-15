package com.creditsaison.loansystem.repositories;

import com.creditsaison.loansystem.dagger.interfaces.LoanSystemApi;
import com.creditsaison.loansystem.model.User;

import io.reactivex.Single;

public class LoanSystemRemoteRepository {
    private final LoanSystemApi loanSystemApi;

    public LoanSystemRemoteRepository(LoanSystemApi loanSystemApi) {
        this.loanSystemApi = loanSystemApi;
    }

    public Single<User> login(String username, String password) {
        return loanSystemApi.authentication(username, password);
    }
}
