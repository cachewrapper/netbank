package org.cachewrapper.command.domain.impl;

import org.cachewrapper.command.domain.Command;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * Command representing a request to create a new account.
 *
 * <p>This command contains all the necessary information to create an account,
 * including:
 * <ul>
 *     <li>{@code acacountUUID} — the unique identifier of the account.</li>
 *     <li>{@code username} — the username of the account owner.</li>
 *     <li>{@code balance} — the initial balance of the account.</li>
 * </ul>
 *
 * <p>It implements the {@link Command} interface and is handled by a
 * {@link org.cachewrapper.command.handler.impl.AccountCreateCommandHandler}.
 *
 * <p>Instances of this record are immutable and should be fully populated
 * when dispatched to the command handler.
 */
public record AccountCreateCommand(
        UUID acacountUUID,
        String username,
        BigDecimal balance
) implements Command {}
