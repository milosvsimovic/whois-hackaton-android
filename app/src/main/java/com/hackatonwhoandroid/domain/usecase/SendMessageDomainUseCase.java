package com.hackatonwhoandroid.domain.usecase;


import com.hackatonwhoandroid.domain.model.Message;
import com.hackatonwhoandroid.domain.repository.IMessageRepository;
import com.hackatonwhoandroid.utils.base.domain.BaseParamUseCase;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Completable;

public class SendMessageDomainUseCase implements BaseParamUseCase<String, Completable> {

    @Inject
    IMessageRepository syncRepository;

    @Inject
    SendMessageDomainUseCase() {
    }

    @Override
    public Completable execute(String domainName) {
        return syncRepository.sendMessageDomain(createDomainMessage(domainName));
    }

    private Message createDomainMessage(String domain) {
        Message message = new Message();
        message.setBody(domain);
        message.setTimestamp(System.currentTimeMillis());
        message.setCreatedByUser(true);
        message.setFavorite(false);
        message.setType(Message.Type.DOMAIN_LOADING);
        message.setDomainStatus(null);
        return message;
    }

}
