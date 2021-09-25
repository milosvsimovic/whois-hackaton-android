package com.hackatonwhoandroid.presentation.chat;

import org.jetbrains.annotations.NotNull;
import org.joda.time.DateTime;
import org.mapstruct.Mapper;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MessageModel {

    public String body;
    @NotNull
    public DateTime timeStamp;
    private boolean isCreatedByUser;

    @Mapper
    public static abstract class Mappers {
//        abstract MessageModel map(Message message);
    }

}
