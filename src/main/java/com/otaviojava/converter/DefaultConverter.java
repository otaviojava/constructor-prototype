package com.otaviojava.converter;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Predicate;

public class DefaultConverter implements Converter {

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
        Constructor<T> constructor = getConstructor(entity);
        map.put("Entity", entityName);
        for (Field field : entity.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            Id id = field.getAnnotation(Id.class);
            if (id != null) {
                feedId(entity, map, field);

            }
            Column column = field.getAnnotation(Column.class);
            if (column != null) {
                extractColumn(entity, map, field);
            }

        }

        return map;
    }

    private <T> void extractColumn(T entity, Map<String, Object> map, Field field) {
        Object value = getValue(entity, field);
        if (value != null) {
            String key = Optional
                    .ofNullable(field.getAnnotation(Column.class))
                    .map(Column::value)
                    .filter(Predicate.not(String::isBlank))
                    .orElse(field.getName());
            map.put(key, value);
        }
    }

    private <T> void feedId(T entity, Map<String, Object> map, Field field) {
        Object value = getValue(entity, field);
        if (value != null) {
            String key = Optional
                    .ofNullable(field.getAnnotation(Id.class))
                    .map(Id::value)
                    .filter(Predicate.not(String::isBlank))
                    .orElse(field.getName());
            map.put(key, value);
        }
    }

    private static <T> Object getValue(T entity, Field field) {
        try {
            return field.get(entity);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    private <T> Constructor<T> getConstructor(T entity) {
        for (Constructor<?> constructor : entity.getClass().getDeclaredConstructors()) {
            if (constructor.getParameterCount() == 0) {
                return (Constructor<T>) constructor;
            }
        }
        throw new IllegalStateException("There is no a legal constructor");
    }

    private <T> String getEntityName(T entity) {
        return Optional.ofNullable(entity.getClass().getAnnotation(Entity.class))
                .map(Entity::value)
                .filter(Predicate.not(String::isBlank))
                .orElse(entity.getClass().getSimpleName());
    }
}