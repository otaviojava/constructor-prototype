package com.otaviojava.converter;

import com.otaviojava.converter.entities.Animal;
import com.otaviojava.converter.entities.Person;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

class DefaultConverterTest {

    private Converter converter;

    @BeforeEach
    public void setUp() {
        this.converter = new DefaultConverter();
        this.converter.add(Animal.class);
        this.converter.add(Person.class);
    }

    @Test
    public void shouldReturnErrorWhenAddNull() {
        Assertions.assertThrows(NullPointerException.class, () ->
                this.converter.add(null));
    }

    @Test
    public void shouldAddBean() {
        this.converter.add(Animal.class);
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

    @Test
    public void shouldReturnNPEWhenConvertMap() {
        Assertions.assertThrows(NullPointerException.class, () ->
                converter.toEntity(null));
    }

    @Test
    public void shouldConvertToMapFields() {
        Map<String, Object> map = new HashMap<>();
        map.put("Entity", Person.class.getSimpleName());
        map.put("_id", "12");
        map.put("name", "Otavio");
        map.put("city", "Salvador");
        Person otavio = converter.toEntity(map);
        Assertions.assertNotNull(otavio);
        Assertions.assertEquals("12", otavio.getId());
        Assertions.assertEquals("Otavio", otavio.getName());
        Assertions.assertEquals("Salvador", otavio.getCity());
    }

    @Test
    public void shouldConvertTMapConstructor() {
        Map<String, Object> map = new HashMap<>();
        map.put("Entity", Person.class.getSimpleName());
        map.put("_id", "12");
        map.put("name", "Ada");
        map.put("race", "Dog");
        Animal animal = converter.toEntity(map);
        Assertions.assertNotNull(animal);
        Assertions.assertEquals("12", animal.getId());
        Assertions.assertEquals("Ada", animal.getName());
        Assertions.assertEquals("Dog", animal.getRace());
    }

}