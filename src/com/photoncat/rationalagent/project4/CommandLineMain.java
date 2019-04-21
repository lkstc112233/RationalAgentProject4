package com.photoncat.rationalagent.project4;

import com.photoncat.rationalagent.project4.util.FileLoader;
import com.photoncat.rationalagent.project4.util.LoadedData;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

public class CommandLineMain {
    public static void main(String[] args) {
        if (args.length < 3) {
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
        }
        // TODO: handle the file.
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
                        if (newProbability[i][j] > max) {
                            max = newProbability[i][j];
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
    }
}
