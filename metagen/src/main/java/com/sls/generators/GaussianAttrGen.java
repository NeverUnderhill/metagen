package com.sls.generators;

public class GaussianAttrGen extends AttributeGenerator {
    double mean;
    double stdDeviation;
    
    public GaussianAttrGen(String name, double mean, double stdDeviation) {
        super(name);
        this.mean = mean;
        this.stdDeviation = stdDeviation;
    }

    @Override
    protected double nextRand() {
        return mean + ((float) rand.nextGaussian()) * stdDeviation; 
    }

    @Override
    protected double calcRarity(double value) {
        double normalAbsValue = Math.abs(normalize(value));
        if (normalAbsValue < 1.28155) {
            return 0.8;
        } else if (normalAbsValue < 1.81191) {
            return 0.13;
        } else if (normalAbsValue < 2.5427) {
            return 0.059;
        } else if (normalAbsValue < 3.29053) {
            return 0.01;
        } else {
            return 0.001;
        }
    }
    
    private double normalize(double value) {
        return (value - this.mean) / this.stdDeviation;
    } 
}
