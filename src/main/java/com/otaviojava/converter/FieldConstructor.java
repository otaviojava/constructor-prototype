package com.otaviojava.converter;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import java.util.Optional;
import java.util.function.Predicate;

/**
 * This implementation will create the instance and then start fill it up
 */
public class FieldConstructor implements EntityConverter {

    @Override
    public <T> T toEntity(Class<T> entity, Constructor<T> constructor, Map<String, Object> map) {
        T instance = getInstance(constructor);

        for (Field field : entity.getDeclaredFields()) {
            Column column = field.getAnnotation(Column.class);
            Id id = field.getAnnotation(Id.class);

            if (id != null) {

            } else if (column != null) {
                field.setAccessible(true);
                String name = Optional.of(column).map(Column::value)
                        .filter(Predicate.not(String::isBlank))
                        .orElse(field.getName());
                Object value = map.get(name);
                if (value != null) {
                    try {
                        field.set(instance, value);
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException(e);
                    }
                }
            }

        }
        return null;
    }

    private static <T> T getInstance(Constructor<T> constructor) {
        try {
            return constructor.newInstance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException exception) {
            throw new RuntimeException(exception);
        }
    }
}
