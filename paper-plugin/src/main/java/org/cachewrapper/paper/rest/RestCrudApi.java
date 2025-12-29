package org.cachewrapper.paper.rest;

import com.google.gson.Gson;
import org.apache.hc.client5.http.classic.HttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

// TODO: REWRITE SYSTEM TO EVENT-DRIVEN
public interface RestCrudApi<K, T> {

    String HTTP_URL = "http://localhost:8080%s";
    HttpClient HTTP_CLIENT = HttpClients.createMinimal();

    Gson GSON = new Gson();

    T load(@NotNull K key);

    T update(@NotNull K key, @NotNull Consumer<T> updateDataConsumer);

    T save(@NotNull K key, T data);

    void delete(@NotNull K key);

    @NotNull
    String getRepository();

    @NotNull
    Class<?> getEntityClass();
}