package org.cachewrapper.updater;

import org.cachewrapper.event.BaseEvent;

/**
 * Generic interface for updating views based on events.
 * <p>
 * Implementations of this interface apply a specific {@link BaseEvent}
 * to a view model, typically updating read-side representations
 * after events are processed.
 * </p>
 *
 * @param <E> the type of {@link BaseEvent} this updater handles
 */
public interface ViewUpdater<E extends BaseEvent<?>> {

    /**
     * Updates the view based on the provided event.
     *
     * @param event the event to process and apply to the view
     */
    void update(E event);
}