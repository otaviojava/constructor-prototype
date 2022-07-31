package com.otaviojava.converter;

import java.util.Map;

public interface Converter {

    void add(Class<?> entity);

    <T> T toEntity(Map<String, Object> map);

    <T> Map<String, Object> toMap(T entity);
}
