package org.cachewrapper.event.payload.impl;

import org.cachewrapper.event.payload.Payload;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * Payload for the {@link org.cachewrapper.event.impl.AccountCreatedEvent}.
 *
 * <p>
 * Contains all necessary data to create a new account aggregate:
 * </p>
 * <ul>
 *     <li>{@code accountUUID} — unique identifier of the account.</li>
 *     <li>{@code username} — the username associated with the account.</li>
 *     <li>{@code balance} — the initial balance of the account.</li>
 * </ul>
 *
 * <p>
 * This payload is typically used when publishing the AccountCreatedEvent to event stores
 * or messaging systems like Kafka, and is consumed by aggregates and view updaters.
 * </p>
 */
public record AccountCreatedPayload(
        UUID accountUUID,
        String username,
        BigDecimal balance
) implements Payload {}