package org.cachewrapper.paper.rest.request.impl;

import org.cachewrapper.paper.rest.request.RequestPayload;

import java.math.BigDecimal;
import java.util.UUID;

public record AccountCreatePayload(
        UUID userUUID,
        String username,
        BigDecimal balance
) implements RequestPayload {}