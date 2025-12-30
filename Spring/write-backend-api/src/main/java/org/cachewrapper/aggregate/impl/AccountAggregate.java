package org.cachewrapper.aggregate.impl;

import lombok.RequiredArgsConstructor;
import org.cachewrapper.aggregate.Aggregate;
import org.cachewrapper.command.domain.impl.AccountCreateCommand;
import org.cachewrapper.command.domain.impl.MoneySendCommand;
import org.cachewrapper.command.handler.impl.AccountCreateCommandHandler;
import org.cachewrapper.command.handler.impl.MoneySendCommandHandler;
import org.cachewrapper.event.impl.AccountCacheEventRebuilder;
import org.cachewrapper.exception.BalanceLessThanZeroException;
import org.cachewrapper.event.impl.AccountCreatedEvent;
import org.cachewrapper.repository.EventRepository;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@RequiredArgsConstructor
public class AccountAggregate implements Aggregate {

    private static final String ACCOUNT_CREATED = "Account created successfully";

    private static final String ACCOUNT_CONFLICT = "Account is already exists";
    private static final String ACCOUNT_NOT_EXISTS = "Account is not exists";

    private static final String TRANSACTION_AMOUNT_ERROR = "Transaction amount must greater than zero";
    private static final String TRANSACTION_SENT = "Money sent successfully";

    private final AccountCreateCommandHandler accountCreateCommandHandler;
    private final MoneySendCommandHandler moneySendCommandHandler;

    private final AccountCacheEventRebuilder accountCacheEventRebuilder;

    public ResponseEntity<String> createAccount(@NotNull AccountCreateCommand accountCreateCommand) {
        var userUUID = accountCreateCommand.userUUID();
        var account = accountCacheEventRebuilder.rebuild(userUUID);

        if (account != null) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(ACCOUNT_CONFLICT);
        }

        var balance = accountCreateCommand.balance();
        if (balance.compareTo(BigDecimal.ZERO) < 0) {
            throw new BalanceLessThanZeroException();
        }

        accountCreateCommandHandler.handle(accountCreateCommand);
        return ResponseEntity.ok().body(ACCOUNT_CREATED);
    }

    public ResponseEntity<String> sendMoney(@NotNull MoneySendCommand moneySendCommand) {
        var senderAccountUUID = moneySendCommand.senderAccountUUID();
        var receiverAccountUUID = moneySendCommand.receiverAccountUUID();
        var transactionAmount = moneySendCommand.transactionAmount();

        if (transactionAmount.compareTo(BigDecimal.ZERO) < 0) {
            throw new BalanceLessThanZeroException();
        }

        if (senderAccountUUID.equals(receiverAccountUUID)) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }

        var senderAccount = accountCacheEventRebuilder.rebuild(senderAccountUUID);
        if (senderAccount == null) {
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY).build();
        }

        if (senderAccount.getBalance().compareTo(transactionAmount) <= 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(TRANSACTION_AMOUNT_ERROR);
        }

        moneySendCommandHandler.handle(moneySendCommand);
        return ResponseEntity.ok().body(TRANSACTION_SENT);
    }
}