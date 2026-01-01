package org.cachewrapper.repository.aggregate.impl;

import org.cachewrapper.aggregate.impl.AccountAggregate;
import org.cachewrapper.repository.aggregate.AggregateRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for {@link AccountAggregate} entities.
 * <p>
 * This repository extends the generic {@link AggregateRepository} and provides
 * CRUD operations specifically for account aggregates.
 * </p>
 *
 * <p>
 * By extending {@link AggregateRepository}, it inherits all standard
 * JpaRepository methods such as:
 * <ul>
 *     <li>findById</li>
 *     <li>save</li>
 *     <li>delete</li>
 *     <li>existsById</li>
 * </ul>
 * </p>
 */
@Repository
public interface AccountAggregateRepository extends AggregateRepository<AccountAggregate> {}