package org.cachewrapper.controller.request;

import java.math.BigDecimal;
import java.util.UUID;

public record MoneySendRequest(
        UUID senderAccountUUID,
        UUID receiverAccountUUID,
        BigDecimal transactionAmount
) {}