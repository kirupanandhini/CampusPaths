package graph.junitTests;

import graph.Graph;
import org.junit.Test;
import static org.junit.Assert.*;

import java.util.Arrays;

/**
 * GraphTest is a blackbox test of the Graph class.
 */

public class GraphTest {

    /**
     * Fields representing different empty graphs
     */
    Graph<String, String> empty = new Graph<>();
    Graph<String, String> G1 = new Graph<>();
    Graph<String, String> G = new Graph<>();
    Graph<String, String> M = new Graph<>();



    /**
     * Fields representing nodes
     */
    Graph.Node<String> one = new Graph.Node<>("1");
    Graph.Node<String> two = new Graph.Node<>("2");
    Graph.Node<String> three = new Graph.Node<>("3");



    /**
     * Test creating an empty Graph
     */

    @Test
    public void testCreationEmptyGraph() {
        assertEquals(Arrays.asList(),
                empty.listNodes());
    }


    /**
     * Testing the containsNode method for graphs with just nodes
     */
    @Test
    public void testContainsNode() {
        M.insertNode(one);
        assertTrue(M.containsNode(one));

        G.insertNode(one);
        G.insertNode(two);
        assertTrue(G.containsNode(one));
        assertTrue(G.containsNode(two));

        G1.insertNode(one);
        G1.insertNode(two);
        G1.insertNode(three);
        assertTrue(G1.containsNode(one));
        assertTrue(G1.containsNode(three));
    }

    /**
     * Testing the containsNode method returns false for graphs that do not contain a node
     */
    @Test
    public void doesNotContainNode() {
        assertFalse(empty.containsNode(one));

        G.insertNode(one);
        G.insertNode(two);
        assertFalse(G.containsNode(three));
    }

    /**
     * Testing the containsEdge method for graphs with at least one edge
     */
    @Test
    public void testContainsEdge() {
        M.insertNode(one);
        M.insertNode(two);
        M.insertEdge(one, two, "E");
        assertTrue(M.containsEdge(one, two, "E"));

        G1.insertNode(one);
        G1.insertNode(two);
        G1.insertNode(three);
        G1.insertEdge(one, two, "E");
        G1.insertEdge(two, three, "E2");
        assertTrue(G1.containsEdge(one, two, "E"));
        assertTrue(G1.containsEdge(one, two, "E"));
    }

    /**
     * Testing the containsEdge method returns false for graphs that do not contain an edge
     */
    @Test
    public void doesNotContainEdge() {
        assertFalse(empty.containsEdge(one, two, "E"));
        assertFalse(empty.containsEdge(one, two, "E"));

        G.insertNode(one);
        G.insertNode(two);
        assertFalse(G.containsEdge(one, two, "E"));
    }

    /**
     * Testing that the listEdges method throws an IllegalArgumentException when
     * a node that does not exist in a graph is taken in as a parameter.
     */
    @Test(expected=IllegalArgumentException.class)
    public void testListEdges() {
        G.listEdges(one);
    }



    /**
     * Testing that the insertEdge method throws an IllegalArgumentException when
     * an edge that does not exist in a graph is taken in as a parameter.
     */
    @Test(expected=IllegalArgumentException.class)
    public void testInsertEdge() {
        M.insertNode(one);
        M.insertNode(one);
        M.insertEdge(one, two, "E1");
    }

    /**
     * Testing that the insertEdge method throws a NullPointerException when
     * parentNode/childNode is null
     */
    @Test(expected=NullPointerException.class)
    public void testInsertEdgeNull() {
        M.insertNode(one);
        M.insertEdge(one, null, "E1");
    }

    /**
     * Testing that the containsEdge method throws a NullPointerException when
     * parentNode/childNode is null
     */
    @Test(expected=NullPointerException.class)
    public void testContainsEdgeNull() {
        M.insertNode(one);
        M.containsEdge(one, null, "E1");
    }

}
