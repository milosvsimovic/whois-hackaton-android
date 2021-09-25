package com.hackatonwhoandroid.domain.model;

import org.joda.time.DateTime;

import lombok.Data;

@Data
public class Message {

    private String body;
    private DateTime timeStamp;
    private boolean isCreatedByUser = false;


}
