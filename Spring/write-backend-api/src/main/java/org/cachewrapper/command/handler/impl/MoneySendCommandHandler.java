package org.cachewrapper.command.handler.impl;

import lombok.RequiredArgsConstructor;
import org.cachewrapper.command.domain.impl.MoneySendCommand;
import org.cachewrapper.command.handler.CommandHandler;
import org.cachewrapper.event.BaseEvent;
import org.cachewrapper.aggregate.mapper.service.AccountAggregateMapperService;
import org.cachewrapper.event.impl.MoneyReceiveEvent;
import org.cachewrapper.event.impl.MoneySendEvent;
import org.cachewrapper.event.payload.impl.MoneyReceivePayload;
import org.cachewrapper.event.payload.impl.MoneySendPayload;
import org.cachewrapper.repository.event.EventRepository;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

/**
 * Command handler responsible for processing {@link MoneySendCommand}.
 *
 * <p>This handler executes the full money transfer logic between accounts:
 * <ol>
 *     <li>Constructs the payloads for the sending and receiving accounts.</li>
 *     <li>Creates {@link MoneySendEvent} and {@link MoneyReceiveEvent} based on the command.</li>
 *     <li>Persists both events to the {@link EventRepository}.</li>
 *     <li>Publishes the events to Kafka topics ("money-send" and "money-receive").</li>
 *     <li>Applies the events to the corresponding aggregates via
 *         {@link AccountAggregateMapperService} and saves the updated state.</li>
 * </ol>
 *
 * <p>In short, this handler ensures atomic propagation of the money transfer
 * in both the event store and the aggregate state, and notifies external systems
 * via Kafka.
 */
@Component
@RequiredArgsConstructor
public class MoneySendCommandHandler implements CommandHandler<MoneySendCommand> {

    private final KafkaTemplate<String, BaseEvent<?>> kafkaTemplate;
    private final EventRepository eventRepository;
    private final AccountAggregateMapperService accountAggregateMapperService;

    /**
     * Handles the {@link MoneySendCommand} by generating the corresponding events,
     * persisting them, sending them to Kafka, and updating aggregates.
     *
     * @param command the {@link MoneySendCommand} containing sender, receiver, and transaction amount
     */
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
        accountAggregateMapperService.applyAndSave(senderAccountUUID, moneySendEvent);
        accountAggregateMapperService.applyAndSave(receiverAccountUUID, moneyReceiveEvent);
    }
}