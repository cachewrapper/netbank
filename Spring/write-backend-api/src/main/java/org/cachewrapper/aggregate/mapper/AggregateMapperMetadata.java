package org.cachewrapper.aggregate.mapper;

import org.cachewrapper.event.BaseEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Supplier;

/**
 * Metadata for mapping domain events to aggregate operations.
 * <p>
 * This class encapsulates:
 * <ul>
 *     <li>A supplier for creating new aggregate instances when they don't exist.</li>
 *     <li>A map of event types to their corresponding "apply" functions for the aggregate.</li>
 * </ul>
 *
 * @param <T> Type of aggregate
 */
public record AggregateMapperMetadata<T>(
        Supplier<T> aggregateSupplier,
        Map<Class<? extends BaseEvent<?>>, BiConsumer<T, BaseEvent<?>>> eventApplierMap
) {

    public static <T> AggregateMapperMetadata.Builder<T> builder() {
        return new AggregateMapperMetadata.Builder<>();
    }

    public static class Builder<T> {

        private final Map<Class<? extends BaseEvent<?>>, BiConsumer<T, BaseEvent<?>>> applierMap = new HashMap<>();
        private Supplier<T> aggregateSupplier;

        /**
         * Registers an event type with a corresponding "apply" function.
         *
         * @param eventType Type of event
         * @param rebuilder Function that applies the event to the aggregate
         * @param <E>       Type of the event
         * @return the builder instance
         */
        @SuppressWarnings("unchecked")
        public <E extends BaseEvent<?>> Builder<T> applier(
                Class<? extends BaseEvent<?>> eventType,
                BiConsumer<T, E> rebuilder
        ) {
            applierMap.put(eventType, (BiConsumer<T, BaseEvent<?>>) rebuilder);
            return this;
        }

        /**
         * Sets the supplier used to create new aggregates.
         *
         * @param domainSupplier Supplier that creates a new aggregate instance
         * @return the builder instance
         */
        public Builder<T> aggregateSupplier(Supplier<T> domainSupplier) {
            this.aggregateSupplier = domainSupplier;
            return this;
        }

        public AggregateMapperMetadata<T> build() {
            return new AggregateMapperMetadata<>(aggregateSupplier, applierMap);
        }
    }
}