package org.cachewrapper.listener.impl;

import lombok.RequiredArgsConstructor;
import org.cachewrapper.event.impl.AccountCreatedEvent;
import org.cachewrapper.listener.Listener;
import org.cachewrapper.model.Account;
import org.cachewrapper.repository.AccountRepository;
import org.jetbrains.annotations.NotNull;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class AccountCreateEventListener implements Listener<AccountCreatedEvent> {

    private final AccountRepository accountRepository;

    @Override
    @Transactional
    @KafkaListener(topics = "account-created", groupId = "account-events-group")
    public void listener(@NotNull AccountCreatedEvent event) {
        var payload = event.getPayload();

        var userUUID = payload.userUUID();
        var username = payload.username();
        var balance = payload.balance();

        var account = new Account(userUUID, username, balance);
        accountRepository.save(account);
    }
}