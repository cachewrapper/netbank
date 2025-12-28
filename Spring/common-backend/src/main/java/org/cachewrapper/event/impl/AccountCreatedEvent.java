package org.cachewrapper.event.impl;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import org.cachewrapper.event.BaseEvent;
import org.cachewrapper.payload.impl.AccountCreatedPayload;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

@Entity
@DiscriminatorValue("ACCOUNT_CREATED")
public class AccountCreatedEvent extends BaseEvent<AccountCreatedPayload> {

    public AccountCreatedEvent(@NotNull UUID aggregateUUID, @NotNull AccountCreatedPayload payload) {
        super(aggregateUUID, payload);
    }

    public AccountCreatedEvent() {

    }
}