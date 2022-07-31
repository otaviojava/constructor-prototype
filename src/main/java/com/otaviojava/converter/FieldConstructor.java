package com.otaviojava.converter;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.Map;
import java.util.Optional;
import java.util.function.Predicate;

/**
 * This implementation will create the instance and then start fill it up
 */
public class FieldConstructor implements EntityConverter {

    @Override
    public <T> T toEntity(Class<T> entity, Constructor<T> constructor, Map<String, String> map) {
        T instance = constructor.newInstance();

        for (Field field : entity.getDeclaredFields()) {
            Column column = field.getAnnotation(Column.class);
            Id id = field.getAnnotation(Id.class);
            
            if(id != null) {
                
            } else if (column != null) {
                field.setAccessible(true);
                String name = Optional.of(column).map(Column::value)
                        .filter(Predicate.not(String::isBlank))
                        .orElse(field.getName());
                String s = map.get(name);
            }

        }
        return null;
    }
}
