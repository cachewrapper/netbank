package org.cachewrapper.paper.rest;

import lombok.SneakyThrows;
import org.apache.hc.core5.http.ContentType;
import org.apache.hc.core5.http.io.entity.StringEntity;
import org.apache.hc.core5.http.io.support.ClassicRequestBuilder;
import org.cachewrapper.paper.rest.request.RequestPayload;
import org.jetbrains.annotations.NotNull;

import java.io.InputStreamReader;
import java.util.function.Consumer;

public interface RestClient<K, T> extends RestCrudApi<K, T> {

    @SneakyThrows
    @SuppressWarnings("unchecked")
    @Override
    default T load(@NotNull K key) {
        var requestHttp = HTTP_URL.formatted(getRepository()) + "/" + key;
        var httpRequest = ClassicRequestBuilder
                .get(requestHttp)
                .build();

        return (T) HTTP_CLIENT.execute(httpRequest, httpResponse -> {
            try (
                    var content = httpResponse.getEntity().getContent();
                    var reader = new InputStreamReader(content)
            ) {
                return GSON.fromJson(reader, getEntityClass());
            }
        });
    }

    @SneakyThrows
    @SuppressWarnings("unchecked")
    @Override
    default T update(@NotNull K key, @NotNull Consumer<T> updateDataConsumer) {
        var loadedData = load(key);
        if (loadedData == null) {
            return null;
        }

        var requestHttp = HTTP_URL.formatted(getRepository()) + "/" + key;
        updateDataConsumer.accept(loadedData);
        var json = GSON.toJson(loadedData);

        var httpRequest = ClassicRequestBuilder
                .post(requestHttp)
                .setEntity(new StringEntity(json, ContentType.APPLICATION_JSON))
                .build();

        return (T) HTTP_CLIENT.execute(httpRequest, httpResponse -> {
            try (
                    var content = httpResponse.getEntity().getContent();
                    var reader = new InputStreamReader(content)
            ) {
                return GSON.fromJson(reader, getEntityClass());
            }
        });
    }

    @SneakyThrows
    @SuppressWarnings("unchecked")
    @Override
    default T save(@NotNull K key, T data) {
        var requestHttp = HTTP_URL.formatted(getRepository()) + "/" + key;
        var httpRequest = ClassicRequestBuilder
                .post(requestHttp)
                .setEntity(new StringEntity(GSON.toJson(data), ContentType.APPLICATION_JSON))
                .build();

        return (T) HTTP_CLIENT.execute(httpRequest, httpResponse -> {
            try (
                    var content = httpResponse.getEntity().getContent();
                    var reader = new InputStreamReader(content)
            ) {
                return GSON.fromJson(reader, getEntityClass());
            }
        });
    }

    @SneakyThrows
    @Override
    default void delete(@NotNull K key) {
        var requestHttp = HTTP_URL.formatted(getRepository()) + "/" + key;
        var httpRequest = ClassicRequestBuilder
                .delete(requestHttp)
                .build();

        HTTP_CLIENT.execute(httpRequest, response -> {
            int status = response.getCode();
            if (status < 200 || status >= 300) {
                throw new RuntimeException("Delete failed: " + status);
            }

            return null;
        });
    }
}