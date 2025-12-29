package org.cachewrapper.event;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Supplier;

public record EventRebuilderMetadata<T>(
        Class<T> rebuildDomainType,
        Supplier<T> domainSupplier,
        Map<Class<? extends BaseEvent<?>>, BiConsumer<T, BaseEvent<?>>> rebuilderMap
) {

    public static <T> EventRebuilderMetadata.Builder<T> builder() {
        return new EventRebuilderMetadata.Builder<>();
    }

    public static class Builder<T> {

        private final Map<Class<? extends BaseEvent<?>>, BiConsumer<T, BaseEvent<?>>> rebuilderMap = new HashMap<>();

        private Class<T> rebuildDomainType;
        private Supplier<T> domainSupplier;

        public Builder<T> rebuildDomainType(Class<T> rebuildDomainType) {
            this.rebuildDomainType = rebuildDomainType;
            return this;
        }

        @SuppressWarnings("unchecked")
        public <E extends BaseEvent<?>> Builder<T> rebuilder(
                Class<? extends BaseEvent<?>> eventType,
                BiConsumer<T, E> rebuilder
        ) {
            rebuilderMap.put(eventType, (BiConsumer<T, BaseEvent<?>>) rebuilder);
            return this;
        }

        public Builder<T> domainSupplier(Supplier<T> domainSupplier) {
            this.domainSupplier = domainSupplier;
            return this;
        }

        public EventRebuilderMetadata<T> build() {
            return new EventRebuilderMetadata<T>(rebuildDomainType, domainSupplier, rebuilderMap);
        }
    }
}