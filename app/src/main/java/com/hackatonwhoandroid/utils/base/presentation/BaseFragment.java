package com.hackatonwhoandroid.utils.base.presentation;

import androidx.databinding.ViewDataBinding;

import com.hackatonwhoandroid.utils.base.presentation.viewmodel.BaseViewModel;

import javax.inject.Inject;

import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;

@SuppressWarnings("unused")
public abstract class BaseFragment<ViewDataBindingT extends ViewDataBinding, ViewModelV extends BaseViewModel>
        extends BindingFragment<ViewDataBindingT, ViewModelV> {

    @Inject
    CompositeDisposable compositeDisposable;

    @Override
    public void onDestroyView() {
        compositeDisposable.dispose();
        super.onDestroyView();
    }

    protected void addDisposable(Disposable disposable) {
        compositeDisposable.add(disposable);
    }

}
