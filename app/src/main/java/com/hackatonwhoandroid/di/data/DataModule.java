package com.hackatonwhoandroid.di.data;

import com.hackatonwhoandroid.data.repository.MessageRepository;
import com.hackatonwhoandroid.domain.repository.IMessageRepository;

import javax.inject.Singleton;

import dagger.Binds;
import dagger.Module;

@Module(includes = {NetworkModule.class, StorageModule.class})
public abstract class DataModule {

    @Singleton
    @Binds
    public abstract IMessageRepository bindExampleRepository(MessageRepository repository);

}
