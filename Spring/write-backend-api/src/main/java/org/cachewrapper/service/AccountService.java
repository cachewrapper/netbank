package org.cachewrapper.service;

import lombok.RequiredArgsConstructor;
import org.cachewrapper.aggregate.impl.AccountAggregate;
import org.cachewrapper.command.domain.impl.AccountCreateCommand;
import org.cachewrapper.command.domain.impl.MoneySendCommand;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountAggregate accountAggregate;

    @NotNull
    public ResponseEntity<String> creatAccount(
            @NotNull UUID userUUID,
            @NotNull String username,
            @NotNull BigDecimal balance
    ) {
        var accountCreateCommand = new AccountCreateCommand(userUUID, username, balance);
        return accountAggregate.createAccount(accountCreateCommand);
    }

    @NotNull
    public ResponseEntity<String> sendMoney(
            @NotNull UUID senderAccountUUID,
            @NotNull UUID receiverAccountUUID,
            @NotNull BigDecimal transactionAmount
    ) {
        var sendMoneyCommand = new MoneySendCommand(senderAccountUUID, receiverAccountUUID, transactionAmount);
        return accountAggregate.sendMoney(sendMoneyCommand);
    }
}