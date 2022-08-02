package com.otaviojava.converter;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Predicate;

public class DefaultConverter implements Converter {

    private static final Predicate<Constructor<?>> DEFAULT_CONSTRUCTOR = c -> c.getParameterCount() == 0;
    private static final Predicate<Constructor<?>> ANNOTATION_CONSTRUCTOR = c ->
            c.getAnnotation(com.otaviojava.converter.Constructor.class) != null;
    private final Map<String, Class<?>> beans = new HashMap<>();


    public void add(Class<?> entity) {
        Objects.requireNonNull(entity, "entity is required");
        beans.put(getEntityName(entity), entity);
    }

    @Override
    public <T> T toEntity(Map<String, Object> map) {
        Objects.requireNonNull(map, "map is required");
        String entity = Optional.ofNullable(map.get("Entity"))
                .map(Object::toString)
                .orElseThrow(() -> new RuntimeException("The Entity key is mandatory"));

        Class<T> bean = Optional.ofNullable(beans.get(entity))
                .map(c -> (Class<T>) c)
                .orElseThrow(() -> new RuntimeException("It does not bean to the entity " + entity));
        Constructor<T> constructor = getConstructor(bean);
        EntityCreator entityCreator = EntityCreator.of(constructor);
        return entityCreator.toEntity(bean, constructor, map);
    }

    @Override
    public <T> Map<String, Object> toMap(T entity) {
        Objects.requireNonNull(entity, "entity is required");
        Map<String, Object> map = new HashMap<>();
        String entityName = getEntityName(entity.getClass());
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

    private <T> void setValue(T instance, Field field, Object value) {
        try {
            field.set(instance, value);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    private <T> T getInstance(Constructor<T> constructor) {
        try {
            constructor.setAccessible(true);
            return constructor.newInstance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    private <T> void extractColumn(T entity, Map<String, Object> map, Field field) {
        Object value = getValue(entity, field);
        if (value != null) {
            String key = getColumnName(field);
            map.put(key, value);
        }
    }

    private <T> void feedId(T entity, Map<String, Object> map, Field field) {
        Object value = getValue(entity, field);
        if (value != null) {
            String key = getIdValue(field);
            map.put(key, value);
        }
    }

    private static String getIdValue(Field field) {
        return Optional
                .ofNullable(field.getAnnotation(Id.class))
                .map(Id::value)
                .filter(Predicate.not(String::isBlank))
                .orElse("_id");
    }

    private static String getColumnName(Field field) {
        return Optional
                .ofNullable(field.getAnnotation(Column.class))
                .map(Column::value)
                .filter(Predicate.not(String::isBlank))
                .orElse(field.getName());
    }

    private static <T> Object getValue(T entity, Field field) {
        try {
            return field.get(entity);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    private <T> Constructor<T> getConstructor(Class<T> entity) {

        Constructor<?>[] constructors = entity.getDeclaredConstructors();
        if(constructors.length == 1) {
            if (constructors[0].getParameterCount() == 0) {
                return (Constructor<T>) constructors[0];
            }

        }

        return extractConstructorFromMultiple(constructors);
    }

    private <T> Constructor<T> extractConstructorFromMultiple(Constructor<?>[] constructors) {
        Constructor<T> defaultConstructor = null;
        for (Constructor<?> constructor : constructors) {

            if (DEFAULT_CONSTRUCTOR.test(constructor)) {
                defaultConstructor = (Constructor<T>) constructor;
            } else if (ANNOTATION_CONSTRUCTOR.test(constructor)) {
                return (Constructor<T>) constructor;
            }
        }
        return Optional.ofNullable(defaultConstructor)
                .orElseThrow(() -> new IllegalStateException("There is no a legal constructor"));
    }

    private <T> String getEntityName(Class<T> entity) {
        return Optional.ofNullable(entity.getAnnotation(Entity.class))
                .map(Entity::value)
                .filter(Predicate.not(String::isBlank))
                .orElse(entity.getSimpleName());
    }
}
