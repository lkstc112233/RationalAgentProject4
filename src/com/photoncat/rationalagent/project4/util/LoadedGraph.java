package com.photoncat.rationalagent.project4.util;


import java.util.*;

/**
 * A compiled, append only program. Basically it's a Short array. You can iterate through it by using for-each loop.
 * @author Xu Ke
 *
 */
public class LoadedGraph implements Iterable<LoadedGraph.GraphNode> {
    public class GraphNode implements Iterable<GraphNode> {
        private final int id;
        private final int x;
        private final int y;
        private Set<GraphNode> edges;

        GraphNode(int id, int x, int y) {
            this.id = id;
            this.x = x;
            this.y = y;
        }

        void setEdges(Set<GraphNode> edges) {
            this.edges = edges;
        }

        public int getId() {
            return id;
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }

        public int distanceTo(GraphNode node) {
            return distanceBetween(this, node);
        }

        /**
         * Provides support for <b>for-each</b> loop.
         */
        @Override
        public Iterator<GraphNode> iterator() {
            return edges.iterator();
        }
    }

    /**
     * @return distance between two nodes. If not directly connected, return -1 instead.
     */
    private int distanceBetween(GraphNode from, GraphNode to) {
        if (!edges.containsKey(nodes.get(from.getId()))) {
            return -1;
        }
        var edgesOfFrom = edges.get(nodes.get(from.getId()));
        if (!edgesOfFrom.containsKey(nodes.get(to.getId()))) {
            return -1;
        }
        return edgesOfFrom.get(nodes.get(to.getId()));
    }

    public GraphNode getNode(int id) {
        return nodes.get(id);
    }

    /**
     * Nodes storage.
     */
    private final List<GraphNode> nodes = new ArrayList<>();
    private final Map<GraphNode, Map<GraphNode, Integer>> edges = new HashMap<>();

    void addNode(int id, int x, int y) {
        nodes.add(new GraphNode(id, x, y));
    }

    void sortNodes() {
        nodes.sort(Comparator.comparingInt(n -> n.id));
    }

    void addEdge(int from, int to, int distance) {
        if (!edges.containsKey(nodes.get(from))) {
            edges.put(nodes.get(from), new HashMap<>());
        }
        if (!edges.containsKey(nodes.get(to))) {
            edges.put(nodes.get(to), new HashMap<>());
        }
        edges.get(nodes.get(from)).put(nodes.get(to), distance);
        edges.get(nodes.get(to)).put(nodes.get(from), distance);
    }

    void createEdgesCache() {
        for (GraphNode node : nodes) {
            node.setEdges(edges.get(node).keySet());
        }
    }

    /**
     * Provides support for <b>for-each</b> loop.
     */
    @Override
    public Iterator<GraphNode> iterator() {
        return nodes.iterator();
    }
}