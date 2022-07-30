package com.otaviojava.converter.entities;


import com.otaviojava.converter.Column;
import com.otaviojava.converter.Constructor;
import com.otaviojava.converter.Entity;
import com.otaviojava.converter.Id;

import java.util.Objects;

@Entity
public class Animal {

    private final String id;

    private final  String name;

    private final  String race;


    @Constructor
    public Animal(@Id String id, @Column String name, @Column String race) {
        this.id = id;
        this.name = name;
        this.race = race;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getRace() {
        return race;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Animal animal = (Animal) o;
        return Objects.equals(id, animal.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Animal{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", race='" + race + '\'' +
                '}';
    }
}
