package org.cachewrapper.listener;

import lombok.RequiredArgsConstructor;
import org.cachewrapper.event.impl.AccountCreatedEvent;
import org.cachewrapper.model.Account;
import org.cachewrapper.repository.AccountRepository;
import org.jetbrains.annotations.NotNull;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AccountCreateEventListener {

    private final AccountRepository accountRepository;

    @KafkaListener(topics = "account-created", groupId = "account-events-group")
    public void listener(@NotNull AccountCreatedEvent accountCreatedEvent) {
        var payload = accountCreatedEvent.getPayload();

        var userUUID = payload.userUUID();
        var balance = payload.balance();

        var account = new Account(userUUID, balance);
        accountRepository.save(account);
    }
}