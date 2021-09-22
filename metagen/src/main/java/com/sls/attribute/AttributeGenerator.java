package com.sls.attribute;

import java.util.Random;

public abstract class AttributeGenerator {
    protected String attributeName;
    protected Random rand;
    
    AttributeGenerator(String attributeName) {
        this.attributeName = attributeName;
        this.rand = new Random();
    }

    public Attribute generate() {
        double value = nextRand();
        return new Attribute(this.attributeName, value, calcRarity(value));
    }
    
    public abstract double nextRand();
    public abstract double calcRarity(double value);
}
