package com.hackatonwhoandroid.utils.base.presentation;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;

import com.hackatonwhoandroid.utils.base.presentation.viewmodel.Action;
import com.hackatonwhoandroid.utils.base.presentation.viewmodel.BaseViewModel;

import javax.inject.Inject;

import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;


@SuppressWarnings("unused")
public abstract class BaseActivity<ViewDataBindingT extends ViewDataBinding, ViewModelT extends BaseViewModel> extends BindingActivity<ViewDataBindingT, ViewModelT> {

    @Inject
    CompositeDisposable compositeDisposable;

    @Override
    protected void onDestroy() {
        compositeDisposable.dispose();
        super.onDestroy();
    }

    protected void addDisposable(Disposable disposable) {
        compositeDisposable.add(disposable);
    }

    @SuppressWarnings("unchecked")
    protected <ActionCodeT> void observeActions(Observer<Action<ActionCodeT>> observer) {
        getViewModel().getActions().observe(this, observer);
    }

    public void addFragment(@IdRes int containerViewId, @NonNull Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .add(containerViewId, fragment)
                .addToBackStack(fragment.getClass().getSimpleName())
                .commit();
    }

    public void removeFragment(@IdRes int containerViewId, @NonNull Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .remove(fragment)
                .commit();
        getSupportFragmentManager().popBackStack();
    }

    public void replaceFragment(@IdRes int containerViewId, @NonNull Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(containerViewId, fragment)
                .addToBackStack(fragment.getClass().getSimpleName())
                .commit();
    }


}
