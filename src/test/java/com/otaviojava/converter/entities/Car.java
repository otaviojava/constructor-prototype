package com.otaviojava.converter.entities;

public class Car {

    private String name;
    private int year;

    public Car(String name, int year) {
        this.name = name;
        this.year = year;
    }

    public Car(int year) {
        this.year = year;
    }

    public Car(String name) {
        this.name = name;
    }
}
