package com.otaviojava.converter;

import com.otaviojava.converter.entities.Animal;
import com.otaviojava.converter.entities.Person;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;

class DefaultConverterTest {

    private Converter converter;

    @BeforeEach
    public void setUp() {
        this.converter = new DefaultConverter();
    }

    @Test
    public void shouldReturnNPEMap() {
        Assertions.assertThrows(NullPointerException.class, ()->
                converter.toMap(null));
    }

    @Test
    public void shouldReturnEntityUsingField() {
        Person otavio = Person.builder().id("10").name("Otavio").city("Salvador").build();
        Map<String, Object> map = this.converter.toMap(otavio);
        Assertions.assertNotNull(map);
        Assertions.assertEquals("10", map.get("_id"));
        Assertions.assertEquals("Otavio", map.get("name"));
        Assertions.assertEquals("Salvador", map.get("city"));
        Assertions.assertEquals(Person.class.getSimpleName(), map.get("Entity"));
    }

    @Test
    public void shouldReturnEntityUsingConstructor() {
        Animal lion = Animal.builder().id("lion").race("mammal").name("Simba").build();
        Map<String, Object> map = this.converter.toMap(lion);
        Assertions.assertNotNull(map);
        Assertions.assertEquals("lion", map.get("_id"));
        Assertions.assertEquals("mammal", map.get("race"));
        Assertions.assertEquals("Simba", map.get("name"));
        Assertions.assertEquals(Animal.class.getSimpleName(), map.get("Entity"));
    }

}