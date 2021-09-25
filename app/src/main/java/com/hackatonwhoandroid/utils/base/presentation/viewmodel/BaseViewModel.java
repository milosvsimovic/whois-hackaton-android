package com.hackatonwhoandroid.utils.base.presentation.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import javax.inject.Inject;

import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;

@SuppressWarnings("unused")
public abstract class BaseViewModel<ActionCodeT> extends ViewModel {

    @Inject
    CompositeDisposable compositeDisposable;

    @Inject
    ActionProvider actionProvider;

    private final MutableLiveData<Action<ActionCodeT>> action = new MutableLiveData<>();

    public MutableLiveData<Action<ActionCodeT>> getActions() {
        return action;
    }

    protected void dispatchAction(ActionCodeT code) {
        action.setValue(actionProvider.provide(code));
    }

    protected void dispatchAction(ActionCodeT code, Object data) {
        action.setValue(actionProvider.provide(code, data));
    }

    protected void addDisposable(Disposable disposable) {
        compositeDisposable.add(disposable);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        compositeDisposable.dispose();
    }

}
