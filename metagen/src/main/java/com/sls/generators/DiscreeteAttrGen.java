package com.sls.generators;

public class DiscreeteAttrGen extends AttributeGenerator {
    int min, max;

    public DiscreeteAttrGen(String attributeName, int min, int max) {
        super(attributeName);
        this.min = min;
        this.max = max;
    }

    @Override
    protected double nextRand() {
        return min + (rand.nextInt(max - min));
    }

    @Override
    protected double calcRarity(double value) {
        return 1 / (max - min);
    }
    
}
