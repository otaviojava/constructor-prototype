package com.otaviojava.converter;

import java.util.Map;

public interface Converter {

    <T> T toEntity(Map<String, Object> map);

    <T> Map<String, Object> toMap(T entity);
}
