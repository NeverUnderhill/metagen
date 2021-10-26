package com.sls;

import java.lang.reflect.Type;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Stack;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.sls.generators.AttributeGenerator;
import com.sls.generators.CategoryGenerator;
import com.sls.generators.ComponentGenerator;
import com.sls.generators.DiscreeteAttrGen;
import com.sls.generators.GaussianAttrGen;
import com.sls.generators.TraitGenerator;
import com.sls.generators.UniformAttrGen;

public class MetagenListenerImpl extends MetagenBaseListener {
    Gson gson = new GsonBuilder().registerTypeHierarchyAdapter(Collection.class, new CollectionAdapter()).create();
    private Stack<TraitGenerator> traitStack;
    private Stack<CategoryGenerator> categoryStack;
    private Stack<ComponentGenerator> componentStack;
    private Stack<BlockType> blockStack;
    private ComponentGenerator model;

    public ComponentGenerator getModel() {
        return model;
    }

    @Override
    public void enterModel(MetagenParser.ModelContext ctx) {
        componentStack = new Stack<>();
        traitStack = new Stack<>();
        categoryStack = new Stack<>();
        blockStack = new Stack<>();
        componentStack.add(new ComponentGenerator("model"));
        blockStack.add(BlockType.COMPONENT);
    }

    @Override
    public void exitModel(MetagenParser.ModelContext ctx) {
        model = componentStack.pop();
        System.out.println(gson.toJson(model.generate()));
        blockStack.pop();
    }

    @Override
    public void enterComponent(MetagenParser.ComponentContext ctx) {
        String name = ctx.IDENTIFIER().getText();
        ComponentGenerator component;
        if (ctx.list_modifier() == null) {
            component = new ComponentGenerator(name);
        } else {
            int count = Integer.parseInt(ctx.list_modifier().NUMBER().getText());
            component = new ComponentGenerator(name, count);
        }
        componentStack.peek().addComponentGenerator(component);
        componentStack.add(component);
        blockStack.push(BlockType.COMPONENT);
    }

    @Override
    public void exitComponent(MetagenParser.ComponentContext ctx) {
        componentStack.pop();
        blockStack.pop();
    }

    @Override
    public void enterTrait(MetagenParser.TraitContext ctx) {
        String name = ctx.IDENTIFIER().getText();
        TraitGenerator trait = new TraitGenerator(name);
        traitStack.push(trait);
        blockStack.push(BlockType.TRAIT);
    }

    @Override
    public void exitTrait(MetagenParser.TraitContext ctx) {
        while (BlockType.CATEGORY.equals(blockStack.peek())) {
            traitStack.peek().addCategory(categoryStack.pop());
            blockStack.pop();
        }

        if (BlockType.TRAIT.equals(blockStack.peek())) {
            blockStack.pop();
        } else {
            throw new IllegalStateException("Categories must be contained inside Trait");
        }

        if (BlockType.CATEGORY.equals(blockStack.peek())) {
            categoryStack.peek().addTraitGenerator(traitStack.pop());
        } else if (BlockType.COMPONENT.equals(blockStack.peek())) {
            componentStack.peek().addTraitGenerator(traitStack.pop());
        }
    }

    @Override
    public void enterCategory(MetagenParser.CategoryContext ctx) {
        String name = ctx.IDENTIFIER().getText();
        double likelihood = Integer.parseInt(ctx.NUMBER().getText());
        CategoryGenerator cg = new CategoryGenerator(name, likelihood);
        categoryStack.push(cg);
        blockStack.push(BlockType.CATEGORY);
    }

    @Override
    public void enterAttribute(MetagenParser.AttributeContext ctx) {
        AttributeGenerator ag;
        String name = ctx.IDENTIFIER().getText();
        if (ctx.modifier() == null || Objects.equals(ctx.modifier().IDENTIFIER().getText(), "n")) {
            double mean = Double.parseDouble(ctx.NUMBER(0).getText());
            double stdDeviation = Double.parseDouble(ctx.NUMBER(1).getText());

            ag = new GaussianAttrGen(name, mean, stdDeviation);
        } else if (Objects.equals(ctx.modifier().IDENTIFIER().getText(), "u")) {
            double min = Double.parseDouble(ctx.NUMBER(0).getText());
            double max = Double.parseDouble(ctx.NUMBER(1).getText());

            ag = new UniformAttrGen(name, min, max);
        } else if (Objects.equals(ctx.modifier().IDENTIFIER().getText(), "d")) {
            int min = Integer.parseInt(ctx.NUMBER(0).getText());
            int max = Integer.parseInt(ctx.NUMBER(1).getText());

            ag = new DiscreeteAttrGen(name, min, max);
        } else {
            throw new IllegalArgumentException("Unsupported distribution type " + name);
        }

        if (BlockType.CATEGORY.equals(blockStack.peek())) {
            categoryStack.peek().addAttrGenerator(ag);
        } else if (BlockType.COMPONENT.equals(blockStack.peek())) {
            componentStack.peek().addAttrGenerator(ag);
        }
    }

    class CollectionAdapter implements JsonSerializer<List<?>> {
        @Override
        public JsonElement serialize(List<?> src, Type typeOfSrc, JsonSerializationContext context) {
            if (src == null || src.isEmpty()) // exclusion is made here
                return null;

            JsonArray array = new JsonArray();

            for (Object child : src) {
                JsonElement element = context.serialize(child);
                array.add(element);
            }

            return array;
        }
    }

    private enum BlockType {
        COMPONENT, CATEGORY, TRAIT
    }
}