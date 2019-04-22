package com.photoncat.rationalagent.project4.util;


import java.util.*;

/**
 * A compiled, append only program. Basically it's a Short array. You can iterate through it by using for-each loop.
 * @author Xu Ke
 *
 */
public class LoadedData implements Iterable<Integer> {
    /**
     * Data storage.
     */
    private final double probabilityOfNotSwitching;
    private final double logProbabilityOfNotSwitching;
    private final double logProbabilityOfSwitching;
    private final double p11;
    private final double p12;
    private final double p13;
    private final double p21;
    private final double p22;
    private final double p23;
    private final double p31;
    private final double p32;
    private final double p33;
    private final double logP11;
    private final double logP12;
    private final double logP13;
    private final double logP21;
    private final double logP22;
    private final double logP23;
    private final double logP31;
    private final double logP32;
    private final double logP33;
    private final List<Integer> observations = new ArrayList<>();

    LoadedData(double probabilityOfNotSwitching, double[][] pInputs, List<Integer> observations) {
        this.probabilityOfNotSwitching = probabilityOfNotSwitching;
        logProbabilityOfNotSwitching = Math.log(this.probabilityOfNotSwitching);
        logProbabilityOfSwitching = Math.log((1 - this.probabilityOfNotSwitching) / 2);
        p11 = pInputs[0][0];
        logP11 = Math.log(p11);
        p12 = pInputs[0][1];
        logP12 = Math.log(p12);
        p13 = pInputs[0][2];
        logP13 = Math.log(p13);
        p21 = pInputs[1][0];
        logP21 = Math.log(p21);
        p22 = pInputs[1][1];
        logP22 = Math.log(p22);
        p23 = pInputs[1][2];
        logP23 = Math.log(p23);
        p31 = pInputs[2][0];
        logP31 = Math.log(p31);
        p32 = pInputs[2][1];
        logP32 = Math.log(p32);
        p33 = pInputs[2][2];
        logP33 = Math.log(p33);
        this.observations.addAll(observations);
    }

    static double reverseLog(double loggedValue) {
        return Math.pow(Math.E, loggedValue);
    }

    public double calculateProbabilityOfGivenStates(List<Integer> inputStates) {
        double probability = 1;
        if (inputStates.size() != observations.size()) {
            throw new IllegalArgumentException("Observations mismatches input states in count.");
        }
        int lastState = 0;
        for (int i = 0; i < inputStates.size(); ++i) {
            if (lastState == 0) {
                probability = 1.0 / 3 * getProbabilityOfRolling(inputStates.get(i), observations.get(i));
            } else {
                double transferProbability =
                        inputStates.get(i) == lastState ? probabilityOfNotSwitching : ((1 - probabilityOfNotSwitching) / 2);
                probability *= transferProbability * getProbabilityOfRolling(inputStates.get(i), observations.get(i));
            }
            lastState = inputStates.get(i);
        }
        return probability;
    }

    public double calculateProbabilityOfGivenStatesThroughLog(List<Integer> inputStates) {
        double probability = 0;
        if (inputStates.size() != observations.size()) {
            throw new IllegalArgumentException("Observations mismatches input states in count.");
        }
        int lastState = 0;
        for (int i = 0; i < inputStates.size(); ++i) {
            if (lastState == 0) {
                probability = Math.log(1.0 / 3) + getLogProbabilityOfRolling(inputStates.get(i), observations.get(i));
            } else {
                double transferProbability =
                        inputStates.get(i) == lastState ? logProbabilityOfNotSwitching : logProbabilityOfSwitching;
                probability += transferProbability + getLogProbabilityOfRolling(inputStates.get(i), observations.get(i));
            }
            lastState = inputStates.get(i);
        }
        return reverseLog(probability);
    }

    public double getProbabilityOfNotSwitching() {
        return probabilityOfNotSwitching;
    }

    public double getLogProbabilityOfNotSwitching() {
        return logProbabilityOfNotSwitching;
    }

    public double getLogProbabilityOfSwitching() {
        return logProbabilityOfSwitching;
    }

    public double getProbabilityOfRolling(int dice, int side) {
        switch (dice) {
            case 1:
                switch(side) {
                    case 1:
                        return p11;
                    case 2:
                        return p12;
                    case 3:
                        return p13;
                    default:
                        break;
                }
                break;
            case 2:
                switch(side) {
                    case 1:
                        return p21;
                    case 2:
                        return p22;
                    case 3:
                        return p23;
                    default:
                        break;
                }
                break;
            case 3:
                switch(side) {
                    case 1:
                        return p31;
                    case 2:
                        return p32;
                    case 3:
                        return p33;
                    default:
                        break;
                }
                break;
        }
        throw new IllegalArgumentException("Unexpected dice or side in getProbabilityOfRolling.");
    }

    public double getLogProbabilityOfRolling(int dice, int side) {
        switch (dice) {
            case 1:
                switch(side) {
                    case 1:
                        return logP11;
                    case 2:
                        return logP12;
                    case 3:
                        return logP13;
                    default:
                        break;
                }
                break;
            case 2:
                switch(side) {
                    case 1:
                        return logP21;
                    case 2:
                        return logP22;
                    case 3:
                        return logP23;
                    default:
                        break;
                }
                break;
            case 3:
                switch(side) {
                    case 1:
                        return logP31;
                    case 2:
                        return logP32;
                    case 3:
                        return logP33;
                    default:
                        break;
                }
                break;
        }
        throw new IllegalArgumentException("Unexpected dice or side in getProbabilityOfRolling.");
    }

    public int getLength() {
        return observations.size();
    }

    /**
     * Provides support for <b>for-each</b> loop.
     */
    @Override
    public Iterator<Integer> iterator() {
        return observations.iterator();
    }
}