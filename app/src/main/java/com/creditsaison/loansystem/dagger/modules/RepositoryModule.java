package com.creditsaison.loansystem.dagger.modules;

import com.creditsaison.loansystem.dagger.interfaces.LoanSystemApi;
import com.creditsaison.loansystem.repositories.LoanSystemRemoteRepository;

import dagger.Module;
import dagger.Provides;

@Module(includes = LoanSystemModule.class)
public class RepositoryModule {

    @Provides
    public LoanSystemRemoteRepository loanSystemRemoteRepository(LoanSystemApi loanSystemApi) {
        return new LoanSystemRemoteRepository(loanSystemApi);
    }
}
