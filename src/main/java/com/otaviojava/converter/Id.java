package com.otaviojava.converter;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 *This isn't a mandatory field,  but when it defines this field is a key.
 *  That going to be used mainly for key-value operations.
 *  @see Entity
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Id {

    String value() default "_id";
}