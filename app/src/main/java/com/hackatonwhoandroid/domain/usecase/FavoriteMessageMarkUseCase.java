package com.hackatonwhoandroid.domain.usecase;


import com.hackatonwhoandroid.domain.model.Message;
import com.hackatonwhoandroid.domain.repository.IMessageRepository;
import com.hackatonwhoandroid.utils.base.domain.BaseParamUseCase;

import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Completable;

public class FavoriteMessageMarkUseCase implements BaseParamUseCase<Message, Completable> {

    @Inject
    IMessageRepository messageRepository;

    @Inject
    FavoriteMessageMarkUseCase() {
    }

    @Override
    public Completable execute(Message message) {
        if (!Message.Type.isClickable(message.getType())) {
            return Completable.complete();
        }
        // toggle message favorite
        boolean favorite = !message.isFavorite();


        String domainName = message.getBody();
        List<Message> list = messageRepository.getFullMessages().firstElement().blockingGet();
        if (list == null || list.size() == 0) {
            return Completable.complete();
        }
        List<Message> updatedList = list.stream()
                .map(element -> {
                    if (domainName.equals(element.getBody())) {
                        // toggle message favorite
                        element.setFavorite(favorite);
                    }
                    return element;
                }).collect(Collectors.toList());

        return messageRepository.updateMessages(updatedList);
    }

}
