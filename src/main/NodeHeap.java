package main;

import java.util.Arrays;



/**
 * A Min Heap to manage priorities of nodes.
 * Assumes that upon creation, all distances of nodes in the graph are positive infinity, except for one starting node with distance 0.
 * <p>
 * Lowest distance in graph instance means highest priority.
 */
public class NodeHeap extends CLILogger {

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
    private Integer[] nodeLocation;


    /**
     * Construct a NodeHeap instance. Starting node is automatically added to heap,
     * others have to be added with addNode() while having infinite distance, e.g. when found in Dijkstra's algorithm.
     *
     * @param graph        The graph to work with.
     * @param startingNode the starting node
     */
    public NodeHeap(Graph graph, int startingNode) {
        this.graph = graph;
//        this.heapNodes = IntStream.rangeClosed(0, graph.getNodesCount() - 1).toArray(); //Now, start with empty array of correct size.
        this.heapNodes = new int[graph.getNodesCount()];

        // If items were even on the heap:
        // no need to iterate through first length/2 items for making sure heap property is achieved,
        // since all but one item's node distance are set to only INFINITY

        heapNodes[0] = startingNode;
//        heapNodes[startingNode] = 0;
//        //swap starting node with node that has index 0 in graph. May be unnecessary if identical.

//      this.maxIndex = heapNodes.length - 1;
      this.maxIndex = 0;
        
        nodeLocation = new Integer[graph.getNodesCount()]; 	//Integer instead of int, so it starts as null
        nodeLocation[startingNode] = 0; 
//        nodeLocation = new int[graph.getNodesCount()];
//        Arrays.fill(nodeLocation, -1);
//        nodeLocation[startingNode] = 0;
        // In the beginning, all nodes are saved at an index equal to their number in the graph, except for the two swapped ones.
        // Further tracking of locations is done within the swap method.
    }

    public void resetHeap(int startingNode) {
    	Arrays.fill(heapNodes, 0);
    	heapNodes[0] = startingNode;
    	this.maxIndex = 0;
    	Arrays.fill(nodeLocation, null);
    	nodeLocation[startingNode] = 0;
    }

    /**
     * Decreases the distance of a node in the original graph, then restores heap condition if necessary.
     *
     * @param node        Index of node from the graph that gets new distance.
     * @param newDistance Will be written in graph object.
     */
    public void decreaseDistance(int node, double newDistance) {
        graph.setDistance(node, newDistance);

        // Find heap item that belongs to the node.
        node = nodeLocation[node];
//		System.out.print(heapNodes[node] + " is at position " + node);

        siftUp(node, newDistance);
    }

    
    /**
     * Adds the given node to the heap, unless it already exists.
     * Distance value in the graph must be Double.POSITIVE_INFINITY when using this method - use decreaseDistance() to set a new distance later.
     * <p>
     * The heap can only contain as many nodes as specified upon construction, which must be numbered between 0 and the max amount minus 1
     * 
     * @param node Index of node in the graph.
     * @throws IllegalArgumentException if node ID is not between 0 and the amount of specified nodes minus 1.
     */
    public void addNode(int node) throws IllegalArgumentException {
    	if (node >= heapNodes.length || node < 0)
    		throw new IllegalArgumentException("node " + node + " is not between 0 and " + (heapNodes.length-1) );
    	
    	if (!contains(node)) {
			heapNodes[++maxIndex] = node;
			nodeLocation[node] = maxIndex;
		}
    	
    }
    
    /**
     * checks whether a node has been added to the heap previously.
     * 
     * @param node Index of node in the graph.
     * @return True, if node exists in heap.
     */
    public boolean contains(int node) {
    	if (node >= heapNodes.length || node < 0)
    		return false;
        return nodeLocation[node] != null;
    }

    /**
     * If necessary to maintain the heap property, sifts down an item.
     *
     * @param heapItem will sift down if associated node distance is greater than any child's
     * @return true, if sift was necessary.
     */
    private boolean siftDown(int heapItem) throws IllegalArgumentException {
        return siftDown(heapItem, graph.getDistance(heapNodes[heapItem]));
    }

    /**
     * If necessary to maintain the heap property, sifts down a an item.
     *
     * @param heapItem       will sift down if the associated node distance is greater than any child's
     * @param distanceOfNode (optionally) the current distance for this item's node.
     * @return true, if sift was necessary.
     */
    private boolean siftDown(int heapItem, double distanceOfNode) throws IllegalArgumentException {
        if (heapItem > maxIndex)
            throw new IllegalArgumentException("Heap item " + heapItem + " does not exist. Max index is " + maxIndex);

        int minChild = getLeftChild(heapItem);

        if (minChild < 0)
            return false;    // no children exist at all. Can't sift down.

        double minChildDist = graph.getDistance(heapNodes[minChild]);

        int tempR = getRightChild(heapItem);
        if (tempR > 0) {
            // Determine which child has smaller distance
            double tempRDist = graph.getDistance(heapNodes[tempR]);

            if (minChildDist > tempRDist) {
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
     *
     * @param heapItem will sift up if associated node distance is less than its parent's.
     * @return true, if sift was necessary.
     */
    private boolean siftUp(int heapItem) {
        if (heapItem > maxIndex)
            throw new IllegalArgumentException("Heap item " + heapItem + " does not exist. Max index is " + maxIndex);

        return siftUp(heapItem, graph.getDistance(heapNodes[heapItem]));
    }


    /**
     * Sift up a node, if necessary to acquire heap condition.
     *
     * @param heapItem       will sift up if associated node distance is less than its parent's.
     * @param distanceOfNode (optionally) the current distance for this item's node.
     * @return true, if sift was necessary.
     */
    private boolean siftUp(int heapItem, double distanceOfNode) {
        if (heapItem > maxIndex)
            throw new IllegalArgumentException("Heap item " + heapItem + " does not exist. Max index is " + maxIndex);

        int parent = getParent(heapItem);

        if (graph.getDistance(heapNodes[parent]) > distanceOfNode) {
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

        int temp = heapItem;

        if (temp % 2 == 1) {
            temp++;
        }
        return temp / 2 - 1;
    }

    /**
     * Returns the index of the item's left child, if it exists.
     * Returns -1 if not.
     *
     * @param heapItem index of item on the heap
     */
    private int getLeftChild(int heapItem) {
        int temp = 2 * heapItem + 1;
        if (temp <= maxIndex) {
            return temp;
        }
        return -1;
    }

    /**
     * Returns the index of the item's right child, if it exists.
     * Returns -1 if not.
     *
     * @param heapItem index of item on the heap
     */
    private int getRightChild(int heapItem) {
        int temp = 2 * heapItem + 2;
        if (temp <= maxIndex) {
            return temp;
        }
        return -1;
    }


    /**
     * @param node a node from the graph
     * @return current location of the node in the heap. -1 if out of bounds
     */
    public int getPositionOf(int node) {
        if (node < 0 || node >= nodeLocation.length) {
            return -1;
        }
        return nodeLocation[node];
    }


    /**
     * swaps two items in the heap.
     * Does not maintain heap property!
     * <p>
     * also updates location info for the associated graph nodes in this.nodeLocation[]
     *
     * @param item1 index of first item
     * @param item2 index of second item
     */
    private void swap(int item1, int item2) {
        int temp = heapNodes[item1];
        
        nodeLocation[temp] = item2;    // item from position 'item1' is to be at 'item2'
        nodeLocation[heapNodes[item2]] = item1;    // vice versa

        heapNodes[item1] = heapNodes[item2];
        heapNodes[item2] = temp;
    }


    /**
     * @return a String containing the top 7 nodes in the heap separated by comma
     */
    public String getTopElementsPeek() {
        return heapNodes[0] + ", " + heapNodes[1] + ", " + heapNodes[2] + ", " + heapNodes[3] + ", " + heapNodes[4] + ", " + heapNodes[5] + ", " + heapNodes[6];
    }


    /**
     * removes and returns the root item and determines a new one. Does preserve heap property.
     * Returns -1 if no items are on the heap (If so, maxIndex will be -1 as well).
     *
     * @return Node number in graph or -1
     */
    private int removeRoot() {
        if (maxIndex < 0) {
            return -1;
        } else if (maxIndex == 0) {
            maxIndex--;
            return heapNodes[0];
        } else {
            swap(0, maxIndex--);

            siftDown(0);

            return heapNodes[maxIndex + 1];

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