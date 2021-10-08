package com.sls.trait;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class TraitGenerator {
    private String name;
    private List<CategoryGenerator> categoryGens;
    
    public TraitGenerator(String name) {
        this.name = name;
        this.categoryGens = new ArrayList<>();
    }

    private CategoryGenerator getRandomCategory() {
        Random r = new Random();
        double likelihoodSum = calcLikelihoodSum();
        double counter = 0;

        double rand = r.nextDouble() * likelihoodSum;
        for (CategoryGenerator c: categoryGens) {
           if (counter < rand && counter + c.getLikelihood() > rand) {
               return c;
           } else {
               counter += c.getLikelihood();
           }
        }
        throw new IllegalStateException();
    }
    
    public static List<Trait> generate(List<TraitGenerator> traitGens) {
        List<Trait> traits = new ArrayList<>();
        
        for (TraitGenerator tg : traitGens) {
            traits.add(tg.generate());
        }
        
        return traits;
    }
    private double calcLikelihoodSum() {
        double likelihoodSum = 0;
        for (CategoryGenerator c : categoryGens) {
            likelihoodSum += c.getLikelihood();
        }
        return likelihoodSum;
    }
    
    public Trait generate() {
        CategoryGenerator category = this.getRandomCategory();
        double rarity = category.getLikelihood() / calcLikelihoodSum();
        Trait trait = new Trait(this.name, category.getName(), rarity);
        trait.setAttributes(category.generateAttributes());
        
        return trait;
    }
    
    public List<CategoryGenerator> getCategoryGenerators() {
        return categoryGens;
    }
    
    public void addCategory(CategoryGenerator cg) {
        this.categoryGens.add(cg);
    }
}
