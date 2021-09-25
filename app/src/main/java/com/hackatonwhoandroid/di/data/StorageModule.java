package com.hackatonwhoandroid.di.data;

import android.content.Context;
import android.content.SharedPreferences;

import com.hackatonwhoandroid.utils.base.di.AppContext;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class StorageModule {

    private static final String PREF_NAME = "Storage";

    @Singleton
    @Provides
    SharedPreferences provideSharedPreferences(@AppContext Context context) {
        return context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

}

