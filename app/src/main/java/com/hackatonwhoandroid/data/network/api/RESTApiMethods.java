package com.hackatonwhoandroid.data.network.api;

import io.reactivex.rxjava3.core.Single;
import retrofit2.http.GET;

import retrofit2.http.Query;

public interface RESTApiMethods {

    @GET("whois")
    Single<String> getWhois(@Query("domain") String domain);

}