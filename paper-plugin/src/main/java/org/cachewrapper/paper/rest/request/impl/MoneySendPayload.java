package org.cachewrapper.paper.rest.request.impl;

import org.cachewrapper.paper.rest.request.RequestPayload;

import java.math.BigDecimal;
import java.util.UUID;

public record MoneySendPayload(
        UUID senderAccountUUID,
        UUID receiverAccountUUID,
        BigDecimal transactionAmount
) implements RequestPayload {}