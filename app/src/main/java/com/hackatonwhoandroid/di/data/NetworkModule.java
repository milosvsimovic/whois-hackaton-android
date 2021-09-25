package com.hackatonwhoandroid.di.data;

import android.content.Context;
import android.net.ConnectivityManager;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.hackatonwhoandroid.BuildConfig;
import com.hackatonwhoandroid.data.network.api.RESTApiMethods;
import com.hackatonwhoandroid.data.network.interceptors.ContentTypeInterceptor;
import com.hackatonwhoandroid.data.network.interceptors.ErrorInterceptor;
import com.hackatonwhoandroid.data.network.interceptors.NetworkConnectionInterceptor;
import com.hackatonwhoandroid.utils.base.di.AppContext;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class NetworkModule {

    private static final String API_BASE_URL = "https://thedespot-api-grl3r7n4kq-uc.a.run.app/";

    @Singleton
    @Provides
    Gson provideGson() {
        return new GsonBuilder().disableHtmlEscaping().create();
    }

    @Singleton
    @Provides
    RESTApiMethods provideApiMethodsServer(OkHttpClient client, Gson gson) {
        return new Retrofit.Builder()
                .baseUrl(API_BASE_URL)
                .client(client)
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()
                .create(RESTApiMethods.class);
    }

    @Singleton
    @Provides
    OkHttpClient provideClient(NetworkConnectionInterceptor networkConnectionInterceptor, ErrorInterceptor errorInterceptor, ContentTypeInterceptor contentTypeInterceptor, HttpLoggingInterceptor loggingInterceptor) {
        return new OkHttpClient.Builder()
                .addInterceptor(networkConnectionInterceptor)
                .addInterceptor(errorInterceptor)
                .addInterceptor(contentTypeInterceptor)
                .addInterceptor(loggingInterceptor)
                .build();
    }

    @Singleton
    @Provides
    HttpLoggingInterceptor provideLoggingInterceptor() {
        return new HttpLoggingInterceptor().setLevel(BuildConfig.DEBUG
                ? HttpLoggingInterceptor.Level.BODY
                : HttpLoggingInterceptor.Level.NONE);
    }

    @Singleton
    @Provides
    ConnectivityManager provideConnectivityManager(@AppContext Context context) {
        return (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
    }

}
