package org.cachewrapper.updater.impl;

import lombok.RequiredArgsConstructor;
import org.cachewrapper.event.impl.MoneySendEvent;
import org.cachewrapper.repository.AccountViewRepository;
import org.cachewrapper.updater.ViewUpdater;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * Updates account views when a {@link MoneySendEvent} occurs.
 * <p>
 * This component listens to the "money-send" Kafka topic and
 * adjusts the sender's account balance in the read-side view
 * by subtracting the transaction amount.
 * </p>
 */
@Component
@RequiredArgsConstructor
public class MoneySendViewUpdater implements ViewUpdater<MoneySendEvent> {

    private final AccountViewRepository accountRepository;

    /**
     * Processes a {@link MoneySendEvent} and updates the sender's account view.
     * <p>
     * This method is transactional and listens to the "money-send" Kafka topic.
     * </p>
     *
     * @param event the MoneySendEvent to apply
     */
    @Override
    @Transactional
    @KafkaListener(topics = "money-send", groupId = "account-events-group")
    public void update(MoneySendEvent event) {
        var payload = event.getPayload();
        var senderAccountUUID = payload.senderAccountUUID();
        var transactionAmount = payload.transactionAmount();

        accountRepository.subtractBalance(senderAccountUUID, transactionAmount);
    }
}