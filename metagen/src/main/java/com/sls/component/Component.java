package com.sls.component;

import java.util.ArrayList;
import java.util.List;

import com.sls.attribute.Attribute;

public class Component {
    private String name;
    private String type; 
    private double rarity;
    private List<Attribute> attributes;
    private List<Component> subComponents;
    
    Component(String name, String type) {
        this.name = name;
        this.type = type;
        this.attributes = new ArrayList<>();
        this.subComponents = new ArrayList<>();
    }
    
    public double calculateRarity() {
        double rarity = 1;

        for (Attribute a: this.attributes) {
            rarity *= a.getRarity();
        }
        
        for (Component c: subComponents) {
            rarity *= c.calculateRarity();
        }
        
        return rarity;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public List<Attribute> getAttributes() {
        return attributes;
    }

    public List<Component> getSubComponents() {
        return subComponents;
    }

    public double getRarity() {
        return rarity;
    }

}
