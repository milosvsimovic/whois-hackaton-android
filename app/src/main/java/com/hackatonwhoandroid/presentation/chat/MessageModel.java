package com.hackatonwhoandroid.presentation.chat;

import static com.hackatonwhoandroid.utils.ValidatorUtil.domainValidatorPattern;

import android.content.res.Resources;

import com.hackatonwhoandroid.R;
import com.hackatonwhoandroid.domain.model.DomainStatus;
import com.hackatonwhoandroid.domain.model.Message;

import org.apache.commons.lang3.StringUtils;
import org.mapstruct.Context;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;
import java.util.regex.Matcher;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MessageModel {

    public String body;
    public String domainName;
    public String domainExtension;
    public String statusMessage;
    public DomainStatus domainStatus;
    public long timestamp;
    private boolean isCreatedByUser;
    private boolean isFavorite;
    private Message.Type type;
    private boolean isVisible = true;

    @Mapper
    public abstract static class Mappers {

        @Mapping(target = "visible", ignore = true)
        @Mapping(target = "domainName", expression = "java(getDomainName(message))")
        @Mapping(target = "domainExtension", expression = "java(getDomainExtension(message))")
        @Mapping(target = "statusMessage", expression = "java(getStatusMessage(message, resources))")
        public abstract MessageModel map(Message message, @Context Resources resources);

        public abstract Message mapToMessage(MessageModel message);

        @IterableMapping(elementTargetType = MessageModel.class)
        public abstract List<MessageModel> mapAll(List<Message> list, @Context Resources resources);

        String getStatusMessage(Message message, Resources resources) {
            DomainStatus domainStatus = message.getDomainStatus();
            if (domainStatus == null) {
                return null;
            }
            switch (domainStatus) {
                case Active:
                    return resources.getString(R.string.chat_status_message_active);
                case NotRegistered:
                case Inactive:
                    return resources.getString(R.string.chat_status_message_inactive);
                default:
                    return resources.getString(R.string.chat_status_message_otherStatus);
            }
        }

        String getDomainName(Message message){
            if(message.getType().isClickable() || message.getType() == Message.Type.DOMAIN_LOADING){
                String domainName = message.getBody();
                domainName = domainName.toLowerCase();
                try{
                    Matcher matcher = domainValidatorPattern.matcher(domainName);
                    if (matcher.matches()) {
                        String[] split = domainName.split("\\.");
                        return split[0];
                    }
                    return StringUtils.EMPTY;
                } catch (Exception e){
                    return StringUtils.EMPTY;
                }
            }
            return StringUtils.EMPTY;
        }

        String getDomainExtension(Message message){
            if(message.getType().isClickable() || message.getType() == Message.Type.DOMAIN_LOADING){
                String domainName = message.getBody();
                domainName = domainName.toLowerCase();
                try{
                    Matcher matcher = domainValidatorPattern.matcher(domainName);
                    if (matcher.matches()) {
                        String[] split = domainName.split("\\.");
                        return "." + split[1];
                    }
                    return StringUtils.EMPTY;
                } catch (Exception e){
                    return StringUtils.EMPTY;
                }
            }
            return StringUtils.EMPTY;
        }

    }

}
