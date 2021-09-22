package com.sls.component;
import java.util.EnumMap;
import java.util.List;
import java.util.Random;

public abstract class QualitativeTrait<T extends Enum<T>> {
    EnumMap<T, Double> probabilityMap;
    //List<QuantitativeTrait> attributes;

    QualitativeTrait(Class<T> clazz, List<Double> probabilities) {
        probabilityMap = new EnumMap<T, Double>(clazz);
        if (probabilityMap.keySet().size() != probabilities.size()) {
            throw new IllegalArgumentException("Bad number of probabilities in config file");
        }

    }

    public T getRandom() {
        double totalWeight = 0;
        double counter = 0;

        for (T t : probabilityMap.keySet()) {
            totalWeight += probabilityMap.get(t);
        }

        Random r = new Random();
        double rand = r.nextDouble() * totalWeight;
        for (T t: probabilityMap.keySet()) {
           if (counter < rand && counter + probabilityMap.get(t) > rand) {
               return t;
           } else {
               counter += probabilityMap.get(t);
           }
        }
        return null;
    }
}
