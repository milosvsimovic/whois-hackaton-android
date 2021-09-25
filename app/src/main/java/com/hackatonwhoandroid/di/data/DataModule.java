package com.hackatonwhoandroid.di.data;

import com.hackatonwhoandroid.data.repository.ExampleRepository;
import com.hackatonwhoandroid.domain.repository.IExampleRepository;

import javax.inject.Singleton;

import dagger.Binds;
import dagger.Module;

@Module(includes = {NetworkModule.class, StorageModule.class})
public abstract class DataModule {

    @Singleton
    @Binds
    public abstract IExampleRepository bindExampleRepository(ExampleRepository repository);

}
