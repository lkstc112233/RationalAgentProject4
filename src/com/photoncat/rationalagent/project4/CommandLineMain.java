package com.photoncat.rationalagent.project4;

import com.photoncat.rationalagent.project4.util.FileLoader;
import com.photoncat.rationalagent.project4.util.LoadedData;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CommandLineMain {
    private static List<Integer> handleByMultiplication(LoadedData data) {
        double[] probabilityLastLayer = null;
        int[][] fromWhere = new int[data.getLength()][3];
        int currentIndex = 0;
        int largestLastLayer = 0;
        final double INITIAL_PROBABILITY = 1.0 / 3;
        for (Integer observation: data) {
            if (probabilityLastLayer == null) {
                probabilityLastLayer = new double[3];
                for (int i = 0; i < 3; ++i) {
                    probabilityLastLayer[i] = INITIAL_PROBABILITY * data.getProbabilityOfRolling(i + 1, observation);
                }
            } else {
                double[][] newProbability = new double[3][3];
                for (int i = 0; i < 3; ++i) {
                    for (int j = 0; j < 3; ++j) {
                        double pTransfer =
                                i == j ? data.getProbabilityOfNotSwitching()
                                        : ((1 - data.getProbabilityOfNotSwitching()) / 2);
                        newProbability[i][j] = probabilityLastLayer[i] * pTransfer * data.getProbabilityOfRolling(j + 1, observation);
                    }
                }
                double globalMax = 0;
                for (int i = 0; i < 3; ++i) {
                    double max = 0;
                    int maxId = 0;
                    for (int j = 0; j < 3; ++j) {
                        if (newProbability[j][i] > max) {
                            max = newProbability[j][i];
                            maxId = j;
                        }
                    }
                    probabilityLastLayer[i] = max;
                    fromWhere[currentIndex][i] = maxId;
                    if (globalMax < max) {
                        globalMax = max;
                        largestLastLayer = i;
                    }
                }
            }
            currentIndex += 1;
        }
        List<Integer> result = new ArrayList<>();
        for (int i = 0; i < data.getLength(); ++i) {
            result.add(largestLastLayer + 1);
            largestLastLayer = fromWhere[data.getLength() - 1 - i][largestLastLayer];
        }
        Collections.reverse(result);
        return result;
    }

    private static List<Integer> handleByLogAddition(LoadedData data) {
        double[] logProbabilityLastLayer = null;
        int[][] fromWhere = new int[data.getLength()][3];
        int currentIndex = 0;
        int largestLastLayer = 0;
        final double INITIAL_PROBABILITY = Math.log(1.0 / 3);
        for (Integer observation: data) {
            if (logProbabilityLastLayer == null) {
                logProbabilityLastLayer = new double[3];
                for (int i = 0; i < 3; ++i) {
                    logProbabilityLastLayer[i] = INITIAL_PROBABILITY + data.getLogProbabilityOfRolling(i + 1, observation);
                }
            } else {
                double[][] newLogProbability = new double[3][3];
                for (int i = 0; i < 3; ++i) {
                    for (int j = 0; j < 3; ++j) {
                        double pTransfer = i == j ? data.getLogProbabilityOfNotSwitching() : data.getLogProbabilityOfSwitching();
                        newLogProbability[i][j] = logProbabilityLastLayer[i] + pTransfer + data.getLogProbabilityOfRolling(j + 1, observation);
                    }
                }
                double globalMax = -Double.MAX_VALUE;
                for (int i = 0; i < 3; ++i) {
                    double max = -Double.MAX_VALUE;
                    int maxId = 0;
                    for (int j = 0; j < 3; ++j) {
                        if (newLogProbability[i][j] > max) {
                            max = newLogProbability[i][j];
                            maxId = j;
                        }
                    }
                    logProbabilityLastLayer[i] = max;
                    fromWhere[currentIndex][i] = maxId;
                    if (globalMax < max) {
                        globalMax = max;
                        largestLastLayer = i;
                    }
                }
            }
            currentIndex += 1;
        }
        List<Integer> result = new ArrayList<>();
        for (int i = 0; i < data.getLength(); ++i) {
            result.add(largestLastLayer + 1);
            largestLastLayer = fromWhere[data.getLength() - 1 - i][largestLastLayer];
        }
        Collections.reverse(result);
        return result;
    }

    public static void main(String[] args) {
        if (args.length < 1) {
            System.err.println("Usage: ");
            System.err.println("CommandLineMain inputFilename");
            System.exit(0xDEADBEEF);
        }
        File inputFile = new File(args[0]);
        LoadedData data = null;
        try {
            data = FileLoader.load(new FileReader(inputFile));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.exit(1);
        }
        // Handle the file.
        List<Integer> result = handleByMultiplication(data);
        for (int i : result) {
            System.out.print(i);
            System.out.print(", ");
        }
        System.out.println();
        System.out.print("With a probability: ");
        System.out.println(data.calculateProbabilityOfGivenStates(result));
        System.out.print("Or calculate with log: ");
        System.out.println(data.calculateProbabilityOfGivenStatesThroughLog(result));
        // Handle the file.
        result = handleByLogAddition(data);
        for (int i : result) {
            System.out.print(i);
            System.out.print(", ");
        }
        System.out.println();
        System.out.print("With a probability: ");
        System.out.println(data.calculateProbabilityOfGivenStates(result));
        System.out.print("Or calculate with log: ");
        System.out.println(data.calculateProbabilityOfGivenStatesThroughLog(result));
    }
}
