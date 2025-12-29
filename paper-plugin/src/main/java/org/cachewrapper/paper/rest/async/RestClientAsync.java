package org.cachewrapper.paper.rest.async;

import org.cachewrapper.paper.rest.RestClient;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

public interface RestClientAsync<K, T> extends RestClient<K, T> {

    default CompletableFuture<T> loadAsync(@NotNull K key) {
        return CompletableFuture.supplyAsync(() -> load(key));
    }

    default CompletableFuture<T> updateAsync(@NotNull K key, @NotNull Consumer<T> updateDataConsumer) {
        return CompletableFuture.supplyAsync(() -> update(key, updateDataConsumer));
    }

    default CompletableFuture<T> saveAsync(@NotNull K key, @NotNull T data) {
        return CompletableFuture.supplyAsync(() -> save(key, data));
    }

    default CompletableFuture<Void> deleteAsync(@NotNull K key) {
        return CompletableFuture.runAsync(() -> delete(key));
    }
}