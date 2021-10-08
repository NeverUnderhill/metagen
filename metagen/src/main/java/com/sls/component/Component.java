package com.sls.component;

import java.util.List;

import com.sls.attribute.Attribute;
import com.sls.trait.Trait;

public class Component {
    private String name; 
    private List<Attribute> attributes;
    private List<Trait> traits;
    private List<Component> components;

    public Component(String name) {
       this.name = name;
    }
    
    public double calculateRarity() {
        double rarity = 1;
        
        for (Attribute a: attributes) {
            rarity *= a.getRarity();
        }
        for (Trait t: traits) {
            rarity *= t.calculateRarity();
        }
        for (Component c: components) {
            rarity *= c.calculateRarity();
        }
        
        return rarity;
    }

    public String getName() {
        return name;
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

    public List<Component> getComponents() {
        return components;
    }

    public void setComponents(List<Component> components) {
        this.components = components;
    }
   
}
