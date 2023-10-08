package graph.junitTests;

import graph.Graph;
import org.junit.Test;
import static org.junit.Assert.*;

import java.util.Arrays;

public class EdgeTest {

    /**
     * fields that represent Nodes and Edges
     */

    Graph.Node<String> a = new Graph.Node<>("one");
    Graph.Node<String> b = new Graph.Node<>("two");
    Graph.Edge<String, String> e1 = new Graph.Edge<>(a, b, "e1");


    /**
     * tests whether the getParent method returns the parent node of an edge we created
     */
    @Test
    public void testGetParent() {
        assertEquals(a, e1.getParent());
    }

    /**
     * tests whether the getChild method returns the child node of an edge we created
     */
    @Test
    public void testGetChild() {
        assertEquals(b, e1.getChild());
    }

    /**
     * tests whether the getEdgeLabel method returns the label of an edge we created
     */
    @Test
    public void testGetEdgeLabel() {
        assertEquals("e1", e1.getEdgeLabel());
    }

    /**
     * tests whether two of the same nodes are equal
     */
    @Test
    public void testEdgeEquals() {
        assertEquals(e1, new Graph.Edge<String, String>(a, b, "e1"));
        assertTrue(e1.equals(new Graph.Edge<>(a, b, "e1")));
    }

    /**
     * tests whether two different nodes are not equal
     */
    @Test
    public void testEdgeNotEquals() {
        assertNotEquals(e1, new Graph.Edge<>(a, b, "e2"));
        assertFalse(e1.equals(new Graph.Edge<>(a, a, "e1")));
    }


    /**
     * tests whether two of the same nodes have the same hashCode
     */
    @Test
    public void testHashCode() {
        assertEquals(e1.hashCode(), (new Graph.Edge<String, String>(a, b, "e1")).hashCode());
    }
}
