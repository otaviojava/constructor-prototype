package com.otaviojava.converter.entities;


import com.otaviojava.converter.Column;
import com.otaviojava.converter.Entity;
import com.otaviojava.converter.Id;

@Entity
public class Animal {

    private final String id;

    private final  String name;

    private final  String race;


    public Animal(@Id String id, @Column String name, @Column String race) {
        this.id = id;
        this.name = name;
        this.race = race;
    }
}
