package com.sls.properties;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.sls.generators.TraitGenerator;

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
    protected List<Attribute> attributes;
    protected List<Trait> traits;
    
    public Trait(String name, String category, double rarity) {
        this.name = name;
        this.category = category;
        this.rarity = rarity;
        this.attributes = new ArrayList<>();
        this.traits = new ArrayList<>();
    }
    
    public String getName() {
        return name;
    }

    public String getCategory() {
        return category;
    }

    public double getRarity() {
        return rarity;
    }

    public List<Attribute> getAttributes() {
        return attributes;
    }

    public void setAttributes(List<Attribute> attributes) {
        this.attributes = attributes;
    }

    public List<Trait> getTraits() {
        return traits;
    }

    public void setTraits(List<Trait> traits) {
        this.traits = traits;
    }

    public double getAttr(String name) {
        return getAttr(name, this.attributes);
    }

    public float getAttrf(String name) {
        return (float) getAttr(name);
    }

    private double getAttr(String name, List<Attribute> attributes) {
        for (Attribute a : attributes) {
            if (Objects.equals(a.getName(), name)) {
                return a.getValue();
            }
        }
        throw new IllegalArgumentException("Attribute " + name + " does not exist.");
    }

    public Trait getTrait(String name) {
        for (Trait t : traits) {
            if (Objects.equals(t.getName(), name)) {
                return t;
            }
        }
        throw new IllegalArgumentException("Trait " + name + " does not exist.");
    }

    public double getTraitAttr(String traitName, String attrName) {
        Trait t = getTrait(traitName);
        return getAttr(attrName, t.getAttributes());
    }

    public float getTraitAttrf(String traitName, String attrName) {
        return (float) getTraitAttr(traitName, attrName);
    }

    public String getTraitCategory(String name) {
        return getTrait(name).getCategory();
    }
    public double calculateRarity() {
        double rarity = this.rarity;

        for (Attribute a : attributes) {
            rarity *= a.getRarity();
        }
        for (Trait t : traits) {
            rarity *= t.calculateRarity();
        }

        return rarity;
    }
}
