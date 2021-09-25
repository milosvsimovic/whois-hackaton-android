package com.hackatonwhoandroid.presentation.chat;

import com.hackatonwhoandroid.domain.model.Message;

import org.jetbrains.annotations.NotNull;
import org.joda.time.DateTime;
import org.mapstruct.Mapper;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MessageModel {

    public String body;
    @NotNull
    public DateTime timeStamp;
    private boolean isCreatedByUser;
    private boolean isFavorite;
    private Message.Type type;

    @Mapper
    public static abstract class Mappers {
        abstract MessageModel map(Message message);
    }

}
