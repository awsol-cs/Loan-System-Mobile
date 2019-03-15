package com.creditsaison.loansystem.dagger.modules;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.creditsaison.loansystem.dagger.scopes.LoanSystemApplicationScope;
import com.creditsaison.loansystem.utils.SharedPrefManager;

import dagger.Module;
import dagger.Provides;

@Module
public class ContextModule {
    final Context context;
    private SharedPrefManager sharedPrefManager;

    public ContextModule(Context context) {
        this.context = context;
    }

    @Provides
    public Context context() {
        return context;
    }


    @Provides
    public SharedPreferences sharedPreferences() {
        return PreferenceManager.getDefaultSharedPreferences(context);
    }

    @LoanSystemApplicationScope
    @Provides
    public SharedPrefManager sharedPrefManager(SharedPreferences sharedPreferences) {
        if (sharedPrefManager == null) {
            sharedPrefManager = new SharedPrefManager(sharedPreferences);
        }

        return sharedPrefManager;
    }
}
