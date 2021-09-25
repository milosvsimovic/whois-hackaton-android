/*
 *  Copyright (C) 2017 MINDORKS NEXTGEN PRIVATE LIMITED
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      https://mindorks.com/license/apache-v2
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License
 */

package com.hackatonwhoandroid.utils.base.presentation;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.ViewModel;

import com.hackatonwhoandroid.utils.base.presentation.viewmodel.ViewModelIdProvider;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;

@SuppressWarnings("unused")
abstract class BindingFragment<ViewDataBindingT extends ViewDataBinding, ViewModelV extends ViewModel> extends DaggerFragment {

    private ViewDataBindingT viewDataBinding;
    private ViewModelV viewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        viewDataBinding = getViewDataBinding(inflater, container);
        viewDataBinding.setLifecycleOwner(this);
        viewDataBinding.setVariable(ViewModelIdProvider.getViewModelId(), viewModel);
        return viewDataBinding.getRoot();
    }

    private ViewDataBindingT getViewDataBinding(@NonNull LayoutInflater inflater, @Nullable ViewGroup container) {
        return getView() == null
                ? DataBindingUtil.inflate(inflater, provideLayoutId(), container, false)
                : DataBindingUtil.getBinding(getView());
    }

    @Inject
    void setViewModel(ViewModelV viewModel){
        this.viewModel = viewModel;
    }

    @SuppressWarnings("WeakerAccess")
    @LayoutRes
    protected abstract int provideLayoutId();

    protected ViewDataBindingT getViewDataBinding() {
        return viewDataBinding;
    }

    protected ViewModelV getViewModel() {
        return viewModel;
    }

}

