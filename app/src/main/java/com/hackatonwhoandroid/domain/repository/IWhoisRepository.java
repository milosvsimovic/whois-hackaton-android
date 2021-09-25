package com.hackatonwhoandroid.domain.repository;

import com.hackatonwhoandroid.domain.model.Message;

import io.reactivex.rxjava3.core.Single;

public interface IWhoisRepository {

    Single<Message> getWhois(String domain);

}
