package com.sls.properties;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.google.gson.Gson;

public class Component {
    private String name;
    protected List<Attribute> attributes;
    protected List<Trait> traits;
    protected List<Component> components;

    public Component(String name) {
        this.name = name;
        this.attributes = new ArrayList<>();
        this.traits = new ArrayList<>();
        this.components = new ArrayList<>();
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
        double rarity = 1;

        for (Attribute a : attributes) {
            rarity *= a.getRarity();
        }
        for (Trait t : traits) {
            rarity *= t.calculateRarity();
        }
        for (Component c : components) {
            rarity *= c.calculateRarity();
        }

        return rarity;
    }

    public Component getSubComponent(String name) {
        for (Component c : components) {
            if (Objects.equals(c.getName(), name)) {
                return c;
            }
        }
        throw new IllegalArgumentException("Sub-component " + name + " does not exist.");
    }

    public String toJson() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }
}
