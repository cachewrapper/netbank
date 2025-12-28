package org.cachewrapper.controller.request;

import java.math.BigDecimal;
import java.util.UUID;

public record AccountCreateRequest(
        UUID userUUID,
        BigDecimal balance
) {}