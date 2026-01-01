package org.cachewrapper.event.payload.impl;

import org.cachewrapper.event.payload.Payload;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * Payload for the {@link org.cachewrapper.event.impl.MoneySendEvent}.
 *
 * <p>
 * Contains the data required to process money sending from an account:
 * </p>
 * <ul>
 *     <li>{@code senderAccountUUID} — unique identifier of the account sending the money.</li>
 *     <li>{@code receiverAccountUUID} — unique identifier of the account receiving the money.</li>
 *     <li>{@code transactionAmount} — the amount of money being transferred.</li>
 * </ul>
 *
 * <p>
 * This payload is used when publishing the MoneySendEvent to event stores
 * or messaging systems like Kafka, and is consumed by aggregates and view updaters
 * to deduct the balance from the sender account.
 * </p>
 */
public record MoneySendPayload(
        UUID senderAccountUUID,
        UUID receiverAccountUUID,
        BigDecimal transactionAmount
) implements Payload {}