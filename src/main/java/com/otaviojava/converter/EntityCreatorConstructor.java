package com.otaviojava.converter;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Predicate;

/**
 * This implementation will create the instance using the constructor
 */
public class EntityCreatorConstructor implements EntityCreator {

    @Override
    public <T> T toEntity(Class<T> entity, Constructor<T> constructor, Map<String, Object> map) {
        List<Object> parameterValues = new ArrayList<>(constructor.getParameterCount());
        for (Parameter parameter : constructor.getParameters()) {
            Id id = parameter.getAnnotation(Id.class);
            Column column = parameter.getAnnotation(Column.class);
            if (id != null) {
                String name = Optional.of(id).map(Id::value)
                        .filter(Predicate.not(String::isBlank))
                        .orElse(parameter.getName());
                Object value = map.get(name);
                parameterValues.add(value);
            } else if (column != null) {
                String name = Optional.of(column).map(Column::value)
                        .filter(Predicate.not(String::isBlank))
                        .orElse(parameter.getName());
                Object value = map.get(name);
                parameterValues.add(value);
            } else {
                parameterValues.add(null);
            }

        }
        constructor.setAccessible(true);
        if(constructor.getParameterCount() != parameterValues.size()) {
            throw new IllegalStateException(String.format("The number of parameters %d are different than arguments %d",
                    constructor.getParameterCount(), parameterValues.size()));
        }

        try {
            return constructor.newInstance(parameterValues.toArray());
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException exception) {
            throw new RuntimeException(exception);
        }
    }

}
