package org.cachewrapper.command.coordinator;

import lombok.RequiredArgsConstructor;
import org.cachewrapper.command.domain.impl.MoneySendCommand;
import org.cachewrapper.command.handler.impl.MoneySendCommandHandler;
import org.cachewrapper.repository.aggregate.impl.AccountAggregateRepository;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * Service responsible for handling money transfer commands between accounts.
 *
 * <p>This service validates the business logic specific to money transfers,
 * constructs the corresponding {@link MoneySendCommand}, and
 * dispatches it to the {@link MoneySendCommandHandler}.
 *
 * <p>Implemented methods return a {@link ResponseEntity}&lt;String&gt;
 * indicating the result of the command execution, such as OK, NOT FOUND, or BAD REQUEST.
 *
 * <p>Example flow:
 * <ol>
 *     <li>Check if the receiver account exists in the repository.</li>
 *     <li>Check if the sender account exists and has sufficient balance.</li>
 *     <li>Create a new MoneySendCommand.</li>
 *     <li>Send the command to the handler for processing.</li>
 *     <li>Return appropriate HTTP response status.</li>
 * </ol>
 */
@Service
@RequiredArgsConstructor
public class MoneyCommandCoordinator {

    private final MoneySendCommandHandler moneySendCommandHandler;
    private final AccountAggregateRepository accountAggregateRepository;

    /**
     * Creates a new money transfer from sender to receiver.
     *
     * @param senderAccountUUID   the unique identifier of the sender's account
     * @param receiverAccountUUID the unique identifier of the receiver's account
     * @param transactionAmount   the amount of money to transfer
     * @return {@link ResponseEntity}&lt;String&gt; with HTTP status:
     *         <ul>
     *             <li>{@code 200 OK} if the transfer was successfully created</li>
     *             <li>{@code 404 NOT FOUND} if either sender or receiver account does not exist</li>
     *             <li>{@code 400 BAD REQUEST} if sender's balance is insufficient</li>
     *         </ul>
     */
    @NotNull
    public ResponseEntity<String> createSendMoney(
            @NotNull UUID senderAccountUUID,
            @NotNull UUID receiverAccountUUID,
            @NotNull BigDecimal transactionAmount
    ) {
        var isReceiverAccountPresent = accountAggregateRepository.existsById(receiverAccountUUID);
        if (!isReceiverAccountPresent) {
            return ResponseEntity.notFound().build();
        }

        var senderAccount = accountAggregateRepository.findById(senderAccountUUID).orElse(null);
        if (senderAccount == null) {
            return ResponseEntity.notFound().build();
        }

        if (senderAccount.getBalance().compareTo(transactionAmount) < 0) {
            return ResponseEntity.badRequest().build();
        }

        var moneySendCommand = new MoneySendCommand(senderAccountUUID, receiverAccountUUID, transactionAmount);
        moneySendCommandHandler.handle(moneySendCommand);
        return ResponseEntity.ok().build();
    }
}
