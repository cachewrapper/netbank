package org.cachewrapper.payload.impl;

import org.cachewrapper.payload.Payload;

import java.math.BigDecimal;
import java.util.UUID;

public record MoneyReceivePayload(
        UUID senderAccountUUID,
        UUID receiverAccountUUID,
        BigDecimal transactionAmount
) implements Payload {}