package com.hackatonwhoandroid.domain.repository;

import io.reactivex.rxjava3.core.Single;

public interface IWhoisRepository {

    Single<String> getWhois(String domain);

}
