package com.sls.trait;

import java.util.ArrayList;
import java.util.List;

import com.sls.attribute.Attribute;
import com.sls.attribute.AttributeGenerator;

public class CategoryGenerator {
    private String name;
    private double likelihood;
    private List<AttributeGenerator> attrGens;
    
    public CategoryGenerator(String name, double likelihood) {
        this.name = name;
        this.likelihood = likelihood;
        this.attrGens = new ArrayList<>();
    }
    
    public String getName() {
        return this.name;
    }
    
    public double getLikelihood() {
        return this.likelihood;
    }
    
    public void addAttrGenerator(AttributeGenerator a) {
        this.attrGens.add(a);
    }
    
    public List<Attribute> generateAttributes() {
        List<Attribute> attributes = new ArrayList<>();
        
        for (AttributeGenerator ag : attrGens) {
            attributes.add(ag.generate());
        }
        
        return attributes;
    }
}
