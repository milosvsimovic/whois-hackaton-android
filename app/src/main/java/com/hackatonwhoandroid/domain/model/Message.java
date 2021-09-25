package com.hackatonwhoandroid.domain.model;

import org.joda.time.DateTime;

import lombok.Data;

@Data
public class Message {

    private String body;
    private DateTime timeStamp = DateTime.now();
    private boolean isCreatedByUser = false;
    private boolean isFavorite = false;
    private Type type;

    public enum Type {
        TEXT,
        DOMAIN,
        INFO
    }

}
