package com.hackatonwhoandroid.domain.usecase;


import com.hackatonwhoandroid.domain.model.Message;
import com.hackatonwhoandroid.domain.repository.IMessageRepository;
import com.hackatonwhoandroid.utils.base.domain.BaseUseCase;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Observable;


public class GetMessagesUseCase implements BaseUseCase<Observable<List<Message>>> {

    @Inject
    IMessageRepository syncRepository;

    @Inject
    GetMessagesUseCase() {
    }

    @Override
    public Observable<List<Message>> execute() {
        return syncRepository.getMessages();
    }

}
