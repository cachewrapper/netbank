package org.cachewrapper.event.impl;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import org.cachewrapper.event.BaseEvent;
import org.cachewrapper.payload.impl.MoneyReceivePayload;
import org.cachewrapper.payload.impl.MoneySendPayload;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

@Entity
@DiscriminatorValue("MONEY_RECEIVE")
public class MoneyReceiveEvent extends BaseEvent<MoneyReceivePayload> {

    public MoneyReceiveEvent(@NotNull UUID aggregateUUID, @NotNull MoneyReceivePayload payload) {
        super(aggregateUUID, payload);
    }

    public MoneyReceiveEvent() {

    }
}