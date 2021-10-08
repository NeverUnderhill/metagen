package com.sls;

import java.util.List;
import java.util.Objects;
import java.util.Stack;

import com.google.gson.Gson;
import com.sls.component.ComponentGenerator;
import com.sls.attribute.AttributeGenerator;
import com.sls.attribute.GaussianAttrGen;
import com.sls.attribute.UniformAttrGen;
import com.sls.component.Component;
import com.sls.trait.CategoryGenerator;
import com.sls.trait.TraitGenerator;

public class MetagenListenerImpl extends MetagenBaseListener {
    Gson gson = new Gson();
    private TraitGenerator activeTrait;
    private ComponentGenerator model;
    private Stack<ComponentGenerator> componentStack;

    @Override
    public void enterModel(MetagenParser.ModelContext ctx) {
        model = new ComponentGenerator("model");
        componentStack = new Stack<>();
        componentStack.add(model);
    }

    @Override
    public void exitModel(MetagenParser.ModelContext ctx) {
        Component result = model.generate(); 
        System.out.println(gson.toJson(result));
        componentStack.pop();
    }

    @Override
    public void enterComponent(MetagenParser.ComponentContext ctx) {
        ComponentGenerator component = new ComponentGenerator(ctx.IDENTIFIER().getText());
        componentStack.peek().addComponentGenerator(component);
        componentStack.add(component);
    }

    @Override
    public void exitComponent(MetagenParser.ComponentContext ctx) {
        componentStack.pop();
    }
    
    @Override
    public void enterTrait(MetagenParser.TraitContext ctx) {
        String name = ctx.IDENTIFIER().getText();
        activeTrait = new TraitGenerator(name);
    }
    
    @Override
    public void exitTrait(MetagenParser.TraitContext ctx) {
        componentStack.peek().addTraitGenerator(activeTrait); 
        activeTrait = null;
    }
    
    @Override
    public void enterCategory(MetagenParser.CategoryContext ctx) {
        if (activeTrait != null) {
            String name = ctx.IDENTIFIER().getText();
            double likelihood = Integer.parseInt(ctx.NUMBER().getText());
            CategoryGenerator cg = new CategoryGenerator(name, likelihood);
            activeTrait.addCategory(cg); 
        } 
    }
    
    @Override
    public void enterAttribute(MetagenParser.AttributeContext ctx) {
        AttributeGenerator ag;
        String name = ctx.IDENTIFIER().getText();
        if (
            ctx.modifier() == null ||
            Objects.equals(ctx.modifier().IDENTIFIER().getText(), "n")
        ) {
            double mean = Double.parseDouble(ctx.NUMBER(0).getText());
            double stdDeviation = Double.parseDouble(ctx.NUMBER(1).getText());

            ag = new GaussianAttrGen(name, mean, stdDeviation);
        } else if (Objects.equals(ctx.modifier().IDENTIFIER().getText(), "u")) {
            double min = Double.parseDouble(ctx.NUMBER(0).getText());
            double max = Double.parseDouble(ctx.NUMBER(1).getText());
            
            ag = new UniformAttrGen(name, min, max);
        } else {
            throw new IllegalArgumentException("Unsupported distribution type " + name);
        }

        if (activeTrait != null) {
            List<CategoryGenerator> categoryGens = activeTrait.getCategoryGenerators();
            categoryGens.get(categoryGens.size() - 1).addAttrGenerator(ag);
        } else {
            componentStack.peek().addAttrGenerator(ag);
        }
    }
}