package com.otaviojava.converter;

import java.lang.reflect.Constructor;
import java.util.Map;

public interface EntityConverter {

    <T> T toEntity(Class<T> entity, Constructor<T> constructor, Map<String, String> map);
}
