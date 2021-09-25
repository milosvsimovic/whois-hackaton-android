package com.hackatonwhoandroid.di.presentation;

import com.hackatonwhoandroid.presentation.chat.ChatFragment;
import com.hackatonwhoandroid.utils.base.di.FragmentScope;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module(includes = ViewModelBuilderModule.class)
public abstract class FragmentBuilderModule {

    @FragmentScope
    @ContributesAndroidInjector()
    abstract ChatFragment bindChatFragment();

}
