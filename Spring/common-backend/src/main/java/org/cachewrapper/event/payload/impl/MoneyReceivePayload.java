package org.cachewrapper.event.payload.impl;

import org.cachewrapper.event.payload.Payload;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * Payload for the {@link org.cachewrapper.event.impl.MoneyReceiveEvent}.
 *
 * <p>
 * Contains the data required to process money reception for an account:
 * </p>
 * <ul>
 *     <li>{@code senderAccountUUID} — unique identifier of the account sending the money.</li>
 *     <li>{@code receiverAccountUUID} — unique identifier of the account receiving the money.</li>
 *     <li>{@code transactionAmount} — the amount of money being transferred.</li>
 * </ul>
 *
 * <p>
 * This payload is typically used when publishing the MoneyReceiveEvent to event stores
 * or messaging systems like Kafka, and is consumed by aggregates and view updaters
 * to update account balances.
 * </p>
 */
public record MoneyReceivePayload(
        UUID senderAccountUUID,
        UUID receiverAccountUUID,
        BigDecimal transactionAmount
) implements Payload {}