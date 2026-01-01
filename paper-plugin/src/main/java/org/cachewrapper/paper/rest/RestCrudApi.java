package org.cachewrapper.paper.rest;

import com.google.gson.Gson;
import org.apache.hc.client5.http.classic.HttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.cachewrapper.paper.rest.request.RequestPayload;
import org.jetbrains.annotations.NotNull;

public interface RestCrudApi<K, T> {

    String WRITE_HTTP_URL = "http://localhost:8081%s%s";
    String READ_HTTP_URL = "http://localhost:8082%s%s";

    HttpClient HTTP_CLIENT = HttpClients.createMinimal();

    Gson GSON = new Gson();

    T load(@NotNull K key);

    <R extends RequestPayload> T post(@NotNull R requestPayload, @NotNull String endpoint);

    @NotNull
    String getRepository();

    @NotNull
    Class<T> getEntityClass();
}