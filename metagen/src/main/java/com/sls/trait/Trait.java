package com.sls.trait;

import java.util.ArrayList;
import java.util.List;

import com.sls.attribute.Attribute;

/**
 * Class that represents a qualitative trait.
 * Instances of this class are obtained through {@link TraitGenerator}.
 * 
 * An example of a trait would be EyeType, and different individuals can have
 * different eye types, one can have round eye, another one squinty etc.
 * Those different kinds of the trait are called categories.
 */
public class Trait {
    private String name;
    private String category; 
    private double rarity;
    private List<Attribute> attributes;
    
    public Trait(String name, String category, double rarity) {
        this.name = name;
        this.category = category;
        this.rarity = rarity;
        this.attributes = new ArrayList<>();
    }
    
    public double calculateRarity() {
        double rarity = this.rarity;

        for (Attribute a: attributes) {
            rarity *= a.getRarity();
        }
        
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

    public double getRarity() {
        return rarity;
    }
}
