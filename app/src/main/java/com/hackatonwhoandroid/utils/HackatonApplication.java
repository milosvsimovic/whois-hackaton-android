package com.hackatonwhoandroid.utils;

import com.hackatonwhoandroid.BR;
import com.hackatonwhoandroid.di.DaggerHackatonApplicationComponent;
import com.hackatonwhoandroid.utils.base.presentation.viewmodel.ViewModelIdProvider;
import com.hackatonwhoandroid.utils.base.utils.BaseApplication;

import dagger.android.AndroidInjector;
import dagger.android.support.DaggerApplication;

public class HackatonApplication extends BaseApplication {

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    protected AndroidInjector<? extends DaggerApplication> applicationInjector() {
        return DaggerHackatonApplicationComponent.builder().application(this).build();
    }

    @Override
    protected void setViewModelId() {
        ViewModelIdProvider.setViewModelId(BR.viewmodel);
    }


}