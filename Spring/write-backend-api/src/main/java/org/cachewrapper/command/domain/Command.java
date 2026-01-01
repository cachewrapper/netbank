package org.cachewrapper.command.domain;

/**
 * Marker interface for a command in a CQRS architecture.
 *
 * <p>A command represents an intention to perform a specific action or change
 * the state of the system. Commands are handled by {@link org.cachewrapper.command.handler.CommandHandler}
 * implementations, which contain the business logic for executing the command.
 *
 * <p>Commands should be immutable and contain all the information necessary
 * to perform the requested action.
 */
public interface Command {}