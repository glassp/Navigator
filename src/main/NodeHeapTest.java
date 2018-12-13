package main;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class NodeHeapTest {

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
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
		
		NodeHeap heap = new NodeHeap(graph, 4);
	}


	@Test
	void testDecreaseDistance() {
		fail("Not yet implemented");
	}

	@Test
	void testGetAndRemoveNext() {
		fail("Not yet implemented");
	}

}
