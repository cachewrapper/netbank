package org.cachewrapper.aggregate.impl;

import lombok.RequiredArgsConstructor;
import org.cachewrapper.aggregate.Aggregate;
import org.cachewrapper.command.domain.impl.AccountCreateCommand;
import org.cachewrapper.command.domain.impl.MoneySendCommand;
import org.cachewrapper.command.handler.impl.AccountCreateCommandHandler;
import org.cachewrapper.command.handler.impl.MoneySendCommandHandler;
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
    private static final String MONEY_SENT = "Money sent successfully";

    private static final String ACCOUNT_CONFLICT = "Account is already exists";
    private static final String ACCOUNT_NOT_EXISTS = "Account is not exists";

    private final AccountCreateCommandHandler accountCreateCommandHandler;
    private final MoneySendCommandHandler moneySendCommandHandler;
    private final EventRepository eventRepository;

    public ResponseEntity<String> createAccount(@NotNull AccountCreateCommand accountCreateCommand) {
        var userUUID = accountCreateCommand.userUUID();
        var lastAccountCreatedEvent = eventRepository
                .findFirstByAggregateUUIDAndTypeOrderByCreatedAtDesc(userUUID, AccountCreatedEvent.class)
                .orElse(null);

        if (lastAccountCreatedEvent != null) {
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

        var lastAccountCreatedEvent = eventRepository
                .findFirstByAggregateUUIDAndTypeOrderByCreatedAtDesc(receiverAccountUUID, AccountCreatedEvent.class)
                .orElse(null);
        if (lastAccountCreatedEvent == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ACCOUNT_NOT_EXISTS);
        }

        moneySendCommandHandler.handle(moneySendCommand);
        return ResponseEntity.ok().body(MONEY_SENT);
    }
}