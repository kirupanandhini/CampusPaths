package graph.junitTests;

import graph.Graph;
import org.junit.Test;
import static org.junit.Assert.*;

public class NodeTest {
    /**
     * fields that represent Nodes
     */

    Graph.Node<String> a = new Graph.Node<String>("a");
    Graph.Node<String> b = new Graph.Node<>("b");

    /**
     * tests whether the getNodeLabel method returns the labels of nodes we created
     */
    @Test
    public void testGetNodeLabel() {
        assertEquals("a", a.getNodeLabel());
        assertEquals("b", b.getNodeLabel());

    }

    /**
     * tests whether two of the same nodes are equal
     */
    @Test
    public void testNodeEquals() {
        assertEquals(a, new Graph.Node<>("a"));
        assertEquals(b, new Graph.Node<>("b"));

    }

    /**
     * tests whether two of the same nodes are not equal
     */
    @Test
    public void testNodeNotEquals() {
        assertNotEquals(b, new Graph.Node<>("a"));
        assertNotEquals(a, new Graph.Node<>("b"));

    }

    /**
     * tests whether two of the same nodes share a hashcode value;
     */
    @Test
    public void testNodeHashCodeEquals() {
        assertEquals(a.hashCode(), (new Graph.Node<>("a")).hashCode());
        assertEquals(b.hashCode(), (new Graph.Node<>("b")).hashCode());

    }
}
