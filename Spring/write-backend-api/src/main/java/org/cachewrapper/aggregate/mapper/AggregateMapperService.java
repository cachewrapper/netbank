package org.cachewrapper.aggregate.mapper;

import lombok.Getter;
import org.cachewrapper.aggregate.Aggregate;
import org.cachewrapper.event.BaseEvent;
import org.cachewrapper.repository.aggregate.AggregateRepository;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

/**
 * Base service for mapping events to aggregates and persisting the updated state.
 * <p>
 * This class handles:
 * <ul>
 *     <li>Retrieving the aggregate from the repository using its UUID.</li>
 *     <li>Applying a domain event to the aggregate using a pre-defined mapping.</li>
 *     <li>Saving the updated aggregate back to the repository.</li>
 * </ul>
 * <p>
 * Subclasses must provide {@link AggregateMapperMetadata} through {@link #metadata()}
 * to define:
 * <ul>
 *     <li>How to instantiate a new aggregate if one does not exist.</li>
 *     <li>Which event types map to which "apply" methods on the aggregate.</li>
 * </ul>
 *
 * @param <T> Type of aggregate handled by this service
 * @param <R> Type of repository used for persisting the aggregate
 */
@Getter
public abstract class AggregateMapperService<T extends Aggregate, R extends AggregateRepository<T>> {

    /**
     * Repository for loading and saving aggregates.
     */
    private final R aggregateRepository;

    /**
     * Metadata that defines aggregate instantiation and event-to-method mapping.
     */
    private final AggregateMapperMetadata<T> metadata;

    /**
     * Constructs the service and initializes metadata.
     *
     * @param aggregateRepository repository for the aggregate type
     */
    public AggregateMapperService(@NotNull R aggregateRepository) {
        this.aggregateRepository = aggregateRepository;
        this.metadata = metadata();
    }

    /**
     * Applies the given event to the aggregate identified by {@code aggregateUUID}
     * and persists the updated state.
     * <p>
     * If the aggregate does not exist in the repository, a new instance will be created
     * using the supplier defined in {@link AggregateMapperMetadata}.
     *
     * @param aggregateUUID UUID of the aggregate to apply the event to
     * @param event         event to be applied to the aggregate
     */
    public void applyAndSave(UUID aggregateUUID, BaseEvent<?> event) {
        var aggregate = aggregateRepository.findById(aggregateUUID).orElse(null);
        if (aggregate == null) {
            aggregate = metadata.aggregateSupplier().get();
        }

        var eventType = event.getClass();
        var eventMapper = getMetadata().eventApplierMap().get(eventType);

        eventMapper.accept(aggregate, event);
        aggregateRepository.save(aggregate);
    }

    /**
     * Subclasses must implement this to provide metadata about the aggregate and event mappings.
     *
     * @return metadata describing aggregate instantiation and event handling
     */
    protected abstract AggregateMapperMetadata<T> metadata();
}