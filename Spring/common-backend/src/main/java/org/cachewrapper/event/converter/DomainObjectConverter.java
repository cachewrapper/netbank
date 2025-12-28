package org.cachewrapper.event.converter;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import lombok.SneakyThrows;
import org.jetbrains.annotations.NotNull;

@Converter
public class DomainObjectConverter<T> implements AttributeConverter<T, String> {

    private final Gson gson = new Gson();

    @SneakyThrows
    @Override
    public String convertToDatabaseColumn(@NotNull T attribute) {
        var jsonWrapper = new JsonObject();
        jsonWrapper.addProperty("type", attribute.getClass().getName());
        jsonWrapper.add("data", gson.toJsonTree(attribute));

        return gson.toJson(jsonWrapper);
    }

    @SneakyThrows
    @Override
    @SuppressWarnings("unchecked")
    public T convertToEntityAttribute(@NotNull String dbData) {
        var jsonWrapper = gson.fromJson(dbData, JsonObject.class);
        var className = jsonWrapper.get("type").getAsString();

        var domainClass = Class.forName(className);
        var data = jsonWrapper.get("data");

        return (T) gson.fromJson(data, domainClass);
    }
}
