package com.creditsaison.loansystem.dagger;

import android.content.Context;

import com.creditsaison.loansystem.dagger.components.DaggerLoanSystemComponent;
import com.creditsaison.loansystem.dagger.components.LoanSystemComponent;
import com.creditsaison.loansystem.dagger.modules.ContextModule;

public class DaggerManager {

    private LoanSystemComponent loanSystemComponent;


    public static DaggerManager getInstance() {
        return Holder.INSTANCE;
    }

    public static LoanSystemComponent loanSystemComponent() {
        return getInstance().loanSystemComponent;
    }

    public void buildComponentAndInject(Context context) {
        loanSystemComponent = DaggerLoanSystemComponent.builder()
                .contextModule(new ContextModule(context))
                .build();
    }

    private static class Holder {
        static final DaggerManager INSTANCE = new DaggerManager();
    }
}
