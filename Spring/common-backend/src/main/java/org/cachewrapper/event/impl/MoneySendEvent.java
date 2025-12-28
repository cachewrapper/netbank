package org.cachewrapper.event.impl;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import org.cachewrapper.event.BaseEvent;
import org.cachewrapper.payload.impl.AccountCreatedPayload;
import org.cachewrapper.payload.impl.MoneySendPayload;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

@Entity
@DiscriminatorValue("MONEY_SEND")
public class MoneySendEvent extends BaseEvent<MoneySendPayload> {

    public MoneySendEvent(@NotNull UUID aggregateUUID, @NotNull MoneySendPayload payload) {
        super(aggregateUUID, payload);
    }

    public MoneySendEvent() {

    }
}