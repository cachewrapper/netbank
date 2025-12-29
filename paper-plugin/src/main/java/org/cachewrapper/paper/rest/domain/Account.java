package org.cachewrapper.paper.rest.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@RequiredArgsConstructor
public class Account {

    private final UUID accountUUID;
    private final BigDecimal balance;
}