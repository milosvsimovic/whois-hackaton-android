package com.hackatonwhoandroid.domain.repository;

import com.hackatonwhoandroid.domain.model.Message;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Observable;

public interface IMessageRepository {

    Completable sendMessageDomain(Message message);

    Observable<List<Message>> getFullMessages();

    Observable<List<Message>> getMessages();

    Completable updateMessages(List<Message> message);

    Completable showFavoriteMessages();

    Completable refreshMessages();
}
