package com.sls.attribute;

public class Attribute {
    private String name;
    private double value;
    private double rarity;
    
    public Attribute(String name, double value, double rarity) {
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
    
    @Override
    public String toString() {
        return String.format("\n Name: %s \n value: %f \n rarity %f", name, value, rarity);
    }
}