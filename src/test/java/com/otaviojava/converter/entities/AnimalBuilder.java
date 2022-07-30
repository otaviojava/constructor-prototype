package com.otaviojava.converter.entities;

public class AnimalBuilder {
    private String id;
    private String name;
    private String race;

    AnimalBuilder() {
    }

    public AnimalBuilder id(String id) {
        this.id = id;
        return this;
    }

    public AnimalBuilder name(String name) {
        this.name = name;
        return this;
    }

    public AnimalBuilder race(String race) {
        this.race = race;
        return this;
    }

    public Animal build() {
        return new Animal(id, name, race);
    }
}