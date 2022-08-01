package com.otaviojava.converter;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import java.util.Optional;
import java.util.function.Predicate;

/**
 * This implementation will create the instance using the constructor
 */
public class EntityCreatorConstructor implements EntityCreator {

    @Override
    public <T> T toEntity(Class<T> entity, Constructor<T> constructor, Map<String, Object> map) {

        return null;
    }

}
