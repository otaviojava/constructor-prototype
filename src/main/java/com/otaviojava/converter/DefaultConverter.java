package com.otaviojava.converter;

import java.util.Map;
import java.util.Objects;

public class DefaultConverter implements Converter{

    @Override
    public <T> T toEntity(Map<String, Object> map) {
        Objects.requireNonNull(map, "map is required");
        return null;
    }

    @Override
    public <T> Map<String, Object> toMap(T entity) {
        Objects.requireNonNull(entity, "entity is required");
        return null;
    }
}
