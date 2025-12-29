package org.cachewrapper.command.handler.impl;

import lombok.RequiredArgsConstructor;
import org.cachewrapper.command.domain.impl.MoneySendCommand;
import org.cachewrapper.command.handler.CommandHandler;
import org.cachewrapper.event.BaseEvent;
import org.cachewrapper.event.impl.AccountCacheEventRebuilder;
import org.cachewrapper.event.impl.MoneyReceiveEvent;
import org.cachewrapper.event.impl.MoneySendEvent;
import org.cachewrapper.payload.impl.MoneyReceivePayload;
import org.cachewrapper.payload.impl.MoneySendPayload;
import org.cachewrapper.repository.EventRepository;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MoneySendCommandHandler implements CommandHandler<MoneySendCommand> {

    private final KafkaTemplate<String, BaseEvent<?>> kafkaTemplate;
    private final EventRepository eventRepository;
    private final AccountCacheEventRebuilder accountCacheEventRebuilder;

    @Override
    public void handle(MoneySendCommand command) {
        var senderAccountUUID = command.senderAccountUUID();
        var receiverAccountUUID = command.receiverAccountUUID();
        var transactionAmount = command.transactionAmount();

        var moneySendPayload = new MoneySendPayload(senderAccountUUID, receiverAccountUUID, transactionAmount);
        var moneySendEvent = new MoneySendEvent(senderAccountUUID, moneySendPayload);

        var moneyReceivePayload = new MoneyReceivePayload(senderAccountUUID, receiverAccountUUID, transactionAmount);
        var moneyReceiveEvent = new MoneyReceiveEvent(receiverAccountUUID, moneyReceivePayload);

        eventRepository.save(moneySendEvent);
        eventRepository.save(moneyReceiveEvent);
        kafkaTemplate.send("money-send", moneySendEvent);
        kafkaTemplate.send("money-receive", moneyReceiveEvent);
        accountCacheEventRebuilder.applyEvent(senderAccountUUID, moneySendEvent);
        accountCacheEventRebuilder.applyEvent(receiverAccountUUID, moneyReceiveEvent);
    }
}
