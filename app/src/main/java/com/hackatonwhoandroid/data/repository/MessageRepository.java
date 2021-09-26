package com.hackatonwhoandroid.data.repository;

import android.content.res.Resources;

import com.hackatonwhoandroid.data.localstorage.LocalStorage;
import com.hackatonwhoandroid.data.localstorage.model.MessageData;
import com.hackatonwhoandroid.data.network.api.RESTApiMethods;
import com.hackatonwhoandroid.data.network.model.WhoIsDtoResponse;
import com.hackatonwhoandroid.domain.model.Message;
import com.hackatonwhoandroid.domain.repository.IMessageRepository;

import org.mapstruct.factory.Mappers;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Observable;

public class MessageRepository implements IMessageRepository {

    @Inject
    RESTApiMethods apiMethodsServer;

    @Inject
    LocalStorage localStorage;

    @Inject
    Resources resources;

    @Inject
    MessageRepository() {
    }

    @Override
    public Observable<List<Message>> getFullMessages() {
        return Observable.just(localStorage.getList())
                .map(data -> Mappers.getMapper(MessageData.Mappers.class).mapAllFromData(data));
    }

    @Override
    public Observable<List<Message>> getMessages() {
        return localStorage.getMessages()
                .map(data -> Mappers.getMapper(MessageData.Mappers.class).mapAllFromData(data));
    }

    @Override
    public Completable sendMessageMessage(Message message) {
        localStorage.addMessage(Mappers.getMapper(MessageData.Mappers.class).mapToData(message));
        return apiMethodsServer.fetchWhoisDomain(message.getBody())
                .map(whoisResponse -> {
                    Message response = Mappers.getMapper(WhoIsDtoResponse.Mappers.class).mapToMessage(whoisResponse, resources);

                    // update domain message DomainStatus and Type
                    message.setDomainStatus(response.getDomainStatus());
                    switch (response.getDomainStatus()) {
                        case Active:
                            message.setType(Message.Type.DOMAIN_ACTIVE);
                            break;
                        case NotRegistered:
                        case Inactive:
                            message.setType(Message.Type.DOMAIN_INACTIVE);
                            break;
                        default:
                            message.setType(Message.Type.DOMAIN_OTHER);
                            break;
                    }
                    localStorage.updateMessage(Mappers.getMapper(MessageData.Mappers.class).mapToData(message));

                    // add whois response message
                    localStorage.addMessage(Mappers.getMapper(MessageData.Mappers.class).mapToData(response));
                    return whoisResponse;
                })
                .ignoreElement();
    }

    @Override
    public Completable updateMessages(List<Message> message) {
        localStorage.updateMessageList(Mappers.getMapper(MessageData.Mappers.class).mapAllToData(message));
        return Completable.complete();
    }

    @Override
    public Completable showFavoriteMessages() {
        localStorage.showFavoritesOnly();
        return Completable.complete();
    }

    @Override
    public Completable refreshMessages() {
        localStorage.refreshMessages();
        return Completable.complete();
    }
}
