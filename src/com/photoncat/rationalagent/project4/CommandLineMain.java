package com.photoncat.rationalagent.project4;

import com.photoncat.rationalagent.project4.algorithm.AStarAlgorithm;
import com.photoncat.rationalagent.project4.algorithm.Algorithm;
import com.photoncat.rationalagent.project4.algorithm.DijkstraAlgorithm;
import com.photoncat.rationalagent.project4.util.FileLoader;
import com.photoncat.rationalagent.project4.util.LoadedGraph;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class CommandLineMain {
    public static void main(String[] args) {
        if (args.length < 3) {
            System.err.println("Usage: ");
            System.err.println("CommandLineMain inputFilename startIndex endIndex ...");
            System.exit(0xDEADBEEF);
        }
        List<Integer> starts = new ArrayList<>();
        List<Integer> ends  = new ArrayList<>();
        for (int index = 1; index + 1 < args.length; index = 2) {
            starts.add(Integer.valueOf(args[index]));
            ends.add(Integer.valueOf(args[index + 1]));
        }
        File inputFile = new File(args[0]);
        LoadedGraph graph = null;
        try {
            graph = FileLoader.load(new FileReader(inputFile));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        for (int i = 0; i < starts.size(); ++i) {
            int start = starts.get(i);
            int end = ends.get(i);
            Algorithm algorithm = new DijkstraAlgorithm(graph);
            var before = System.nanoTime();
            int answer = algorithm.shortestPath(start, end);
            var after = System.nanoTime();
            System.out.println("The path between " + start + " and " + end + " has a length of: " + answer);
            System.out.println("Dijkstra Calculation ends in " + (after - before) + " ns.");
            algorithm = new AStarAlgorithm(graph);
            before = System.nanoTime();
            answer = algorithm.shortestPath(start, end);
            after = System.nanoTime();
            System.out.println("The path between " + start + " and " + end + " has a length of: " + answer);
            System.out.println("A* Calculation ends in " + (after - before) + " ns.");
        }
    }
}
