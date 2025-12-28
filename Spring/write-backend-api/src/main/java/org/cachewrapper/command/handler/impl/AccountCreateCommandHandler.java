package org.cachewrapper.command.handler.impl;

import lombok.RequiredArgsConstructor;
import org.cachewrapper.command.domain.impl.AccountCreateCommand;
import org.cachewrapper.command.handler.CommandHandler;
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

    @Override
    public void handle(AccountCreateCommand command) {
        var accountUUID = command.userUUID();
        var balance = command.balance();

        var accountCreatedPayload = new AccountCreatedPayload(accountUUID, balance);
        var accountCreateEvent = new AccountCreatedEvent(accountUUID, accountCreatedPayload);

        eventRepository.save(accountCreateEvent);
        kafkaTemplate.send("account-created", accountCreateEvent);
    }
}