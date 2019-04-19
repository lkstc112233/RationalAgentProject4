package com.photoncat.rationalagent.project4.algorithm;

import com.photoncat.rationalagent.project4.util.LoadedGraph;
import com.photoncat.rationalagent.project4.util.OnetimePriorityQueue;

import java.util.HashMap;
import java.util.Map;

public class AStarAlgorithm implements Algorithm {
    private final LoadedGraph graph;
    private final Heuristics heuristics;

    public interface Heuristics {
        int heuristic(LoadedGraph.GraphNode current, LoadedGraph.GraphNode target);
    }

    /**
     * Initialize the algorithm with a default heuristic.
     */
    public AStarAlgorithm(LoadedGraph graph) {
        this(graph, (current, target) -> {
            int xDistance = Math.abs(current.getX() - target.getX());
            int yDistance = Math.abs(current.getY() - target.getY());
            if (xDistance > 0) {
                xDistance -= 1;
            }
            if (yDistance > 0) {
                yDistance -= 1;
            }
            return (int) Math.floor(100 * Math.sqrt(xDistance * xDistance + yDistance * yDistance));
        });
    }

    public AStarAlgorithm(LoadedGraph graph, Heuristics heuristics) {
        this.graph = graph;
        this.heuristics = heuristics;
    }

    @Override
    public int shortestPath(int start, int end) {
        OnetimePriorityQueue<LoadedGraph.GraphNode> fringe = new OnetimePriorityQueue<>();
        Map<LoadedGraph.GraphNode, Integer> realCost = new HashMap<>(); // stores g(n)
        var starting = graph.getNode(start);
        var goal = graph.getNode(end);
        fringe.add(starting, heuristics.heuristic(starting, goal));
        realCost.put(starting, 0);
        while (!fringe.isEmpty() && fringe.peek().getKey() != goal) {
            var pair = fringe.poll();
            var currentNode = pair.getKey();
            int currentCost = realCost.get(currentNode);
            for (var nodeNeighbour : currentNode) {
                int newCost = currentCost + currentNode.distanceTo(nodeNeighbour);
                if (fringe.add(nodeNeighbour, newCost + heuristics.heuristic(nodeNeighbour, goal))) {
                    if (!realCost.containsKey(nodeNeighbour) || realCost.get(nodeNeighbour) > newCost) {
                        realCost.put(nodeNeighbour, newCost);
                    }
                }
            }
        }
        if (fringe.isEmpty()) {
            return -1;
        }
        return realCost.get(fringe.peek().getKey());
    }
}
