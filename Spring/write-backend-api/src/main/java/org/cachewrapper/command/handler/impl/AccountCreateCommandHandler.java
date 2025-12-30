package org.cachewrapper.command.handler.impl;

import lombok.RequiredArgsConstructor;
import org.cachewrapper.command.domain.impl.AccountCreateCommand;
import org.cachewrapper.command.handler.CommandHandler;
import org.cachewrapper.event.impl.AccountCacheEventRebuilder;
import org.cachewrapper.event.impl.AccountCreatedEvent;
import org.cachewrapper.event.BaseEvent;
import org.cachewrapper.payload.impl.AccountCreatedPayload;
import org.cachewrapper.repository.EventRepository;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AccountCreateCommandHandler implements CommandHandler<AccountCreateCommand> {

    private final KafkaTemplate<String, BaseEvent<?>> kafkaTemplate;
    private final EventRepository eventRepository;
    private final AccountCacheEventRebuilder accountCacheEventRebuilder;

    @Override
    public void handle(AccountCreateCommand command) {
        var accountUUID = command.userUUID();
        var username = command.username();
        var balance = command.balance();

        var accountCreatedPayload = new AccountCreatedPayload(accountUUID, username, balance);
        var accountCreateEvent = new AccountCreatedEvent(accountUUID, accountCreatedPayload);

        eventRepository.save(accountCreateEvent);
        kafkaTemplate.send("account-created", accountCreateEvent);
        accountCacheEventRebuilder.applyEvent(accountUUID, accountCreateEvent);
    }
}