package org.cachewrapper.aggregate.mapper.service;

import org.cachewrapper.aggregate.impl.AccountAggregate;
import org.cachewrapper.aggregate.mapper.AggregateMapperMetadata;
import org.cachewrapper.aggregate.mapper.AggregateMapperService;
import org.cachewrapper.event.impl.AccountCreatedEvent;
import org.cachewrapper.event.impl.MoneyReceiveEvent;
import org.cachewrapper.event.impl.MoneySendEvent;
import org.cachewrapper.repository.aggregate.impl.AccountAggregateRepository;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

import java.util.function.BiConsumer;

/**
 * Service responsible for mapping domain events to {@link AccountAggregate} updates.
 * <p>
 * This class defines how specific events (AccountCreatedEvent, MoneySendEvent,
 * MoneyReceiveEvent) are applied to the account aggregate and ensures that the
 * aggregate is properly updated and persisted.
 *
 * <p>
 * It uses {@link AggregateMapperService} as a base to provide the following functionality:
 * <ul>
 *     <li>Create a new aggregate instance if none exists for a given UUID.</li>
 *     <li>Apply an incoming event to the aggregate using the configured event-to-applier mapping.</li>
 *     <li>Persist the updated aggregate back to the repository.</li>
 * </ul>
 */
@Component
public class AccountAggregateMapperService extends AggregateMapperService<AccountAggregate, AccountAggregateRepository> {

    public AccountAggregateMapperService(@NotNull AccountAggregateRepository aggregateRepository) {
        super(aggregateRepository);
    }

    /**
     * Defines the metadata for mapping events to aggregate operations.
     * <p>
     * Specifies the supplier for creating new {@link AccountAggregate} instances
     * and the mapping between event types and their corresponding "apply" methods.
     *
     * @return configured {@link AggregateMapperMetadata} for AccountAggregate
     */
    @Override
    protected AggregateMapperMetadata<AccountAggregate> metadata() {
        return AggregateMapperMetadata.<AccountAggregate>builder()
                .aggregateSupplier(AccountAggregate::new)
                .applier(AccountCreatedEvent.class, accountCreatedEventConsumer())
                .applier(MoneySendEvent.class, moneySendEventConsumer())
                .applier(MoneyReceiveEvent.class, moneyReceiveEventConsumer())
                .build();
    }

    @NotNull
    private BiConsumer<AccountAggregate, AccountCreatedEvent> accountCreatedEventConsumer() {
        return AccountAggregate::applyAccountCreatedEvent;
    }

    @NotNull
    private BiConsumer<AccountAggregate, MoneyReceiveEvent> moneyReceiveEventConsumer() {
        return AccountAggregate::applyMoneyReceiveEvent;
    }

    @NotNull
    private BiConsumer<AccountAggregate, MoneySendEvent> moneySendEventConsumer() {
        return AccountAggregate::applyMoneySendEvent;
    }
}
