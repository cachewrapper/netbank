package org.cachewrapper.event.impl;

import org.cachewrapper.event.CacheEventRebuilder;
import org.cachewrapper.event.EventRebuilderMetadata;
import org.cachewrapper.model.Account;
import org.cachewrapper.repository.EventRepository;
import org.jetbrains.annotations.NotNull;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.function.BiConsumer;

@Component
public class AccountCacheEventRebuilder extends CacheEventRebuilder<Account> {

    public AccountCacheEventRebuilder(EventRepository eventRepository) {
        super(eventRepository);
    }

    @Override
    protected EventRebuilderMetadata<Account> metadata() {
        return EventRebuilderMetadata.<Account>builder()
                .rebuildDomainType(Account.class)
                .domainSupplier(Account::new)
                .rebuilder(AccountCreatedEvent.class, accountCreatedEventConsumer())
                .rebuilder(MoneySendEvent.class, moneySendEventConsumer())
                .rebuilder(MoneyReceiveEvent.class, moneyReceiveEventConsumer())
                .build();
    }

    @NotNull
    private BiConsumer<Account, AccountCreatedEvent> accountCreatedEventConsumer() {
        return (Account account, AccountCreatedEvent accountCreatedEvent) -> {
            var balance = accountCreatedEvent.getPayload().balance();
            var userUUID = accountCreatedEvent.getPayload().userUUID();

            account.setAccountUUID(userUUID);
            account.setBalance(balance);
        };
    }

    @NotNull
    private BiConsumer<Account, MoneyReceiveEvent> moneyReceiveEventConsumer() {
        return (Account account, MoneyReceiveEvent moneyReceiveEvent) -> {
            var balance = account.getBalance();
            var transactionAmount = moneyReceiveEvent.getPayload().transactionAmount();

            account.setBalance(balance.add(transactionAmount));
        };
    }

    @NotNull
    private BiConsumer<Account, MoneySendEvent> moneySendEventConsumer() {
        return (Account account, MoneySendEvent moneySendEvent) -> {
            var balance = account.getBalance();
            var transactionAmount = moneySendEvent.getPayload().transactionAmount();

            account.setBalance(balance.subtract(transactionAmount));
        };
    }
}
