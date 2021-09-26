package com.hackatonwhoandroid.data.localstorage.model;

import com.hackatonwhoandroid.domain.model.DomainStatus;
import com.hackatonwhoandroid.domain.model.Message;

import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;

import java.util.List;

import lombok.Data;

@Data
public class MessageData {

    private String body;
    private long timestamp = System.currentTimeMillis();
    private boolean isCreatedByUser = false;
    private boolean isFavorite = false;
    private DomainStatus domainStatus;
    private Message.Type type;

    @Mapper
    public static abstract class Mappers {

        public abstract MessageData mapToData(Message message);

        public abstract Message mapFromData(MessageData data);

        @IterableMapping(elementTargetType = Message.class)
        public abstract List<Message> mapAllFromData(List<MessageData> list);
    }

}
