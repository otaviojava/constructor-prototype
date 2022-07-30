package com.otaviojava.converter;


@Entity
public class Person {

    @Id
    private String id;

    @Column
    private String name;

    @Column
    private String city;


}
