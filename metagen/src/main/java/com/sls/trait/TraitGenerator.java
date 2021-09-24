package com.sls.trait;
import java.util.List;
import java.util.Random;

public class TraitGenerator {
    private String name;
    private List<CategoryGenerator> categories;

    private CategoryGenerator getRandomCategory() {
        Random r = new Random();
        double likelihoodSum = calcLikelihoodSum();
        double counter = 0;

        double rand = r.nextDouble() * likelihoodSum;
        for (CategoryGenerator c: categories) {
           if (counter < rand && counter + c.getLikelihood() > rand) {
               return c;
           } else {
               counter += c.getLikelihood();
           }
        }
        throw new IllegalStateException();
    }
    
    private double calcLikelihoodSum() {
        double likelihoodSum = 0;
        for (CategoryGenerator c : categories) {
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
}
