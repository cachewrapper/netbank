package org.cachewrapper.listener.impl;

import lombok.RequiredArgsConstructor;
import org.cachewrapper.event.impl.MoneySendEvent;
import org.cachewrapper.listener.Listener;
import org.cachewrapper.repository.AccountRepository;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class MoneySendEventListener implements Listener<MoneySendEvent> {

    private final AccountRepository accountRepository;

    @Override
    @Transactional
    @KafkaListener(topics = "money-send", groupId = "account-events-group")
    public void listener(MoneySendEvent event) {
        var payload = event.getPayload();

        var senderAccountUUID = payload.senderAccountUUID();
        var transactionAmount = payload.transactionAmount();

        accountRepository.subtract(senderAccountUUID, transactionAmount);
    }
}