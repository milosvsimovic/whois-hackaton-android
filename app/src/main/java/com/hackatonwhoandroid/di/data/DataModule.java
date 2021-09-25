package com.hackatonwhoandroid.di.data;

import com.hackatonwhoandroid.data.repository.WhoisRepository;
import com.hackatonwhoandroid.domain.repository.IWhoisRepository;

import javax.inject.Singleton;

import dagger.Binds;
import dagger.Module;

@Module(includes = {NetworkModule.class, StorageModule.class})
public abstract class DataModule {

    @Singleton
    @Binds
    public abstract IWhoisRepository bindExampleRepository(WhoisRepository repository);

}
