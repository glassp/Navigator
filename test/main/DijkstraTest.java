package main;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class DijkstraTest {
// Are edges with weight 0 supported? maybe test that.

//	@BeforeAll
//	public static void prepareGraph() {
//			}

	@Test
    public void runDijkstra() {
		Graph graph = new Graph(7, 7);
		
		graph.insertEdge(0, 1, 6);
		graph.insertEdge(0, 4, 3);
		graph.insertEdge(0, 3, 8);
		graph.insertEdge(1, 3, 1);
		graph.insertEdge(2, 3, 9);
		graph.insertEdge(4, 1, 0);
		graph.insertEdge(5, 6, 1);
		
		Dijkstra dijkstra = new Dijkstra(graph, 0)

        dijkstra.start();
		
		System.out.println("Entfernungen:");
		System.out.println("Knoten\tEntfernung von Knoten 0");
		
		for (int i = 0; i < 7; i++) {
			System.out.println(i + "\t" + graph.getDistance(i));
			
		}
        
    }
}
