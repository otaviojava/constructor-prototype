package com.otaviojava.converter;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

public class DefaultConverter implements Converter{

    @Override
    public <T> T toEntity(Map<String, Object> map) {
        Objects.requireNonNull(map, "map is required");
        return null;
    }

    @Override
    public <T> Map<String, Object> toMap(T entity) {
        Objects.requireNonNull(entity, "entity is required");
        Map<String, Object> map = new HashMap<>();
        String entityName = getEntityName(entity);
        map.put("Entity", entityName);
        Constructor<T> constructor = getConstructor(entity);
        if (constructor != null) return constructor;

        return null;
    }

    private <T> Constructor<T> getConstructor(T entity) {
        for (Constructor<?> constructor : entity.getClass().getDeclaredConstructors()) {
            if(constructor.getParameterCount() == 0) {
                return (Constructor<T>) constructor;
            }
        }
        throw new IllegalStateException("There is no a legal constructor");
    }

    private static <T> String getEntityName(T entity) {
        return Optional.ofNullable(entity.getClass().getAnnotation(Entity.class))
                .map(Entity::value)
                .orElse(entity.getClass().getName());
    }
}
