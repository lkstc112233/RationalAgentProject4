package com.photoncat.rationalagent.project4.util;

import java.util.ArrayList;

public class LoadedDataTest {
    private static boolean equalsWithTolerance(double d1, double d2) {
        final double TOLERANCE = 1e-7;
        return Math.abs(d1 - d2) < TOLERANCE;
    }

    public static void main(String[] args) {
        LoadedData data = new LoadedData(0.4, new double[][]{{0.1,0.2,0.3},{0.4,0.5,0.6},{0.7,0.8,0.9}}, new ArrayList<>());
        if (!equalsWithTolerance(data.getProbabilityOfRolling(1, 1), LoadedData.reverseLog(data.getLogProbabilityOfRolling(1, 1))))
            throw new AssertionError();
        if (!equalsWithTolerance(data.getProbabilityOfRolling(1, 2), LoadedData.reverseLog(data.getLogProbabilityOfRolling(1, 2))))
            throw new AssertionError();
        if (!equalsWithTolerance(data.getProbabilityOfRolling(1, 3), LoadedData.reverseLog(data.getLogProbabilityOfRolling(1, 3))))
            throw new AssertionError();
        if (!equalsWithTolerance(data.getProbabilityOfRolling(2, 1), LoadedData.reverseLog(data.getLogProbabilityOfRolling(2, 1))))
            throw new AssertionError();
        if (!equalsWithTolerance(data.getProbabilityOfRolling(2, 2), LoadedData.reverseLog(data.getLogProbabilityOfRolling(2, 2))))
            throw new AssertionError();
        if (!equalsWithTolerance(data.getProbabilityOfRolling(2, 3), LoadedData.reverseLog(data.getLogProbabilityOfRolling(2, 3))))
            throw new AssertionError();
        if (!equalsWithTolerance(data.getProbabilityOfRolling(3, 1), LoadedData.reverseLog(data.getLogProbabilityOfRolling(3, 1))))
            throw new AssertionError();
        if (!equalsWithTolerance(data.getProbabilityOfRolling(3, 2), LoadedData.reverseLog(data.getLogProbabilityOfRolling(3, 2))))
            throw new AssertionError();
        if (!equalsWithTolerance(data.getProbabilityOfRolling(3, 3), LoadedData.reverseLog(data.getLogProbabilityOfRolling(3, 3))))
            throw new AssertionError();
        if (!equalsWithTolerance(data.getProbabilityOfNotSwitching(), LoadedData.reverseLog(data.getLogProbabilityOfNotSwitching())))
            throw new AssertionError();
        if (!equalsWithTolerance((1 - data.getProbabilityOfNotSwitching()) / 2, LoadedData.reverseLog(data.getLogProbabilityOfSwitching())))
            throw new AssertionError();
        System.out.println("All test passed.");
    }
}
