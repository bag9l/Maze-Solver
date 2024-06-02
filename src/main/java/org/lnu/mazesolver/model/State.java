package org.lnu.mazesolver.model;

import lombok.ToString;

@ToString
public class State {
    public double[] qValues = {0, 0, 0, 0};

    public int getBestAction() {
        int bestAction = 0;
        double bestValue = -100;

        for (int i = 0; i < qValues.length; i++) {
            if (qValues[i] > bestValue) {
                bestAction = i;
                bestValue = qValues[i];
            } else if (qValues[i] == bestValue) {
                if (Math.random() > 0.5f) {
                    bestAction = i;
                    bestValue = qValues[i];
                }
            }
        }

        return bestAction;
    }

    public double getBestValue() {
        double bestValue = -100;

        for (double qValue : qValues) {
            if (qValue > bestValue) {
                bestValue = qValue;
            }
        }

        return bestValue;
    }
}
