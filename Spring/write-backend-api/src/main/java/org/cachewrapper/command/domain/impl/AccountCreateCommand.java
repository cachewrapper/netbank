package org.cachewrapper.command.domain.impl;

import org.cachewrapper.command.domain.Command;

import java.math.BigDecimal;
import java.util.UUID;

public record AccountCreateCommand(
        UUID userUUID,
        String username,
        BigDecimal balance
) implements Command {}