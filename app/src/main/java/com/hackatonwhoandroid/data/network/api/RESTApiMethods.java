package com.hackatonwhoandroid.data.network.api;

import com.hackatonwhoandroid.data.network.model.WhoIsDtoResponse;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;
import retrofit2.http.GET;

import retrofit2.http.POST;
import retrofit2.http.Query;

public interface RESTApiMethods {

    @GET("whois")
    Single<WhoIsDtoResponse> fetchWhoisDomain(@Query("domain_name") String domainName);

    @POST("email")
    Completable sendEmail(@Query("domain_name") String domainName, @Query("email_to") String emailTo);

}