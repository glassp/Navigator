package main;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class NodeHeapTest {
	Graph graph;
	NodeHeap heap;
	
	@BeforeEach
	void setUpBeforeClass() throws Exception {
		System.out.println("\n--Set up graph... \n--");
		graph = new Graph(7, 14);
		
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
		
		heap = new NodeHeap(graph, 4);
	}


	@Test
	void testDecreaseDistance() {
		System.out.println(graph.getNodesCount() + " nodes in the graph");
		System.out.println("start test for decreaseDistance");
		heap.decreaseDistance(4, 0.51);
		heap.decreaseDistance(1, 0.5);
		assertEquals(1, heap.getAndRemoveNext());
		assertEquals(4, heap.getAndRemoveNext());
		heap.decreaseDistance(2, 5);
		heap.decreaseDistance(3, 3);
		heap.decreaseDistance(2, 4.1);
		heap.decreaseDistance(0, 4);
		heap.decreaseDistance(6, 0.0);
		assertEquals(6, heap.getAndRemoveNext());
		assertEquals(3, heap.getAndRemoveNext());
		assertEquals(0.0, graph.getDistance(6));
		assertEquals(4.1, graph.getDistance(2));
	}

	@Test
	void testGetAndRemoveNext() {
		System.out.println("start Test for getAndRemoveNext");
		
//		System.out.println("next nodes on heap:");
		for (int i = 0; i < 9; i++) {
//			System.out.println(heap.getAndRemoveNext() );
			heap.getAndRemoveNext();
		}
		
		assertEquals(-1, heap.getAndRemoveNext());
	}

}
