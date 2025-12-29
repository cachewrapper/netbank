package org.cachewrapper.listener;

import org.cachewrapper.event.BaseEvent;

public interface Listener<E extends BaseEvent<?>> {
    void listener(E event);
}