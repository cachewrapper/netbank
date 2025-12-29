package org.cachewrapper.paper.instantiator;

import com.google.common.base.Preconditions;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public interface Instantiator<T> {

    Map<Class<?>, Object> FIELDS = new HashMap<>();

    default void init() {
        var instantiatedData = initialize();
        Preconditions.checkNotNull(instantiatedData);

        FIELDS.put(getClass(), instantiatedData);
    }

    @NotNull
    @SuppressWarnings("unchecked")
    default T get() {
        T instantiatedField = (T) FIELDS.get(getClass());
        Preconditions.checkNotNull(instantiatedField);

        return instantiatedField;
    }

    @NotNull
    T initialize();
}