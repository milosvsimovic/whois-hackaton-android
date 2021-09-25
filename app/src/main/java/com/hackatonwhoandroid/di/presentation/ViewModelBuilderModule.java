package com.hackatonwhoandroid.di.presentation;

import androidx.lifecycle.ViewModel;

import com.hackatonwhoandroid.presentation.chat.ChatItemViewModel;
import com.hackatonwhoandroid.presentation.chat.ChatViewModel;
import com.hackatonwhoandroid.presentation.main.MainViewModel;
import com.hackatonwhoandroid.utils.base.di.ViewModelKey;
import com.hackatonwhoandroid.utils.base.di.viewmodel.BaseViewModelBuilderModule;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module(includes = BaseViewModelBuilderModule.class)
abstract class ViewModelBuilderModule {

    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel.class)
    abstract ViewModel bindMainViewModel(MainViewModel viewModel);

    @Binds
    @IntoMap
    @ViewModelKey(ChatViewModel.class)
    abstract ViewModel bindChatViewModel(ChatViewModel viewModel);

    @Binds
    @IntoMap
    @ViewModelKey(ChatItemViewModel.class)
    abstract ViewModel bindChatItemViewModel(ChatItemViewModel viewModel);

}
