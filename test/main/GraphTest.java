package main;

import static org.junit.Assert.*;

public class GraphTest {


    @org.junit.Test
    public void addEdge() {
        try {
            Graph graph = new Graph(3, 3);
            assertFalse(graph.hasOutgoingEdges(0));
            graph.addEdge(0, 1, 1);
            assertTrue(graph.hasOutgoingEdges(0));
            assertEquals(0, graph.getEdge(0, 1));
            graph.addEdge(1, 2, 1);
            graph.addEdge(0, 2, 1);
            assertEquals(1, graph.getEdge(0, 2));
            assertEquals(2, graph.getEdge(1, 2));
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @org.junit.Test
    public void hasOutgoingEdges() {
    }

    @org.junit.Test
    public void getOffset() {
    }

    @org.junit.Test
    public void setOffset() {
    }

    @org.junit.Test
    public void getEdge() {
    }

    @org.junit.Test
    public void nextNode() {
    }

    @org.junit.Test
    public void countOutgoingEdges() {
    }

    @org.junit.Test
    public void getWeight() {
    }

    @org.junit.Test
    public void getDistance() {
    }

    @org.junit.Test
    public void setDistance() {
    }

    @org.junit.Test
    public void getNodeList() {
    }
}
