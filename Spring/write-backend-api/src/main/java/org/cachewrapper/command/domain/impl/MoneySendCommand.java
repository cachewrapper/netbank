package org.cachewrapper.command.domain.impl;

import org.cachewrapper.command.domain.Command;

import java.math.BigDecimal;
import java.util.UUID;

public record MoneySendCommand(
        UUID senderAccountUUID,
        UUID receiverAccountUUID,
        BigDecimal transactionAmount
) implements Command {}