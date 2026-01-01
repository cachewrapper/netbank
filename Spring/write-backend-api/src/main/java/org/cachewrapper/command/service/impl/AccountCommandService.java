package org.cachewrapper.command.service.impl;

import lombok.RequiredArgsConstructor;
import org.cachewrapper.command.domain.impl.AccountCreateCommand;
import org.cachewrapper.command.handler.impl.AccountCreateCommandHandler;
import org.cachewrapper.command.service.CommandService;
import org.cachewrapper.repository.aggregate.impl.AccountAggregateRepository;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * Service responsible for handling account-related commands.
 *
 * <p>This service validates the business logic specific to accounts,
 * constructs the corresponding {@link AccountCreateCommand}, and
 * dispatches it to the {@link AccountCreateCommandHandler}.
 *
 * <p>Implemented methods return a {@link ResponseEntity}&lt;String&gt;
 * indicating the result of the command execution, such as CREATED
 * or CONFLICT status.
 *
 * <p>Example flow:
 * <ol>
 *     <li>Check if the account already exists in the repository.</li>
 *     <li>If not, create a new AccountCreateCommand.</li>
 *     <li>Send the command to the handler for processing.</li>
 *     <li>Return appropriate HTTP response status.</li>
 * </ol>
 */
@Service
@RequiredArgsConstructor
public class AccountCommandService implements CommandService {

    private final AccountCreateCommandHandler accountCreateCommandService;
    private final AccountAggregateRepository accountAggregateRepository;

    /**
     * Creates a new account with the given UUID, username, and initial balance.
     *
     * @param accountUUID the unique identifier of the account
     * @param username    the username for the account
     * @param balance     the initial balance for the account
     * @return {@link ResponseEntity}&lt;String&gt; with HTTP status:
     *         <ul>
     *             <li>{@code 201 CREATED} if account was successfully created</li>
     *             <li>{@code 409 CONFLICT} if an account with the same UUID already exists</li>
     *         </ul>
     */
    @NotNull
    public ResponseEntity<String> createAccount(
            @NotNull UUID accountUUID,
            @NotNull String username,
            @NotNull BigDecimal balance
    ) {
        var isAccountAggregatePresent = accountAggregateRepository.findById(accountUUID).isPresent();
        if (isAccountAggregatePresent) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }

        var accountCreateCommand = new AccountCreateCommand(accountUUID, username, balance);
        accountCreateCommandService.handle(accountCreateCommand);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}