package com.otaviojava.converter;

import com.otaviojava.converter.entities.Person;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class DefaultConverterTest {

    private Converter converter;

    @BeforeEach
    public void setUp() {
        this.converter = new DefaultConverter();
    }

    @Test
    public void shouldReturnErrorOnMap() {
        Person otavio = Person.builder().id("10").name("Otavio").city("Salvador").build();
        Map<String, Object> map = this.converter.toMap(otavio);
        Assertions.assertNotNull(map);
        Assertions.assertEquals("10", map.get("id"));
        Assertions.assertEquals("Otavio", map.get("name"));
        Assertions.assertEquals("Salvador", map.get("city"));
        Assertions.assertEquals(Person.class.getSimpleName(), map.get("Entity"));
    }

}