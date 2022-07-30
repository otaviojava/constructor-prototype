package com.otaviojava.converter.entities;

public class PersonBuilder {
    private String id;
    private String name;
    private String city;

    PersonBuilder() {
    }

    public PersonBuilder id(String id) {
        this.id = id;
        return this;
    }

    public PersonBuilder name(String name) {
        this.name = name;
        return this;
    }

    public PersonBuilder city(String city) {
        this.city = city;
        return this;
    }

    public Person build() {
        return new Person(id, name, city);
    }
}