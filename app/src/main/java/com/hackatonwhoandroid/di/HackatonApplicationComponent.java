package com.hackatonwhoandroid.di;


import com.hackatonwhoandroid.utils.HackatonApplication;
import com.hackatonwhoandroid.di.data.DataModule;
import com.hackatonwhoandroid.di.presentation.ActivityBuilderModule;
import com.hackatonwhoandroid.di.presentation.DialogBuilderModule;
import com.hackatonwhoandroid.di.presentation.FragmentBuilderModule;
import com.hackatonwhoandroid.di.presentation.ServiceBuilderModule;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjectionModule;
import dagger.android.AndroidInjector;

@Singleton
@Component(modules = {
        ApplicationModule.class,
        AndroidInjectionModule.class,
        ActivityBuilderModule.class,
        FragmentBuilderModule.class,
        ServiceBuilderModule.class,
        DialogBuilderModule.class,
        DataModule.class
})
public interface HackatonApplicationComponent extends AndroidInjector<HackatonApplication> {

    void inject(HackatonApplication application);

    @Component.Builder
    interface Builder {

        @BindsInstance
        Builder application(HackatonApplication application);

        HackatonApplicationComponent build();
    }
}