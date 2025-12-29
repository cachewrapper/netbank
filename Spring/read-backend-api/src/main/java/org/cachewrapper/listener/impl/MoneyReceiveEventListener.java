package org.cachewrapper.listener.impl;

import lombok.RequiredArgsConstructor;
import org.cachewrapper.event.impl.MoneyReceiveEvent;
import org.cachewrapper.event.impl.MoneySendEvent;
import org.cachewrapper.listener.Listener;
import org.cachewrapper.repository.AccountRepository;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class MoneyReceiveEventListener implements Listener<MoneyReceiveEvent> {

    private final AccountRepository accountRepository;

    @Override
    @Transactional
    @KafkaListener(topics = "money-receive", groupId = "account-events-group")
    public void listener(MoneyReceiveEvent event) {
        var payload = event.getPayload();

        var receiverAccountUUID = payload.receiverAccountUUID();
        var transactionAmount = payload.transactionAmount();

        accountRepository.add(receiverAccountUUID, transactionAmount);
    }
}