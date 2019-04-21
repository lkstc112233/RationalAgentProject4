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
    private final double p11;
    private final double p12;
    private final double p13;
    private final double p21;
    private final double p22;
    private final double p23;
    private final double p31;
    private final double p32;
    private final double p33;
    private final List<Integer> observations = new ArrayList<>();

    LoadedData(double probabilityOfNotSwitching, double[][] pInputs, List<Integer> observations) {
        this.probabilityOfNotSwitching = probabilityOfNotSwitching;
        p11 = pInputs[0][0];
        p12 = pInputs[0][1];
        p13 = pInputs[0][2];
        p21 = pInputs[1][0];
        p22 = pInputs[1][1];
        p23 = pInputs[1][2];
        p31 = pInputs[2][0];
        p32 = pInputs[2][1];
        p33 = pInputs[2][2];
        this.observations.addAll(observations);
    }

    public double getProbabilityOfNotSwitching() {
        return probabilityOfNotSwitching;
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