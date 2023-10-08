package pathfinder;

import graph.Graph;
import pathfinder.datastructures.Path;

import java.util.*;

/**
 * This class utilizes Dijkstra's Algorithm to find the shortest path between two nodes in a graph
 */

public class Algorithm<V> {

    // Does NOT represent an ADT

    /**
     * @spec.requires graph has edge labels that are Doubles, start is not null, and dest
     * is not null. start and dest must be of the same type (V) and the must be in graph.
     * @param graph that represents a graph with nodes of a specific type
     * @param start which represents a node in graph of a specific type
     * @param dest  which represents a node in graph of a specific type
     * @return a Path that represents the shortest path from the start node
     * to the end node. If no path exists, then the method will
     * return null
     */
    public static <V> Path<V> dijkstra(Graph<Double, V> graph, V start, V dest) {
        V startNode = start;
        V destNode = dest;
        Queue<Path<V>> active = new PriorityQueue<>();

        // Each element is a path from start to a given node.
        // A path's 'priority' in the queue is the total cost of that path.
        // Nodes for which no path is known yet are not in the queue.
        Set<V> finished = new HashSet<>();

        // Initially we only know of the path from start to itself, which has
        // a cost of zero because it contains no edges.
        active.add(new Path<V>(start));

        while (!active.isEmpty()) {
            Path<V> minPath = active.remove();
            V minDest = minPath.getEnd();
            if (minDest.equals(dest)) {
                return minPath;
            }
            if (finished.contains(minDest)) {
                continue;
            }
            for (Graph.Edge<Double, V> edge : graph.listEdges(new Graph.Node<V>(minDest))) {
                if (!finished.contains(edge.getChild().getNodeLabel())) {
                    Path<V> newPath =
                            minPath.extend(edge.getChild().getNodeLabel(), edge.getEdgeLabel());
                    active.add(newPath);
                }
            }
            finished.add(minDest);
        }
        return null;
    }
}
