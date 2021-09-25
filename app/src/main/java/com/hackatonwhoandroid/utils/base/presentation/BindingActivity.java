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

import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.ViewModel;

import com.hackatonwhoandroid.utils.base.presentation.viewmodel.ViewModelIdProvider;

import javax.inject.Inject;

import dagger.android.support.DaggerAppCompatActivity;

@SuppressWarnings("unused")
abstract class BindingActivity<ViewDataBindingT extends ViewDataBinding, ViewModelV extends ViewModel> extends DaggerAppCompatActivity {

    private ViewDataBindingT viewDataBinding;
    private ViewModelV viewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewDataBinding = DataBindingUtil.setContentView(this, provideLayoutId());
        viewDataBinding.setLifecycleOwner(this);
        viewDataBinding.setVariable(ViewModelIdProvider.getViewModelId(), viewModel);
    }

    @Inject
    public void setViewModel(ViewModelV viewModel){
        this.viewModel = viewModel;
    }

    @LayoutRes
    protected abstract int provideLayoutId();

    protected ViewDataBindingT getViewDataBinding() {
        return viewDataBinding;
    }

    protected ViewModelV getViewModel() {
        return viewModel;
    }

}

