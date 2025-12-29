package org.cachewrapper.paper.rest.request.impl;

import org.cachewrapper.paper.rest.request.RequestPayload;

import java.math.BigDecimal;
import java.util.UUID;

public record AccountCreateRequest(
        UUID userUUID,
        BigDecimal balance
) implements RequestPayload {}