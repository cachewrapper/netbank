package org.cachewrapper.repository;

import org.cachewrapper.event.BaseEvent;
import org.cachewrapper.event.impl.AccountCreatedEvent;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface EventRepository extends JpaRepository<BaseEvent<?>, UUID> {

    @Query("""
                SELECT event
                FROM BaseEvent event
                WHERE event.aggregateUUID = :aggregateUUID
                ORDER BY event.createdAt DESC
            """)
    Optional<BaseEvent<?>> findFirstByAggregateUUIDAndTypeOrderByCreatedAtDesc(
            UUID aggregateUUID,
            Class<? extends BaseEvent<?>> type
    );
}