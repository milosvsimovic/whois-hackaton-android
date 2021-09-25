package com.hackatonwhoandroid.di;

import android.app.Application;
import android.content.Context;
import android.content.res.Resources;

import com.hackatonwhoandroid.di.data.DataModule;
import com.hackatonwhoandroid.utils.HackatonApplication;
import com.hackatonwhoandroid.utils.base.di.AppContext;

import dagger.Module;
import dagger.Provides;
import io.reactivex.rxjava3.disposables.CompositeDisposable;

@Module(includes = {
        DataModule.class,
})
class ApplicationModule {

    @Provides
    @AppContext
    Context provideAppContext(HackatonApplication application) {
        return application.getApplicationContext();
    }

    @Provides
    Application provideApplication(HackatonApplication application) {
        return application;
    }

    @Provides
    Resources provideResources(HackatonApplication application) {
        return application.getResources();
    }

    @Provides
    CompositeDisposable provideCompositeDisposable() {
        return new CompositeDisposable();
    }

}