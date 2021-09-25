package com.hackatonwhoandroid.domain.model;

public enum DomainStatus {

    NotRegistered,
    Active,
    Inactive,
    Expired,
    PendingDelete,
    PendingUpdate,
    PendingTransfer,
    Dispute,

    ClientUpdateProhibited,

    RegistryLock,
    ServerUpdateProhibited,
    ServerHold,
    ServerTransferProhibited,
    ServerDeleteProhibited,
    ServerTradeProhibited,
    ServerRegistrantNameChangeProhibited,
    ServerContactDataChangeProhibited,
    ServerDnsChangeProhibited
}
