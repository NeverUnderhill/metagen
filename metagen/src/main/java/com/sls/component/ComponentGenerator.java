package com.sls.component;

import java.util.ArrayList;
import java.util.List;

import com.sls.attribute.AttributeGenerator;
import com.sls.trait.TraitGenerator;

public class ComponentGenerator {
    private String name;
    private List<AttributeGenerator> attributeGens;
    private List<TraitGenerator> traitGens;
    private List<ComponentGenerator> componentGens;
    
    public ComponentGenerator(String name) {
        this.name = name;
        attributeGens = new ArrayList<>();
        traitGens = new ArrayList<>();
        componentGens = new ArrayList<>();
    }

    public static List<Component> generate(List<ComponentGenerator> componentGens) {
        List<Component> components  = new ArrayList<>();
        
        for (ComponentGenerator cg : componentGens) {
            components.add(cg.generate());
        }
        
        return components;
    }
    
    public Component generate() {
        Component component = new Component(name);
        component.setAttributes(AttributeGenerator.generate(this.attributeGens));
        component.setTraits(TraitGenerator.generate(this.traitGens));
        component.setComponents(ComponentGenerator.generate(componentGens));
        
        return component;
    }
    
    public void addComponentGenerator(ComponentGenerator cg) {
        componentGens.add(cg);
    }
    
    public void addTraitGenerator(TraitGenerator tg) {
        traitGens.add(tg);
    }

    public void addAttrGenerator(AttributeGenerator ag) {
        attributeGens.add(ag); 
    }
}
