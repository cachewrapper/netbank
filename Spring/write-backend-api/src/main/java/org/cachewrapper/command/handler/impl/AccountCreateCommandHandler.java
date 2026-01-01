package org.cachewrapper.command.handler.impl;

import lombok.RequiredArgsConstructor;
import org.cachewrapper.aggregate.mapper.service.AccountAggregateMapperService;
import org.cachewrapper.command.domain.impl.AccountCreateCommand;
import org.cachewrapper.command.handler.CommandHandler;
import org.cachewrapper.event.BaseEvent;
import org.cachewrapper.event.impl.AccountCreatedEvent;
import org.cachewrapper.event.payload.impl.AccountCreatedPayload;
import org.cachewrapper.repository.event.EventRepository;
import org.jetbrains.annotations.NotNull;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AccountCreateCommandHandler implements CommandHandler<AccountCreateCommand> {

    private final KafkaTemplate<String, BaseEvent<?>> kafkaTemplate;
    private final EventRepository eventRepository;
    private final AccountAggregateMapperService accountAggregateMapper;

    @Override
    public void handle(@NotNull AccountCreateCommand command) {
        var accountUUID = command.acacountUUID();
        var username = command.username();
        var balance = command.balance();

        var accountCreatedPayload = new AccountCreatedPayload(accountUUID, username, balance);
        var accountCreateEvent = new AccountCreatedEvent(accountUUID, accountCreatedPayload);

        accountAggregateMapper.applyAndSave(accountUUID, accountCreateEvent);
        eventRepository.save(accountCreateEvent);
        kafkaTemplate.send("account-created", accountCreateEvent);
    }
}