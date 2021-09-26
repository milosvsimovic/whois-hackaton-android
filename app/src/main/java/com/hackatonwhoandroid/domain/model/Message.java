package com.hackatonwhoandroid.domain.model;

import java.util.Arrays;
import java.util.List;

import lombok.Data;

@Data
public class Message {

    private String body;
    private long timestamp = System.currentTimeMillis();
    private boolean isCreatedByUser = false;
    private boolean isFavorite = false;
    private DomainStatus domainStatus;
    private Type type;

    public enum Type {
        TEXT,
        DOMAIN_LOADING,
        DOMAIN_ACTIVE,
        DOMAIN_INACTIVE,
        DOMAIN_OTHER,
        INFO;

        private static final List<Type> DOMAIN_RESPONSE_STATUS = Arrays.asList(DOMAIN_ACTIVE, DOMAIN_INACTIVE, DOMAIN_OTHER);

        public boolean isClickable() {
            return DOMAIN_RESPONSE_STATUS.contains(this);
        }
    }

}
