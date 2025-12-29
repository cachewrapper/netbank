package org.cachewrapper.event;

import lombok.Getter;

import java.util.UUID;

@Getter
public abstract class EventRebuilder<T> {

    private final EventRebuilderMetadata<T> metadata;

    public EventRebuilder() {
        this.metadata = metadata();
    }

    public abstract T rebuild(UUID aggregateUUID);

    public abstract T applyEvent(UUID aggregateUUID, BaseEvent<?> event);

    protected abstract EventRebuilderMetadata<T> metadata();
}