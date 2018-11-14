package main;

import java.util.stream.IntStream;

/**
 * A Min Heap to manage priorities of nodes.  
 * Assumes that upon creation, all distances of nodes in the graph are positive infinity, except for one starting node with distance 0.
 * 
 * Lowest distance in graph instance means highest priority.
 * 
 */
public class NodeHeap {

	/**
	 * Graph instance the nodes of which are represented in the heap.
	 */
	private Graph graph;
	
	/**
	 * Contains node indices from graph. Represents the heap, so element 0's node has lowest distace of all.
	 */
	private int graphNodes[];
	
	/**
	 * Maximum index used in graphNodes[]. Elements beyond are not considered part of the heap anymore.
	 */
	private int maxIndex;
	
	// min heap: parent's distance has to be less than or equal to any child's
	
	
	public NodeHeap(Graph graph, int startingNode) {
		
		this.graph = graph;
		this.graphNodes = IntStream.rangeClosed(0, graph.getNodeList().length-1).toArray();
		
		this.maxIndex = graphNodes.length - 1;
		
		// Heap-Eigenschaft herstellen: wohl alle nodes durchgehen unten nach oben (leafs ignorieren, also nur erste maxIndex/2 ansehen,), jeweils pr�fen ob Eigenschaft gilt.
		// hier zu beginn nicht n�tig.
		
//		this.nodesArray = new int[graph.getNodeList().length];
//		Arrays.fill(nodesArray, Integer.MAX_VALUE);
//		nodesArray[0] = 0;
		
		graphNodes[0] = startingNode;
		graphNodes[startingNode] = 0; 
	}
	
	
	
	/**
	 * Decreases the distance of a node in the original graph, then restores heap condition if necessary.
	 * 
	 * @param node	Index of node that gets new distance.
	 * @param newDistance	Will be written in graph object.
	 */
	public void decreaseDistance(int node, double newDistance) {
		graph.setDistance(graphNodes[node], newDistance);
		
		siftUp(node, newDistance);
	}
	
	
	/**
	 * If necessary to maintain the heap property, sifts down a node.
	 * @param node will sift down if distance if greater than any child's
	 * @return true, if sift was necessary.
	 */
	private boolean siftDown(int node) {
		return siftDown(node, graph.getDistance(graphNodes[node]));
	}
	/**
	 * If necessary to maintain the heap property, sifts down a node.
	 * @param node will sift down if distance if greater than any child's
	 * @param distanceOfNode (optionally) the current distance for this node.
	 * @return true, if sift was necessary.
	 */
	private boolean siftDown(int node, double distanceOfNode) {
		int minChild = getLeftChild(node);
		
		if (minChild < 0) 
			return false;	// no children exist at all. Can't sift down.
		
		double minChildDist = graph.getDistance(graphNodes[minChild]);
			
		int tempR = getRightChild(node);
		if (tempR > 0) {
			// Determine which child has smaller distance
			double tempRDist = graph.getDistance(graphNodes[tempR]);
			
			if ( minChildDist > tempRDist) {
				minChild = tempR;
				minChildDist = tempRDist;
			}
		}	
		
		
		if (graph.getDistance(graphNodes[node]) > minChildDist) {
			swap(node, minChild);
			siftDown(minChild);
			
			return true;
		}
		
		
	return false;	
	}
	
	
	
	/**
	 * Sift up a node, if necessary to acquire heap condition.
	 * @param node will sift up if distance is less than its parent's.
	 * @return true, if sift was necessary.
	 */
	private boolean siftUp(int node) {
		return siftUp(node, graph.getDistance(node));
	}
	
	
	/**
	 * Sift up a node, if necessary to acquire heap condition.
	 * 
	 * @param node	will sift up if distance is less than its parent's.
	 * @param distanceOfNode	(optionally) the current distance for this node.
	 * @return true, if sift was necessary.
	 */
	private boolean siftUp(int node, double distanceOfNode) {
		int parent = getParent(node);
		
		if (graph.getDistance(parent) > distanceOfNode) {
			swap(node, parent);
			
			//Recursion - ending at root or earlier, since getParent(0) = 0
			siftUp(parent);
			return true;
		}
		
		return false;
	}
	
	/**
	 * Returns the index of the parent node in this heap.
	 * Will return 0, if the node's index is 0, as the root node has no parent.
	 * 
	 * @param node - the parent of which will be returned.
	 * @return index of parent, or 0 for root node
	 */
	private int getParent(int node) {
		if (node == 0) {
			return 0;
		}
		return (node / 2) - (1/2);
		
//		int temp = node;
//		
//		if (temp % 2 == 1) {
//			temp++;
//		}
//		int parent = temp / 2 - 1;
	}
	/**
	 * Returns the index of the node's left child, if it exists.
	 * Returns -1 if not.
	 * @param node 
	 */
	private int getLeftChild(int node) {
		int temp = 2*(node+1) - 1;
		if (temp <= maxIndex) {
			return temp;
		}
		return -1;
	}
	/**
	 * Returns the index of the node's right child, if it exists.
	 * Returns -1 if not.
	 * @param node 
	 */
	private int getRightChild(int node) {
		int temp = 2*(node+1);
		if (temp <= maxIndex) {
			return temp;
		}
		return -1;
	}
	
	
	/**
	 * swaps two nodes in the heap.
	 * Does not maintain heap property!
	 * 
	 * @param node1
	 * @param node2
	 */
	private void swap(int node1, int node2) {
		int temp = graphNodes[node1];
		graphNodes[node1] = graphNodes[node2];
		graphNodes[node2] = temp;
	}
	
	/**
	 * removes and returns the root element and determines a new one. Does preserve heap property.
	 * Returns -1 if no elements are on the heap (If so, maxIndex will be -1 as well).
	 * @return Node-Number in graph or -1 
	 */
	private int removeRoot() {
		if (maxIndex <= 0) {
			maxIndex = -1;
			return -1;
		}
		else {
			swap(0, maxIndex--);
			siftDown(0);
			return graphNodes[maxIndex+1];

		}
	}
	
	/**
	 * Returns the highest priority (= lowest distance) node from the graph, also removing it from this heap.
	 * -1 if no nodes exist in the heap.
	 * 
	 * @return Node number in graph or -1 
	 */
	public int getAndRemoveNext() {
		return removeRoot();
	}
	
}
