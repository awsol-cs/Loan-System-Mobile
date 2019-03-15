package com.creditsaison.loansystem.dagger.modules;

import com.creditsaison.loansystem.dagger.interfaces.LoanSystemApi;
import com.creditsaison.loansystem.dagger.scopes.LoanSystemApplicationScope;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import dagger.Module;
import dagger.Provides;
import io.reactivex.disposables.CompositeDisposable;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.creditsaison.loansystem.utils.Constants.SERVER_HOST_ADDRESS;

@Module(includes = OkHttpClientModule.class)
public class LoanSystemModule {

    @Provides
    public LoanSystemApi loanSystemApi(Retrofit retrofit) {
        return retrofit.create(LoanSystemApi.class);
    }

    @LoanSystemApplicationScope
    @Provides
    public Retrofit retrofit(OkHttpClient okHttpClient,
                             GsonConverterFactory gsonConverterFactory, Gson gson) {
        return new Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(SERVER_HOST_ADDRESS)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(gsonConverterFactory)
                .build();
    }

    @Provides
    public Gson gson() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        return gsonBuilder.create();
    }

    @Provides
    public GsonConverterFactory gsonConverterFactory(Gson gson) {
        return GsonConverterFactory.create(gson);
    }

    @Provides
    public CompositeDisposable compositeDisposable() {
        return new CompositeDisposable();
    }

}
