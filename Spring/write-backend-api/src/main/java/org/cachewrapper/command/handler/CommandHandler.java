package org.cachewrapper.command.handler;

import org.cachewrapper.command.domain.Command;
import org.jetbrains.annotations.NotNull;

/**
 * Generic interface for handling commands in a CQRS architecture.
 *
 * <p>Each implementation is responsible for processing a specific type of {@link Command}.
 * The handler executes the business logic, triggers events, and updates aggregates as needed.
 *
 * @param <T> the type of command this handler can process
 */
public interface CommandHandler<T extends Command> {

    /**
     * Handles the given command.
     *
     * <p>Implementations should contain the core logic for:
     * <ul>
     *     <li>Validating the command.</li>
     *     <li>Performing any necessary business operations.</li>
     *     <li>Generating and persisting events.</li>
     *     <li>Updating aggregate state.</li>
     *     <li>Notifying external systems if needed (e.g., via Kafka).</li>
     * </ul>
     *
     * @param command the command to handle, guaranteed to be non-null
     */
    void handle(@NotNull T command);
}
