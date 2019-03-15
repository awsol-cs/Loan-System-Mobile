package com.creditsaison.loansystem.dagger.interfaces;

import com.creditsaison.loansystem.model.User;

import io.reactivex.Single;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface LoanSystemApi {

    @POST("authentication")
    Single<User> authentication(@Query("username") String username, @Query("password") String password);
}
