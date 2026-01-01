package org.cachewrapper.command.domain.impl;

import org.cachewrapper.command.domain.Command;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * Command representing a request to transfer money from one account to another.
 *
 * <p>This command contains all the necessary information to perform a money transfer,
 * including:
 * <ul>
 *     <li>{@code senderAccountUUID} — the unique identifier of the sender's account.</li>
 *     <li>{@code receiverAccountUUID} — the unique identifier of the receiver's account.</li>
 *     <li>{@code transactionAmount} — the amount of money to transfer.</li>
 * </ul>
 *
 * <p>It implements the {@link Command} interface and is handled by a
 * {@link org.cachewrapper.command.handler.impl.MoneySendCommandHandler}.
 *
 * <p>Instances of this record are immutable and should be fully populated
 * when dispatched to the command handler.
 */
public record MoneySendCommand(
        UUID senderAccountUUID,
        UUID receiverAccountUUID,
        BigDecimal transactionAmount
) implements Command {}
