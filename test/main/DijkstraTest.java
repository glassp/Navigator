package main;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.*;

public class DijkstraTest {
// Are edges with weight 0 supported? maybe test that.

//	@BeforeAll
//	public static void prepareGraph() {
//			}

	@Test
    public void runDijkstra() {
		Graph graph = new Graph(7, 14);
		
		graph.insertEdge(0, 1, 6); // edgeID 0
		graph.insertEdge(0, 4, 3);
		graph.insertEdge(0, 3, 8);
		
		graph.insertEdge(1, 0, 6); // edgeID 3
		graph.insertEdge(1, 3, 1);
		graph.insertEdge(1, 4, 2);

		graph.insertEdge(2, 3, 9); // edgeID 6
		
		graph.insertEdge(3, 0, 8); // edgeID 7
		graph.insertEdge(3, 1, 1);
		graph.insertEdge(3, 2, 9);
		
		graph.insertEdge(4, 0, 3); // edgeID 10
		graph.insertEdge(4, 1, 2);
		
		graph.insertEdge(5, 6, 1); // edgeID 12
		graph.insertEdge(6, 5, 1);
		
		
		
		
		
		
		
		
		System.out.println("Graph has maximum of " + graph.getMaxEdgesCount() + " edges");
		System.out.println("Graph has " + graph.getNodesCount() + " nodes");

		assertEquals(8, graph.getWeight(graph.getEdge(0, 3)));
		// Test edge just cause
		
		Dijkstra dijkstra = new Dijkstra(graph, 0);

//		System.out.println("Entfernungen:");
//		System.out.println("Knoten\tEntfernung von Knoten 0");
//		
//		for (int i = 0; i < graph.getNodesCount(); i++) {
//			System.out.println(i + "\t" + graph.getDistance(i));
//		}
		
        dijkstra.start();

        //For manual check:
//		System.out.println("Entfernungen:");
//		System.out.println("Knoten\tEntfernung von Knoten 0");
		
//		for (int i = 0; i < graph.getNodesCount(); i++) {
//			System.out.println(i + "\t" + graph.getDistance(i));
//		}
        
        //Auto check:
		assertEquals(0, graph.getDistance(0));
		assertEquals(5, graph.getDistance(1));
		assertEquals(15, graph.getDistance(2));
		assertEquals(6, graph.getDistance(3));
		assertEquals(3, graph.getDistance(4));
		assertEquals(Double.POSITIVE_INFINITY, graph.getDistance(5));
		assertEquals(Double.POSITIVE_INFINITY, graph.getDistance(6));
        
    }
}
