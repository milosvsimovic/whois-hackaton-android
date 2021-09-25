package com.hackatonwhoandroid.domain.model;

import org.joda.time.DateTime;

public class Message {

    private String body;
    private DateTime timeStamp;
    private boolean isCreatedByUser = false;
    private boolean isFavorite;
    private Type type;

    public enum Type {
        TEXT,
        DOMAIN,
        INFO
    }

}
