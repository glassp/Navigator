package main;

import static org.junit.Assert.assertTrue;

import org.junit.*;

public class DijkstraTest {
// Are edges with weight 0 supported? maybe test that.

//	@BeforeAll
//	public static void prepareGraph() {
//			}

	@Test
    public void runDijkstra() {
		Graph graph = new Graph(7, 7);
		
		graph.insertEdge(0, 1, 6); // edgeID 0
		graph.insertEdge(0, 4, 3);
		graph.insertEdge(0, 3, 8); // edgeID 4
		graph.insertEdge(1, 3, 1);
		graph.insertEdge(2, 3, 9); // edgeID 8
		graph.insertEdge(4, 1, 2);
		graph.insertEdge(5, 6, 1); // edgeID 12

//		assertTrue(graph.getDistance(graph.getEdge(0, 3)) == 8);
		
		Dijkstra dijkstra = new Dijkstra(graph, 0);

		System.out.println("Entfernungen:");
		System.out.println("Knoten\tEntfernung von Knoten 0");
		
		for (int i = 0; i < 7; i++) {
			System.out.println(i + "\t" + graph.getDistance(i));
		}
		
        dijkstra.start();
		
		System.out.println("Entfernungen:");
		System.out.println("Knoten\tEntfernung von Knoten 0");
		
		for (int j = 0; j < 7; j++) {
			System.out.println(j + "\t" + graph.getDistance(j));
			
		}
		
        
    }
}
