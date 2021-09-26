package com.hackatonwhoandroid.domain.model;

import org.joda.time.DateTime;

import java.util.Arrays;
import java.util.List;

import lombok.Data;

@Data
public class Message {

    private String body;
    private DateTime timestamp = DateTime.now();
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

        private static final List<Type> CLICKABLE_STATUS = Arrays.asList(DOMAIN_ACTIVE, DOMAIN_INACTIVE, DOMAIN_OTHER);

        public boolean isClickable() {
            return CLICKABLE_STATUS.contains(this);
        }


    }

}
