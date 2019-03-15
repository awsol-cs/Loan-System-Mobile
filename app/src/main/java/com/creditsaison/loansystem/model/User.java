package com.creditsaison.loansystem.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class User {
    public String username;
    public int userId;

    @SerializedName("base64EncodedAuthenticationKey")
    @Expose
    public String authenticationKey;

    public boolean authenticated;

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", userId=" + userId +
                ", authenticationKey='" + authenticationKey + '\'' +
                ", authenticated=" + authenticated +
                '}';
    }
}
