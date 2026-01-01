package org.cachewrapper.repository.event;

import org.cachewrapper.event.BaseEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

/**
 * Repository interface for storing and retrieving events.
 * <p>
 * This repository provides CRUD operations for all types of {@link BaseEvent} entities.
 * It is used in the event sourcing mechanism to persist immutable events representing
 * state changes in aggregates.
 * </p>
 *
 * Extends {@link JpaRepository} to leverage Spring Data JPA features such as:
 * <ul>
 *     <li>Saving events</li>
 *     <li>Finding events by their UUID</li>
 *     <li>Deleting or counting events (if needed)</li>
 * </ul>
 *
 * The primary key of events is of type {@link UUID}.
 */
@Repository
public interface EventRepository extends JpaRepository<BaseEvent<?>, UUID> {}