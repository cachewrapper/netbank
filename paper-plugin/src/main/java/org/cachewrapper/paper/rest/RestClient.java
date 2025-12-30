package org.cachewrapper.paper.rest;

import lombok.SneakyThrows;
import org.apache.hc.core5.http.ContentType;
import org.apache.hc.core5.http.io.entity.StringEntity;
import org.apache.hc.core5.http.io.support.ClassicRequestBuilder;
import org.cachewrapper.paper.rest.request.RequestPayload;
import org.jetbrains.annotations.NotNull;

import java.io.InputStreamReader;

public interface RestClient<K, T> extends RestCrudApi<K, T> {

    @SneakyThrows
    @Override
    default T load(@NotNull K key) {
        var requestHttp = READ_HTTP_URL.formatted(getRepository(), "/" + key);
        var httpRequest = ClassicRequestBuilder
                .get(requestHttp)
                .build();

        return HTTP_CLIENT.execute(httpRequest, httpResponse -> {
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
    default <R extends RequestPayload> T post(@NotNull R requestPayload, @NotNull String endpoint) {
        var requestHttp = WRITE_HTTP_URL.formatted(getRepository(), endpoint);
        var httpRequest = ClassicRequestBuilder
                .post(requestHttp)
                .setEntity(new StringEntity(GSON.toJson(requestPayload), ContentType.APPLICATION_JSON))
                .build();

        return HTTP_CLIENT.execute(httpRequest, _ -> null);
    }
}