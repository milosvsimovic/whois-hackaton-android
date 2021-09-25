package com.hackatonwhoandroid.data.network.model;

import android.content.res.Resources;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.hackatonwhoandroid.R;
import com.hackatonwhoandroid.domain.model.DomainStatus;
import com.hackatonwhoandroid.domain.model.Message;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import lombok.Data;
import lombok.ToString;

@ToString
@Data
public class WhoIsDtoResponse {

    @SerializedName("domain_name")
    @Expose
    public String domainName;
    @SerializedName("domain_status")
    @Expose
    public String domainStatus;
    @SerializedName("registration_date")
    @Expose
    public String registrationDate;
    @SerializedName("expiration_date")
    @Expose
    public String expirationDate;

    @Mapper
    public static abstract class Mappers {

        @Mapping(target = "body", expression = "java(convertToBody(response, resources))")
        @Mapping(target = "type", expression = "java(getType())")
        @Mapping(target = "timestamp", ignore = true)
        @Mapping(target = "createdByUser", ignore = true)
        @Mapping(target = "favorite", ignore = true)
        public abstract Message mapToMessage(WhoIsDtoResponse response, Resources resources);

        String convertToBody(WhoIsDtoResponse response, Resources resources) {
            String body;
            DomainStatus domainStatus = DomainStatus.valueOf(response.domainStatus);
            switch (domainStatus) {
                case Active:
                    body = String.format(resources.getString(R.string.chat_domain_is_already_registred),
                            response.getDomainName(), response.getRegistrationDate(), response.getExpirationDate());
                    break;
                case Inactive:
                case NotRegistered:
                    body = String.format(resources.getString(R.string.chat_domain_is_available),
                            response.getDomainName());
                    break;
                case Expired:
                    body = String.format(resources.getString(R.string.chat_domain_expired_not_available_yet),
                            response.getDomainName(), response.getExpirationDate());
                    break;
                default:
                    body = String.format(resources.getString(R.string.chat_domain_not_available),
                            response.getDomainName());
            }
            return body;
        }

        Message.Type getType() {
            return Message.Type.INFO;
        }
    }

}

