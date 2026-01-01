package org.cachewrapper.command.service;

import org.springframework.http.ResponseEntity;

/**
 * The {@code CommandService} interface defines the contract for services
 * that validate business logic, construct commands, and dispatch them
 * to the appropriate command handler.
 *
 * <p>Implementing classes are responsible for:
 * <ul>
 *     <li>Performing specific logic checks or validations.</li>
 *     <li>Creating and configuring the corresponding command object.</li>
 *     <li>Sending the command to a handler for execution.</li>
 *     <li>Returning a {@link ResponseEntity}&lt;String&gt; as a result of the operation.</li>
 * </ul>
 *
 * <p>This design allows a clean separation between command creation,
 * validation logic, and command execution handling.
 */
public interface CommandService {}