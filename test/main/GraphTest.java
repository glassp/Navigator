package main;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GraphTest {

    @Test
    void runQuery() {
        //TODO
    }


//    @Test
//    void addEdge() {
//        try {
//            Graph graph = new Graph(3, 3);
//            assertFalse(graph.hasOutgoingEdges(0));
//            graph.addEdge(0, 1, 1);
//            assertTrue(graph.hasOutgoingEdges(0));
//            assertEquals(0, graph.getEdge(0, 1));
//            graph.addEdge(0, 2, 1);
//            graph.addEdge(1, 2, 1);
//            assertEquals(1, graph.getEdge(0, 2));
//            assertEquals(2, graph.getEdge(1, 2));
//        } catch (UnorderedGraphException e) {
//            fail("unordered graph exception");
//            e.printStackTrace();
//
//        }
//    }
//
//    @Test
//    void insertEdge() {
//        try {
//            Graph graph = new Graph(4, 3);
//            assertFalse(graph.hasOutgoingEdges(0));
//            graph.insertEdge(0, 1, 1);
//            assertTrue(graph.hasOutgoingEdges(0));
//            assertEquals(0, graph.getEdge(0, 1));
//            graph.insertEdge(1, 2, 1);
//            graph.insertEdge(0, 2, 1);
//            assertEquals(1, graph.getEdge(0, 2));
//            assertEquals(2, graph.getEdge(1, 2));
//        } catch (Exception e) {
//            fail("");
//        }
//    }

    @Test
    void hasOutgoingEdges() {
        try {
            Graph graph = new Graph(3, 3);
            assertFalse(graph.hasOutgoingEdges(0));
            graph.addEdge(0, 1, 1);
            assertTrue(graph.hasOutgoingEdges(0));
        } catch (Exception e) {
            fail("exception in hasOutgoing edges");
            e.printStackTrace();
        }
    }

    @Test
    public void getOffset() {
        try {
            Graph graph = new Graph(3, 3);
            assertEquals(-1, graph.getOffset(0));
            graph.addEdge(0, 1, 1);
            assertEquals(0, graph.getOffset(0));
        } catch (Exception e) {
            fail("exception in getOffset");
            e.printStackTrace();
        }
    }

    @Test
    public void setOffset() {
        try {
            Graph graph = new Graph(3, 3);
            assertEquals(-1, graph.getOffset(0));
            graph.setOffset(0, 0);
            assertEquals(0, graph.getOffset(0));
        } catch (Exception e) {
            fail("exception in setOffset");
            e.printStackTrace();
        }
    }

//    @Test
//    public void getEdge() {
//        try {
//            Graph graph = new Graph(3, 3);
//            assertEquals(-1, graph.getEdge(0, 1));
//            graph.addEdge(0, 1, 1);
//            assertEquals(0, graph.getEdge(0, 1));
//            graph.addEdge(0, 2, 1);
//            assertEquals(1, graph.getEdge(0, 2));
//        } catch (Exception e) {
//            fail("exception in getEdge");
//            e.printStackTrace();
//        }
//    }

    @Test
    public void nextNode() {
        try {
            Graph graph = new Graph(3, 3);
            assertEquals(-1, graph.nextNode(0));
            graph.addEdge(0, 1, 3);
            graph.addEdge(1, 2, 5);
            graph.addEdge(1, 0, 1);
            assertEquals(1, graph.nextNode(0));
            assertEquals(0, graph.nextNode(1));
        } catch (Exception e) {
            fail("exception in nextNode");
            e.printStackTrace();
        }
    }

    @Test
    public void countOutgoingEdges() {
        try {
            Graph graph = new Graph(3, 3);
            assertEquals(0, graph.countOutgoingEdges(0));
            graph.addEdge(0, 1, 1);
            graph.addEdge(0, 2, 2);
            assertEquals(2, graph.countOutgoingEdges(0));
        } catch (Exception e) {
            fail("exception");
            e.printStackTrace();
        }
    }

//    @Test
//    public void getWeight() {
//        try {
//            Graph graph = new Graph(3, 3);
//            assertEquals(-1, graph.getWeight(0, 1), 0.1);
//            graph.addEdge(0, 1, 4);
//            assertEquals(4, graph.getWeight(0, 1), 0.1);
//        } catch (Exception e) {
//            fail("exception");
//            e.printStackTrace();
//        }
//    }

    @Test
    public void getDistance() {
        //also tests functionality of setDistance
        try {
            Graph graph = new Graph(3, 3);
            assertEquals(Double.POSITIVE_INFINITY, graph.getDistance(1), 0.1);
            graph.setDistance(1, 1.5);
            assertEquals(1.5, graph.getDistance(1), 0.1);
        } catch (Exception e) {
            fail("exception");
            e.printStackTrace();
        }
    }


    @Test
    public void getNodeList() {
        int[] initial = {-1, -1, -1};
        int[] arr = {0, 2, -1};
        try {
            Graph graph = new Graph(3, 3);
            assertArrayEquals(initial, graph.getNodeList());
            graph.addEdge(0, 1, 1);
            graph.addEdge(0, 2, 2);
            graph.addEdge(1, 2, 3);
            assertArrayEquals(arr, graph.getNodeList());
        } catch (Exception e) {
            fail("exception");
            e.printStackTrace();

        }
    }


//    @Test
//    void getWeight1() {
//        try {
//            Graph graph = new Graph(3, 3);
//            assertEquals(-1, graph.getWeight(graph.getEdge(0, 1)), 0.1);
//            graph.addEdge(0, 1, 4);
//            assertEquals(4, graph.getWeight(graph.getEdge(0, 1)), 0.1);
//        } catch (Exception e) {
//            fail("exception");
//            e.printStackTrace();
//        }
//
//    }
}