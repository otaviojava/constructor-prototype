package com.otaviojava.converter;

import java.lang.reflect.Constructor;
import java.util.Map;

public interface EntityConverter {

    <T> T toEntity(Class<T> entity, Constructor<T> constructor, Map<String, Object> map);


    static <T>  EntityConverter of(Constructor<T> constructor) {
        if(constructor.getParameterCount() == 0) {
            return new FieldConstructor();
        }
        return null;
    }

}
