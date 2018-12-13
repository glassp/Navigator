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
	 * Contains node indices from graph. Represents the heap, so item 0's node has lowest distance of all.
	 */
	private int[] heapNodes;
	
	/**
	 * Maximum index used in graphNodes array. Items beyond are not considered part of the heap anymore.
	 */
	private int maxIndex;

	/**
	 * Entry n contains the index under which node n from the graph is currently found in heap's array heapNodes[].
	 */
	private int[] nodeLocation;
	
	// min heap: parent's distance has to be less than or equal to any child's
	
	
	public NodeHeap(Graph graph, int startingNode) {
		
		this.graph = graph;
		this.heapNodes = IntStream.rangeClosed(0, graph.getNodeList().length-1).toArray();
		
		this.maxIndex = heapNodes.length - 1;
		
//		this.nodesArray = new int[graph.getNodeList().length];
//		Arrays.fill(nodesArray, Integer.MAX_VALUE);
//		nodesArray[0] = 0;
	
		
		// no need to iterate through first length/2 items for making sure heap property is achieved,
		// since all but one item's node distance are set to only INFINITY
		
		heapNodes[0] = startingNode;
		heapNodes[startingNode] = 0;
		//swap starting node with node that has index 0 in graph. May be unnecessary if identical.
		
		nodeLocation = heapNodes.clone();
		// In the beginning, all nodes are saved at an index equal to their number in the graph, except for the two swapped ones.
		// Further tracking of locations is done within the swap method.
	}
	
	
	
	/**
	 * Decreases the distance of a node in the original graph, then restores heap condition if necessary.
	 * 
	 * @param node	Index of node from the graph that gets new distance.
	 * @param newDistance	Will be written in graph object.
	 */
	public void decreaseDistance(int node, double newDistance) {
		graph.setDistance(heapNodes[node], newDistance);
		
		// Find heap item that belongs to the node.
		node = nodeLocation[node];
		
		siftUp(node, newDistance);
	}
	
	
	/**
	 * If necessary to maintain the heap property, sifts down a an item.
	 * @param heapItem will sift down if associated node distance is greater than any child's
	 * @return true, if sift was necessary.
	 */
	private boolean siftDown(int heapItem) 							throws IllegalArgumentException {
		return siftDown(heapItem, graph.getDistance(heapNodes[heapItem]));
	}
	/**
	 * If necessary to maintain the heap property, sifts down a an item.
	 * @param heapItem will sift down if the associated node distance is greater than any child's
	 * @param distanceOfNode (optionally) the current distance for this item's node.
	 * @return true, if sift was necessary.
	 */
	private boolean siftDown(int heapItem, double distanceOfNode) 	throws IllegalArgumentException {
		if (heapItem > maxIndex)
			throw new IllegalArgumentException("Heap item " + heapItem + " does not exist. Max index is " + maxIndex);
		
		int minChild = getLeftChild(heapItem);
		
		if (minChild < 0) 
			return false;	// no children exist at all. Can't sift down.
		
		double minChildDist = graph.getDistance(heapNodes[minChild]);
			
		int tempR = getRightChild(heapItem);
		if (tempR > 0) {
			// Determine which child has smaller distance
			double tempRDist = graph.getDistance(heapNodes[tempR]);
			
			if ( minChildDist > tempRDist) {
				minChild = tempR;
				minChildDist = tempRDist;
			}
		}	
		
		
		if (graph.getDistance(heapNodes[heapItem]) > minChildDist) {
			swap(heapItem, minChild);
			siftDown(minChild);
			
			return true;
		}
		
		
	return false;	
	}
	
	
	
	/**
	 * Sift up a node, if necessary to acquire heap condition.
	 * @param heapItem will sift up if associated node distance is less than its parent's.
	 * @return true, if sift was necessary.
	 */
	private boolean siftUp(int heapItem) {
		if (heapItem > maxIndex)
			throw new IllegalArgumentException("Node " + heapItem + " does not exist in heap. Max index is " + maxIndex);
		
		return siftUp(heapItem, graph.getDistance(heapNodes[heapItem]));
	}
	
	
	/**
	 * Sift up a node, if necessary to acquire heap condition.
	 * 
	 * @param heapItem	will sift up if associated node distance is less than its parent's.
	 * @param distanceOfNode	(optionally) the current distance for this item's node.
	 * @return true, if sift was necessary.
	 */
	private boolean siftUp(int heapItem, double distanceOfNode) {
		if (heapItem > maxIndex)
			throw new IllegalArgumentException("Node " + heapItem + " does not exist in heap. Max index is " + maxIndex);
		
		int parent = getParent(heapItem);
		
		if (graph.getDistance(parent) > distanceOfNode) {
			swap(heapItem, parent);
			
			//Recursion - ending at root or earlier, since getParent(0) = 0
			siftUp(parent);
			return true;
		}
		
		return false;
	}
	
	/**
	 * Returns the index of the parent item in this heap.
	 * Will return 0, if the item's index is 0, as the root has no parent.
	 * 
	 * @param heapItem - the parent of this item will be returned.
	 * @return index of parent, or 0 for root item
	 */
	private int getParent(int heapItem) {
		if (heapItem == 0) {
			return 0;
		}
		return (heapItem / 2) - (1/2);
		
//		int temp = heapItem;
//		
//		if (temp % 2 == 1) {
//			temp++;
//		}
//		int parent = temp / 2 - 1;
	}
	/**
	 * Returns the index of the item's left child, if it exists.
	 * Returns -1 if not.
	 * TODO: missing parameter description
	 * @param heapItem 
	 */
	private int getLeftChild(int heapItem) {
//		int temp = 2*(heapItem + 1) - 1;
		
		int temp = 2*heapItem + 1;
		if (temp <= maxIndex) {
			return temp;
		}
		return -1;
	}
	/**
	 * Returns the index of the item's right child, if it exists.
	 * Returns -1 if not.
	 * TODO: missing parameter description
	 * @param heapItem 
	 */
	private int getRightChild(int heapItem) {
		int temp = 2*heapItem + 2;
		if (temp <= maxIndex) {
			return temp;
		}
		return -1;
	}
	
	
	/**
	 * swaps two items in the heap.
	 * Does not maintain heap property!
	 * 
	 * also updates location info for the associated graph nodes in this.nodeLocation[] 
	 * 
	 * @param item1	index of first item
	 * @param item2	index of second item
	 */
	private void swap(int item1, int item2) {
		int temp = heapNodes[item1];
		
		nodeLocation[ temp ] 				= item2;	// item from position 'item1' is to be at 'item2'
		nodeLocation[ heapNodes[item2] ] 	= item1;	// vice versa
		
		heapNodes[item1] = heapNodes[item2];	
		heapNodes[item2] = temp;				
		
	}
	
	/**
	 * removes and returns the root item and determines a new one. Does preserve heap property.
	 * Returns -1 if no items are on the heap (If so, maxIndex will be -1 as well).
	 * @return Node number in graph or -1 
	 */
	private int removeRoot() {
		if (maxIndex <= 0) {
			maxIndex = -1;
			return -1;
		}
		else {
			swap(0, maxIndex--);
			siftDown(0);
			return heapNodes[maxIndex+1];

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