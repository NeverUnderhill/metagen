package com.sls.attribute;

public class Attribute {
    private String name;
    private double value;
    private double rarity;
    
    Attribute(String name, double value, double rarity) {
        this.name = name;
        this.value = value;
        this.rarity = rarity;
    }

    public String getName() {
        return name;
    }

    public double getValue() {
        return value;
    }

    public double getRarity() {
        return rarity;
    }
}