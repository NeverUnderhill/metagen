package com.sls.generators;

import java.util.ArrayList;
import java.util.List;

import com.sls.properties.Component;

public class ComponentGenerator {
    private String name;
    protected int multiplicity;
    protected List<AttributeGenerator> attributeGens;
    protected List<TraitGenerator> traitGens;
    protected List<ComponentGenerator> componentGens;
    
    public ComponentGenerator(String name, int multiplicity) {
        this.name = name;
        this.multiplicity = multiplicity;
        this.attributeGens = new ArrayList<>();
        this.traitGens = new ArrayList<>();
        this.componentGens = new ArrayList<>();
    }

    public ComponentGenerator(String name) {
        this(name, 1);
    }

    public void addTraitGenerator(TraitGenerator tg) {
        traitGens.add(tg);
    }

    public void addAttrGenerator(AttributeGenerator ag) {
        attributeGens.add(ag); 
    }
    
    public void addComponentGenerator(ComponentGenerator cg) {
        componentGens.add(cg);
    }

    public static List<Component> generate(List<ComponentGenerator> componentGens) {
        List<Component> components  = new ArrayList<>();
        
        for (ComponentGenerator cg : componentGens) {
            components.addAll(cg.generate());
        }
        
        return components;
    }
    
    public Component generateSingle() {
        Component component = new Component(name);
        component.setAttributes(AttributeGenerator.generate(attributeGens));
        component.setTraits(TraitGenerator.generate(traitGens));
        component.setComponents(ComponentGenerator.generate(componentGens));
        return component;
    }

    public List<Component> generate() {
        List<Component> output = new ArrayList<>();
        for (int i = 0; i < this.multiplicity; i++) {
            Component component = new Component(name);
            component.setAttributes(AttributeGenerator.generate(attributeGens));
            component.setTraits(TraitGenerator.generate(traitGens));
            component.setComponents(ComponentGenerator.generate(componentGens));
            output.add(component);
        }
        
        return output;
    }
    
}
