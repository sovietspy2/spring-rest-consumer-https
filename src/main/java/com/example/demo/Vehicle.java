package com.example.demo;

import java.io.Serializable;

public class Vehicle implements Serializable {

    private String name;
    private Integer serialNumber;
    private TYPE type;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(Integer serialNumber) {
        this.serialNumber = serialNumber;
    }

    public TYPE getType() {
        return type;
    }

    public void setType(TYPE type) {
        this.type = type;
    }

    public Vehicle(String name, Integer serialNumber, TYPE type) {
        this.name = name;
        this.serialNumber = serialNumber;
        this.type = type;
    }

    public Vehicle() {
    }

    @Override
    public String toString() {
        return "Vehicle{" +
                "name='" + name + '\'' +
                ", serialNumber=" + serialNumber +
                ", type=" + type +
                '}';
    }
}

enum TYPE {
    COUPE, SEDAN, LIMUZINE, SMALL, BIG, WHATEVER
}
