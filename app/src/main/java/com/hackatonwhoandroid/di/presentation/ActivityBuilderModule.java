package com.hackatonwhoandroid.di.presentation;

import com.hackatonwhoandroid.presentation.main.MainActivity;
import com.hackatonwhoandroid.utils.base.di.ActivityScope;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module(includes = ViewModelBuilderModule.class)
public abstract class ActivityBuilderModule {

    @ActivityScope
    @ContributesAndroidInjector()
    abstract MainActivity bindMainActivity();

}
