package com.hackatonwhoandroid.domain.usecase;


import com.hackatonwhoandroid.domain.repository.IMessageRepository;
import com.hackatonwhoandroid.utils.base.domain.BaseUseCase;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Completable;

public class ShowFavoriteMessagesUseCase implements BaseUseCase<Completable> {

    @Inject
    IMessageRepository messageRepository;

    @Inject
    ShowFavoriteMessagesUseCase() {
    }

    @Override
    public Completable execute() {
        return messageRepository.showFavoriteMessages();
    }

}
