package com.sls.trait;

import java.util.ArrayList;
import java.util.List;

import com.sls.attribute.Attribute;

public class Trait {
    private String name;
    private String category; 
    private double rarity;
    private List<Attribute> attributes;
    //private List<Trait> subComponents;
    
    Trait(String name, String category, double rarity) {
        this.name = name;
        this.category = category;
        this.rarity = rarity;
        this.attributes = new ArrayList<>();
        //this.subComponents = new ArrayList<>();
    }
    
    public double calculateRarity() {
        double rarity = 1;

        for (Attribute a: this.attributes) {
            rarity *= a.getRarity();
        }
        
        /*
        for (Trait c: subComponents) {
            rarity *= c.calculateRarity();
        }
        */
        
        return rarity;
    }

    public String getName() {
        return name;
    }

    public String getCategory() {
        return category;
    }

    public void setAttributes(List<Attribute> attributes) {
        this.attributes = attributes;
    }

    public List<Attribute> getAttributes() {
        return attributes;
    }

    /*
    public List<Trait> getSubComponents() {
        return subComponents;
    }
    */

    public double getRarity() {
        return rarity;
    }

}
