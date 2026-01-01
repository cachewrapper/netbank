package org.cachewrapper.updater.impl;

import lombok.RequiredArgsConstructor;
import org.cachewrapper.event.impl.MoneyReceiveEvent;
import org.cachewrapper.updater.ViewUpdater;
import org.cachewrapper.repository.AccountViewRepository;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * Updates account views when a {@link MoneyReceiveEvent} occurs.
 * <p>
 * This component listens to the "money-receive" Kafka topic and
 * adjusts the receiver's account balance in the read-side view
 * by adding the transaction amount.
 * </p>
 */
@Component
@RequiredArgsConstructor
public class MoneyReceiveViewUpdater implements ViewUpdater<MoneyReceiveEvent> {

    private final AccountViewRepository accountRepository;

    /**
     * Processes a {@link MoneyReceiveEvent} and updates the receiver's account view.
     * <p>
     * This method is transactional and listens to the "money-receive" Kafka topic.
     * </p>
     *
     * @param event the MoneyReceiveEvent to apply
     */
    @Override
    @Transactional
    @KafkaListener(topics = "money-receive", groupId = "account-events-group")
    public void update(MoneyReceiveEvent event) {
        var payload = event.getPayload();
        var receiverAccountUUID = payload.receiverAccountUUID();
        var transactionAmount = payload.transactionAmount();

        accountRepository.addBalance(receiverAccountUUID, transactionAmount);
    }
}