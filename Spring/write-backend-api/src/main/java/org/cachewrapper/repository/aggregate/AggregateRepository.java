package org.cachewrapper.repository.aggregate;

import org.cachewrapper.aggregate.Aggregate;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

/**
 * Generic repository interface for storing and retrieving aggregate roots.
 * <p>
 * This interface is used as a base for all aggregate repositories, providing
 * CRUD operations on entities that extend {@link Aggregate}.
 * </p>
 *
 * <p>
 * By extending {@link JpaRepository}, it inherits methods for:
 * <ul>
 *     <li>Finding aggregates by UUID</li>
 *     <li>Saving or updating aggregates</li>
 *     <li>Deleting aggregates</li>
 *     <li>Counting or checking existence</li>
 * </ul>
 * </p>
 *
 * @param <T> the type of aggregate this repository manages
 */
public interface AggregateRepository<T extends Aggregate> extends JpaRepository<T, UUID> {}