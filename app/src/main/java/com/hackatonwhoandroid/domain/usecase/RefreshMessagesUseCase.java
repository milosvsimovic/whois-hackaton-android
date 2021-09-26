package com.hackatonwhoandroid.domain.usecase;


import com.hackatonwhoandroid.domain.model.Message;
import com.hackatonwhoandroid.domain.repository.IMessageRepository;
import com.hackatonwhoandroid.utils.base.domain.BaseUseCase;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Observable;


public class RefreshMessagesUseCase implements BaseUseCase<Completable> {

    @Inject
    IMessageRepository messageRepository;

    @Inject
    RefreshMessagesUseCase() {
    }

    @Override
    public Completable execute() {
        return messageRepository.refreshMessages();
    }

}
