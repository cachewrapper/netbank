package org.cachewrapper.updater.impl;

import lombok.RequiredArgsConstructor;
import org.cachewrapper.event.impl.AccountCreatedEvent;
import org.cachewrapper.updater.ViewUpdater;
import org.cachewrapper.model.AccountViewModel;
import org.cachewrapper.repository.AccountViewRepository;
import org.jetbrains.annotations.NotNull;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * Updates account views when a {@link AccountCreatedEvent} occurs.
 * <p>
 * This component listens to the "account-created" Kafka topic and
 * creates a new record in the account view repository with the
 * account's UUID, username, and initial balance.
 * </p>
 */
@Component
@RequiredArgsConstructor
public class AccountCreateViewUpdater implements ViewUpdater<AccountCreatedEvent> {

    private final AccountViewRepository accountRepository;

    /**
     * Processes an {@link AccountCreatedEvent} and adds a new account view.
     * <p>
     * This method is transactional and listens to the "account-created" Kafka topic.
     * </p>
     *
     * @param event the AccountCreatedEvent to apply
     */
    @Override
    @Transactional
    @KafkaListener(topics = "account-created", groupId = "account-events-group")
    public void update(@NotNull AccountCreatedEvent event) {
        var payload = event.getPayload();

        var userUUID = payload.accountUUID();
        var username = payload.username();
        var balance = payload.balance();

        var account = new AccountViewModel(userUUID, username, balance);
        accountRepository.save(account);
    }
}