package com.hackatonwhoandroid.domain.model;

public enum DomainStatus {

    NotRegistered,
    Reserved,
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
    ServerDnsChangeProhibited,

    PendingCreate,
    PendingRenew,
    AutoRenewPeriod,
    PendingRestore,
    RedemptionPeriod,
    RenewPeriod,
    ServerRenewProhibited,
    TransferPeriod,
    ClientDeleteProhibited,
    ClientHold,
    ClientRenewProhibited,
    ClientTransferProhibited,
}
