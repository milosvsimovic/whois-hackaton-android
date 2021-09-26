package com.hackatonwhoandroid.domain.usecase;


import android.content.res.Resources;

import com.hackatonwhoandroid.R;
import com.hackatonwhoandroid.domain.model.Message;
import com.hackatonwhoandroid.domain.repository.IMessageRepository;
import com.hackatonwhoandroid.utils.base.domain.BaseParamUseCase;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Completable;

public class SendMessageDomainUseCase implements BaseParamUseCase<String, Completable> {

    Pattern domainValidatorPattern = Pattern.compile("^((?!-)[A-Za-z0-9\\p{IsCyrillic}-]{1,63}(?<!-)\\.)+[A-Za-z\\p{IsCyrillic}]{2,6}$");
    List<String> validDomains = Arrays.asList("rs", "срб", "net", "com");

    @Inject
    IMessageRepository messageRepository;

    @Inject
    Resources resources;

    @Inject
    SendMessageDomainUseCase() {
    }

    @Override
    public Completable execute(String domainName) {
        domainName = domainName.toLowerCase();
        Matcher matcher = domainValidatorPattern.matcher(domainName);

//        if("")

        if (matcher.matches()) {
            String[] split = domainName.split("\\.");
            if (validDomains.contains(split[1])) {
                return messageRepository.sendMessageMessage(createDomainMessage(domainName));
            } else {
                return messageRepository.sendMessageMessage(createBotMessageInvalidDomain(split));
            }
        } else {
            return messageRepository.sendMessageMessage(createBotMessageInvalidFormat(domainName));
        }
    }

    private Message createDomainMessage(String domainName) {
        Message message = new Message();
        message.setBody(domainName);
        message.setTimestamp(System.currentTimeMillis());
        message.setCreatedByUser(true);
        message.setFavorite(false);
        message.setType(Message.Type.DOMAIN_LOADING);
        message.setDomainStatus(null);
        return message;
    }

    private Message createBotMessageInvalidDomain(String[] split) {
        Message botMessage = new Message();
        botMessage.setBody(String.format(resources.getString(R.string.domain_is_not_suported_for_extension), split[1], validDomains.toString()));
        botMessage.setTimestamp(System.currentTimeMillis());
        botMessage.setCreatedByUser(false);
        botMessage.setFavorite(false);
        botMessage.setType(Message.Type.TEXT);
        return botMessage;
    }

    private Message createBotMessageInvalidFormat(String domainName) {
        Message botMessage = new Message();
        botMessage.setBody(String.format(resources.getString(R.string.domain_is_not_valid), domainName));
        botMessage.setTimestamp(System.currentTimeMillis());
        botMessage.setCreatedByUser(false);
        botMessage.setFavorite(false);
        botMessage.setType(Message.Type.TEXT);
        return botMessage;
    }
}