package com.sls.attribute;

public class UniformAttrGen extends AttributeGenerator {
    static final int BUCKET_NUMBER = 5;
    double min;
    double max;
    
    public UniformAttrGen(String name, double min, double max) {
        super(name);
        this.min = min;
        this.max = max;
    }

    @Override
    protected double nextRand() {
        return min + rand.nextDouble() * (max - min); 
    }

    @Override
    protected double calcRarity(double value) {
        return 1.0 / BUCKET_NUMBER;
    } 
}
