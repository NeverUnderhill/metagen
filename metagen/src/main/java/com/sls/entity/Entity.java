package com.sls.entity;
import java.util.ArrayList;
import java.util.List;

import com.sls.attribute.Attribute;
import com.sls.component.Component;

public class Entity {
    String name;
    int numInSequence;
    List<Attribute> attributes;
    List<Component> components;
    
    Entity(String name, int numInSequence) {
        this.name = name;
        this.numInSequence = numInSequence;
        this.attributes = new ArrayList<>();
        this.components = new ArrayList<>();
    }
    
    public double calculateRarity() {
        double rarity = 1;
        
        for (Attribute a: attributes) {
            rarity *= a.getRarity();
        }

        for (Component c: components) {
            rarity *= c.calculateRarity();
        }

        return rarity;
    }
}
