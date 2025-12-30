package org.cachewrapper.payload.impl;

import org.cachewrapper.payload.Payload;

import java.math.BigDecimal;
import java.util.UUID;

public record AccountCreatedPayload(
        UUID userUUID,
        String username,
        BigDecimal balance
) implements Payload {}