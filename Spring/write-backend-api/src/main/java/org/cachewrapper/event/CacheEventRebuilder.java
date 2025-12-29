package org.cachewrapper.event;

import com.github.benmanes.caffeine.cache.*;
import lombok.RequiredArgsConstructor;
import org.cachewrapper.repository.EventRepository;

import java.time.Duration;
import java.util.UUID;

@RequiredArgsConstructor
public abstract class CacheEventRebuilder<T> extends EventRebuilder<T> {

    private final Cache<UUID, T> cachedDataMap = Caffeine.newBuilder()
            .expireAfterAccess(Duration.ofMinutes(60))
            .build();

    private final EventRepository eventRepository;

    @Override
    public T rebuild(UUID aggregateUUID) {
        if (cachedDataMap.asMap().containsKey(aggregateUUID)) {
            return cachedDataMap.getIfPresent(aggregateUUID);
        }

        var domain = getMetadata().domainSupplier().get();
        var events = eventRepository.findAllByAggregateUUIDOrderByCreatedAtAsc(aggregateUUID);

        for (var event : events) {
            System.out.println(event.toString());
            getMetadata().rebuilderMap().get(event.getClass()).accept(domain, event);
        }

        cachedDataMap.put(aggregateUUID, domain);
        System.out.println(domain.toString());
        return domain;
    }

    @Override
    public T applyEvent(UUID aggregateUUID, BaseEvent<?> event) {
        T aggregate = cachedDataMap.getIfPresent(aggregateUUID);
        if (aggregate == null) {
            rebuild(aggregateUUID);
            return applyEvent(aggregateUUID, event);
        }

        var rebuilder = getMetadata().rebuilderMap().get(event.getClass());
        rebuilder.accept(aggregate, event);

        cachedDataMap.put(aggregateUUID, aggregate);
        return aggregate;
    }
}