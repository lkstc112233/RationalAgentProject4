package com.photoncat.rationalagent.project4.algorithm;

import com.photoncat.rationalagent.project4.util.LoadedGraph;
import com.photoncat.rationalagent.project4.util.OnetimePriorityQueue;

/**
 * A class that holds the algorithm as in Dijkstra's algorithm.
 */
public class DijkstraAlgorithm implements Algorithm{
    private final LoadedGraph graph;

    public DijkstraAlgorithm(LoadedGraph graph) {
        this.graph = graph;
    }

    public int shortestPath(int start, int end) {
        OnetimePriorityQueue<LoadedGraph.GraphNode> fringe = new OnetimePriorityQueue<>();
        fringe.add(graph.getNode(start), 0);
        var goal = graph.getNode(end);
        while (!fringe.isEmpty() && fringe.peek().getKey() != goal) {
            var pair = fringe.poll();
            var currentNode = pair.getKey();
            int currentValue = pair.getValue();
            for (var nodeNeighbour: currentNode) {
                fringe.add(nodeNeighbour, currentValue + currentNode.distanceTo(nodeNeighbour));
            }
        }
        if (fringe.isEmpty()) {
            return -1;
        }
        return fringe.peek().getValue();
    }
}
