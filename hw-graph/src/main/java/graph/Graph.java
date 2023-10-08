package graph;

import java.util.*;


/**
 * Represents a mutable, directed labeled graph, a collection of Nodes (vertices) and Edges, which
 * connect two nodes and are one way.
 */

public class Graph<E, V> {

    // RI: this.graph is not null and contains no null keys (nodes) or values (list of edges).
    //     The set of nodes in a graph (this.graph.keySet()) contains no duplicates and the list
    //     of outgoing edges for a particular node also contains no duplicates. Additionally,
    //     nodes are all the same type.
    // AF(this): this.graph is a graph where this.graph.keySet() represents all the Nodes in
    //           the graph and this.graph.get(Node n) represents a list of the outgoing edges
    //           of Node n;

    public static final boolean DEBUG = false;

    private Map<Node<V>, List<Edge<E, V>>> graph;


    /**
     * Represents an immutable Node with a label of a specific type (V)
     */

    public static class Node<V> {

        // RI: nodeLabel of a specific type (V) is not null
        // AF(this): represents a Node with a unique nodeLabel of a specific type (V)
        private final V nodeLabel;

        /**
         * Creates a Node.
         * @param nodeLabel of a specific type that is a unique identifier of a Node
         * @spec.requires nodeLabel is not null
         * @spec.effects this Node is a Node with nodeLabel as its data
         */
        public Node (V nodeLabel) {
            this.nodeLabel= nodeLabel;
            checkRep();
        }

        /**
         * Gets a Node's unique label
         * @return a value of a specific type that represents this Node's data
         * (their unique label)
         */
        public V getNodeLabel() {
            checkRep();
            return this.nodeLabel;
        }


        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Node<?> node = (Node<?>) o;
            return Objects.equals(nodeLabel, node.nodeLabel);
        }

        @Override
        public int hashCode() {
            return Objects.hash(nodeLabel);
        }

        // Checks that nodeLabel is not null
        private void checkRep() {
            assert (this.nodeLabel != null);
        }
    }

    /**
     * Represents an immutable Edge using Nodes and an edge label
     */
    public static class Edge<E, V> {

        // RI: parentNode is not null, childNode is not null, and E edgeLabel is not null.
        //     parentNode and childNode are of the same type.
        // AF(this): Edge --> (parent node, child node, label)

        private final Node<V> parentNode;
        private final Node<V> childNode;
        private final E edgeLabel;

        /**
         * Creates an Edge that connects a parent node to a child node
         * @param parentNode Node (parent node) of a particular type (V)
         * @param childNode Node (child node) of a particular type (V)
         * @param edgeLabel of a specific type (label)
         * @spec.requires Node parent, Node child, and edgeLabel are all not null.
         * Node parent and Node child are of the same type.
         * @spec.effects an Edge that connects Node parent and Node child with an edgeLabel as
         * its data
         */
        public Edge (Node<V> parentNode, Node<V> childNode, E edgeLabel) {
            this.parentNode = parentNode;
            this.childNode = childNode;
            this.edgeLabel = edgeLabel;
            this.checkRep();
        }

        /**
         * Gets this Edge's parent Node
         * @return a Node that represents this Edge's parent Node
         */
        public Node<V> getParent() {
            checkRep();
            return this.parentNode;
        }

        /**
         * Gets this Edge's child Node
         * @return a Node that represents this Edge's child Node
         */
        public Node<V> getChild() {
            checkRep();
            return this.childNode;
        }

        /**
         * Gets this Edge's label
         * @return a value that represents this Edge's label
         */
        public E getEdgeLabel() {
            checkRep();
            return this.edgeLabel;
        }


        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Edge<?, ?> edge = (Edge<?, ?>) o;
            return Objects.equals(parentNode, edge.parentNode) &&
                    Objects.equals(childNode, edge.childNode) &&
                    Objects.equals(edgeLabel, edge.edgeLabel);
        }

        @Override
        public int hashCode() {
            return Objects.hash(parentNode, childNode, edgeLabel);
        }

        // Checks that parentNode, childNode, and edgeLabel are not null
        private void checkRep() {
            assert (this.childNode != null && this.parentNode != null
                    && this.edgeLabel != null);
            if (DEBUG)  {

            }
        }
    }

    /**
     * Creates an empty Graph.
     * @spec.effects this Graph is an empty Graph
     */
    public Graph () {
        this.graph = new HashMap<>();
        checkRep();
    }

    /**
     * Adds a node to the Graph (this).
     * @param n Node
     * @spec.requires Node n is not null
     * @spec.modifies this Graph
     * @spec.effects  this = this + (Node n). Nothing will
     *                happen if Node n already exists in the graph
     */
    public void insertNode(Node<V> n) {
        checkRep();
        if (n == null) {
            throw new NullPointerException();
        }
        if (!containsNode(n)) {
            graph.put(n, new ArrayList<Edge<E,V>>());
        }
        checkRep();
    }

    /**
     * Adds an edge with a label that connects the parent node to the child node
     * in this Graph.
     * @param parentNode Node that represents the initial or starting node
     * @param childNode Node that represents the final node the edge connects to
     * @param edgeLabel of a specific type (E) that represents a unique label for the edge.
     * @spec.modifies this Graph
     * @spec.effects this Graph has one additional edge that connects the parent and the child
     * nodes. Nothing will happen if Edge already exists in the graph
     * @throws IllegalArgumentException if parentNode or childNode (or both) do not refer to
     * nodes that exist in this Graph
     * @throws NullPointerException if parentNode or childNode or edgeLabel is null
     */
    public void insertEdge(Node<V> parentNode, Node<V> childNode,E edgeLabel) {
        checkRep();
        if (parentNode == null || childNode == null || edgeLabel == null) {
            throw new NullPointerException();
        }
        if (!(graph.containsKey(parentNode)) || !(graph.containsKey(childNode))) {
            throw new IllegalArgumentException();
        }
        Edge<E, V> edge = new Edge<>(parentNode, childNode, edgeLabel);
        if (!graph.get(parentNode).contains(edge)) {
            graph.get(parentNode).add(edge);
        }
        checkRep();
    }


    /**
     * Returns all the Nodes from this Graph.
     * @return A list of Nodes from this Graph
     */
    public List<Node<V>> listNodes() {
        checkRep();
//        List<Node<V>> listOfNodes = new ArrayList<>();
//        Iterator<Node<V>> nodes = this.graph.keySet().iterator();
//        while(nodes.hasNext()) {
//            listOfNodes.add(nodes.next());
//        }
//        return listOfNodes;
        return new ArrayList<>(this.graph.keySet());
    }




    /**
     * Returns all edges that are outgoing from a parent Node
     * @param parent Node
     * @spec.requires parent Node is not null
     * @return A list of the outgoing edges of Node parent
     * @throws IllegalArgumentException if parent Node is not in this Graph
     */
    public List<Edge<E, V>> listEdges(Node<V> parent) {
        checkRep();
        if (!(graph.containsKey(parent))) {
            throw new IllegalArgumentException();
        }
        List<Edge<E,V>> listOfEdges = new ArrayList<>();
            for (Edge<E,V> edge : graph.get(parent)) {
                listOfEdges.add(edge);
            }
        return listOfEdges;
    }


    /**
     * Checks to see whether a graph contains graphNode
     * @spec.requires Node graphNode is not null
     * @param graphNode Node represents the node we're checking to see if a graph contains
     * @return true if Node graphNode represents a node in this Graph and false if graphNode
     * does not represent an already existing node in this Graph.
     */
    public boolean containsNode(Node<V> graphNode) {
        checkRep();
        return this.graph.containsKey(graphNode);
    }

    /**
     * Checks to see whether a graph contains edge with a specific label
     * as its identifier.
     * @param parentNode Node that represents the initial or starting node
     * @param childNode Node that represents the final node the edge connects to
     * @param edgeLabel of a specific type (E) that represents a unique label for the edge.
     * @return true if (parentNode, childNode, edgeLabel) represents an edge in this Graph and
     * false if it does not represent an already existing edge in this Graph.
     * @throws NullPointerException if parentNode or childNode or edgeLabel is null
     */
    public boolean containsEdge(Node<V> parentNode, Node<V> childNode, E edgeLabel) {
        checkRep();
        if (parentNode == null || childNode == null || edgeLabel == null) {
            throw new NullPointerException();
        }
        if (graph.isEmpty()) {
            return false;
        }
        return graph.get(parentNode).contains(new Edge<E, V>(parentNode,childNode, edgeLabel));
    }


    // checks to ensure that the rep invariant of Graph holds throughout the class
    private void checkRep() {
        // Check that this.graph is not null, that our set of nodes (keys) are not null,
        // and that none of the Nodes in our graph are null.
        assert ((graph != null) && (graph.keySet() != null));
        for (Node<V> n : graph.keySet()) {
            assert (n != null);
        }
        // checks that none of our edges in our graph are null and that we don't contain
        // two of the exact same Edge.
        if (DEBUG)  {
            for (Node<V> n : graph.keySet()) {
                Set<Edge<E,V>> edges = new HashSet<>();
                for (Edge<E,V> e : graph.get(n)) {
                    edges.add(e);
                    assert (e != null);
                }
                assert (edges.size() == graph.get(n).size());
            }
        }
    }
}
