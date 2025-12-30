package org.cachewrapper.paper.rest.async;

import org.cachewrapper.paper.rest.RestClient;
import org.cachewrapper.paper.rest.request.RequestPayload;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;

public interface RestClientAsync<K, T> extends RestClient<K, T> {

    default CompletableFuture<T> loadAsync(@NotNull K key) {
        return CompletableFuture.supplyAsync(() -> load(key));
    }

    @SuppressWarnings("unchecked")
    default <R extends RequestPayload, C extends CompletableFuture<?>> C postAsync(
            @NotNull R requestPayload,
            @NotNull String endpoint
    ) {
        return (C) CompletableFuture.supplyAsync(() -> post(requestPayload, endpoint));
    }
}