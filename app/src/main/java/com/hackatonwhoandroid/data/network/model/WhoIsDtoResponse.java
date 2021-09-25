package com.hackatonwhoandroid.data.network.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
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

        @Mapping(target = "body", expression = "java(convertToBody(response))")
        @Mapping(target = "type", expression = "java(getType())")
        @Mapping(target = "timeStamp", ignore = true)
        @Mapping(target = "createdByUser", ignore = true)
        @Mapping(target = "favorite", ignore = true)
        public abstract Message mapToMessage(WhoIsDtoResponse response);

        String convertToBody(WhoIsDtoResponse response) {
            StringBuilder bodyMessage = new StringBuilder();
            DomainStatus domainStatus = DomainStatus.valueOf(response.domainStatus);
            if (DomainStatus.ACTIVE == domainStatus) {
                bodyMessage
                        .append("\uD83D\uDE15 Domen ").append(response.getDomainName()).append(" je već registrovan.").append("\n\n")
                        .append("Registrovan: ").append(response.getRegistrationDate()).append("\n\n")
                        .append("Ističe: ").append(response.getExpirationDate());
            } else if (DomainStatus.INACTIVE == domainStatus) {
                bodyMessage
                        .append("\uD83D\uDE00 Sjajno! ").append(response.getDomainName()).append(" je slobodan!").append("\n\n")
                        .append("Odaberi neku akciju za ovaj domen");

            }

            return bodyMessage.toString();
        }

        Message.Type getType() {
            return Message.Type.INFO;
        }
    }

}

