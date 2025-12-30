package org.cachewrapper.controller.request;

import java.math.BigDecimal;
import java.util.UUID;

public record AccountCreateRequest(
        String username,
        UUID userUUID,
        BigDecimal balance
) {}