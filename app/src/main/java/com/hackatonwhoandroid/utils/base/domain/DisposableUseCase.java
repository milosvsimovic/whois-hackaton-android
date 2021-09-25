package com.hackatonwhoandroid.utils.base.domain;

import javax.inject.Inject;

import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;

class DisposableUseCase {

    @Inject
    CompositeDisposable compositeDisposable;

    protected void addDisposable(Disposable disposable) {
        compositeDisposable.add(disposable);
    }

    public void clearDisposables() {
        compositeDisposable.dispose();
    }

}
