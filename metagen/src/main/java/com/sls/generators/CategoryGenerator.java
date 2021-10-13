package com.sls.generators;

import java.util.ArrayList;
import java.util.List;

import com.sls.properties.Trait;

public class CategoryGenerator {
    private String name;
    private double likelihood;
    private List<AttributeGenerator> attributeGens;
    private List<TraitGenerator> traitGens;
    
    public CategoryGenerator(String name, double likelihood) {
        this.name = name;
        this.likelihood = likelihood;
        this.attributeGens = new ArrayList<>();
        this.traitGens = new ArrayList<>();
    }
    
    public String getName() {
        return this.name;
    }
    
    public double getLikelihood() {
        return this.likelihood;
    }
    
    public void addTraitGenerator(TraitGenerator tg) {
        traitGens.add(tg);
    }

    public void addAttrGenerator(AttributeGenerator ag) {
        attributeGens.add(ag); 
    }

    public Trait generate(String traitName, double rarity) {
        Trait t = new Trait(traitName, this.name, rarity);
        
        t.setAttributes(AttributeGenerator.generate(attributeGens));
        t.setTraits(TraitGenerator.generate(traitGens));

        return t;
    }
}
