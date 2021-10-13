package com.sls.generators;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.sls.properties.Attribute;

public abstract class AttributeGenerator {
    protected String attributeName;
    protected Random rand;
    
    public AttributeGenerator(String attributeName) {
        this.attributeName = attributeName;
        this.rand = new Random();
    }
    
    public static List<Attribute> generate(List<AttributeGenerator> attrGens) {
        List<Attribute> attributes = new ArrayList<>();
        
        for (AttributeGenerator ag : attrGens) {
            attributes.add(ag.generate());
        }
        
        return attributes;
    }

    public Attribute generate() {
        double value = nextRand();
        return new Attribute(this.attributeName, value, calcRarity(value));
    }
    
    protected abstract double nextRand();
    protected abstract double calcRarity(double value);
}
