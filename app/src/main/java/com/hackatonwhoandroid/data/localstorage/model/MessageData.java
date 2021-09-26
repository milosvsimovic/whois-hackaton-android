package com.hackatonwhoandroid.data.localstorage.model;

import com.hackatonwhoandroid.domain.model.DomainStatus;
import com.hackatonwhoandroid.domain.model.Message;

import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;

import java.util.List;
import java.util.Objects;

import lombok.Data;

@Data
public class MessageData {

    private String body;
    private long timestamp = System.currentTimeMillis();
    private boolean isCreatedByUser;
    private boolean isFavorite;
    private DomainStatus domainStatus;
    private Message.Type type;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MessageData that = (MessageData) o;
        return body.equals(that.body);
    }

    @Override
    public int hashCode() {
        return Objects.hash(body);
    }

    @Mapper
    public static abstract class Mappers {

        public abstract MessageData mapToData(Message message);

        public abstract Message mapFromData(MessageData data);

        @IterableMapping(elementTargetType = Message.class)
        public abstract List<Message> mapAllFromData(List<MessageData> list);

        @IterableMapping(elementTargetType = MessageData.class)
        public abstract List<MessageData> mapAllToData(List<Message> list);
    }

}
