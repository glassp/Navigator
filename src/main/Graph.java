package main;

import java.util.Arrays;


/**
 * The Graph class
 */
public class Graph {

    /**
     * stores the destination of a edge
     */
    private int[] edges;
    /**
     * stores the offset
     */
    private int[] offset;
    /**
     * the first unused index
     */
    private int current;
    /**
     * stores the weight of a edge
     */
    private double[] weight;
    /**
     * stores the latitude of a node
     */
    private double[] latitude;
    /**
     * stores the longitude of a node
     */
    private double[] longitude;
    /**
     * stores the distance to a node
     */
    private double[] distance;
    
    /**
     * stores any node's preceding node on the ideal path from Dijkstra's starting node to that node, if Dijkstra's algorithm has been run.
     * Values will be -1 if there is no path or if Dijkstra has not run yet.
     */
    private int[] predecessor;

    /**
     * Constructor
     *
     * @param nodes number of nodes that are used in the graph
     * @param edges number of edges that are used in the graph
     */
    public Graph(int nodes, int edges) {
    	this.offset = new int[nodes];
    	this.predecessor = new int[edges];
    	this.distance = new double[nodes];
    	
    	this.edges = new int[edges];
        this.weight = new double[edges];
        
        this.latitude = new double[nodes];
        this.longitude = new double[nodes];
        
        this.current = 0;
        
        //initializes all values in arrays with their default value, if there is one
        Arrays.fill(offset, -1);
        Arrays.fill(predecessor, -1);
        Arrays.fill(distance, Double.POSITIVE_INFINITY);

    }
    
    

    /**
     * adds a new Edge to the Graph
     *
     * @param start  the start node
     * @param dest   the destination node
     * @param weight the weight
     * @throws UnorderedGraphException A special Exception which tells caller to try insert method or to die on error if unchecked
     */
    void addEdge(int start, int dest, double weight) throws UnorderedGraphException {
        //edges are added in right order just append in array
        if (hasOutgoingEdges(start + 1))
            throw new UnorderedGraphException("The next Node has already created Edges please ty to use insertEdge(...)");
        if (this.current >= edges.length) increase(edges);
        if (!hasOutgoingEdges(start)) {
            this.setOffset(start, this.current);
        }
        this.edges[this.current] = dest;
        this.weight[getEdge(start, dest)] = weight;
        this.current++;
    }

    private void increase(double[] arr) {
        double[] array = new double[arr.length + 2];
        //copies array into other array
        System.arraycopy(arr, 0, array, 0, arr.length);
        if (arr == this.weight) this.weight = array;
        else if (arr == this.distance) this.distance = array;
        else if (arr == this.latitude) this.latitude = array;
        else if (arr == this.longitude) this.longitude = array;
        else {
            //just ignore it
        }

    }

    private void increase(int[] arr) {
        int[] array = new int[arr.length + 2];
        for (int i = 0; i < arr.length; i++) {
            array[i] = arr[i];
        }
        if (arr == this.edges) this.edges = array;
        else if (arr == this.offset) this.offset = array;
        else {
            //just ignore it
        }
    }

    /**
     * adds a new Edge to the Graph and moves indices if needed
     *
     * @param start  the start node
     *               addEdge(start, dest, weight);
     * @param dest   the destination node
     * @param weight the weight
     * @param skip   if true skips the test and forces indices to be moved
     */
    public void insertEdge(int start, int dest, double weight, boolean skip) {
        //TODO: implement
        //move offset
        //move edges
        //insert edge in freed index
        //increase array if needed

        if (getOffset(start + 1) == -1 || !skip) {
            try {
            } catch (Exception e) {
                insertEdge(start, dest, weight, true);
            }
        } else {
            int index = getOffset(start + 1);
            //move edges top Right by 1
            if (edges.length <= current + 1) increase(edges);
            for (int i = this.current; i >= index; i--) {

                edges[i + 1] = edges[i];
            }
            edges[index] = dest;
            this.current++;
            //increase offset
            for (int i = start + 1; i < offset.length; i++) {
                if (getOffset(i) >= 0) setOffset(i, getOffset(i) + 1);
            }
        }
    }

    /**
     * adds a new Edge to the Graph and moves indices if needed
     *
     * @param start  the start node
     * @param dest   the destination node
     * @param weight the weight
     */
    public void insertEdge(int start, int dest, double weight) {
        insertEdge(start, dest, weight, false);
    }

    /**
     * Checks if a node has a outgoing edge
     *
     * @param node the node to be checked
     * @return true if has outgoing edge else false
     */
    boolean hasOutgoingEdges(int node) {
        return this.offset[node] != -1;
    }


    /**
     * Returns the Offset of a node
     *
     * @param node the node
     * @return the offset or -1 if it does not exist
     */
    int getOffset(int node) {
        if (!this.hasOutgoingEdges(node)) return -1;		//TODO: This line has no real effect and can be removed (since offset[node] will be -1 if there are no edges). Up for review.
        return this.offset[node];
    }


    /**
     * Sets the offset of a node
     *
     * @param node   the node
     * @param offset the offset
     */
    void setOffset(int node, int offset) {
        this.offset[node] = offset;
    }


    /**
     * Returns the index of the edge
     *
     * @param start the start node
     * @param dest  the destination node
     * @return the offset of the edge or -1 if it does not exist
     */
    public int getEdge(int start, int dest) {
        //gets all edges starting from start
        int from = this.getOffset(start);
        int to = this.getOffset(start + 1);
        //if no such edges return
        if (from == -1) return -1;
        if (to == -1) to = this.edges.length;
        //for all those edges search if destination is same
        for (int i = from; i < to; i++) {
            //return edge index if found
            if (this.edges[i] == dest) return i;
        }
        //if nothing found return
        return -1;
    }


    /**
     * Returns the node which is nearest to [node]
     *
     * @param node the node
     * @return the next node or -1 if it does not exist
     */
    int nextNode(int node) {
        //running infinit time
        double minVal = Double.POSITIVE_INFINITY;
        int minIndex = -1;
        int offset = this.getOffset(node);
        if (offset == -1) return -1;
        int elem = this.countOutgoingEdges(node);
        if (elem < 1) return -1;
        for (int i = 0; i < elem; i++) {
            double var = this.getWeight(node, this.edges[offset + i]);
            if (var < minVal) {
                minVal = var;
                minIndex = i;
            }
        }
        return this.edges[offset + minIndex];
    }


    /**
     * Returns the number of outgoing edges
     *
     * @param node the node
     * @return number of outgoing edges or 0 if none found
     */
    int countOutgoingEdges(int node) {
        if (!this.hasOutgoingEdges(node)) return 0;
        if (this.getOffset(node + 1) == -1)
            return this.current - this.getOffset(node);
        else
            return this.getOffset(node + 1) - this.getOffset(node);
    }


    /**
     * Returns the weight of a Edge
     *
     * @param start the start node
     * @param dest  the destination node
     * @return the weight of the edge or -1 if it does not exist
     */
    double getWeight(int start, int dest) {
        if (!this.hasOutgoingEdges(start)) return -1;
        int edge = this.getEdge(start, dest);
        if (edge < 0) return -1;
        return this.weight[edge];

    }


    /**
     * Returns the distance
     *
     * @param dest the destination node
     * @return the distance to a node or POSITIVE_INFINITY if {@link Dijkstra} did not run yet or if unreachable
     */
    double getDistance(int dest) {
        return this.distance[dest];
    }


    /**
     * Sets the Distance to dest
     *
     * @param dest the destination node
     * @param dist the distance
     * @return true if distance has changed else false
     */
    boolean setDistance(int dest, double dist) {
        double val = this.getDistance(dest);		// TODO: Why use a temp variable? This operation is supposed to happen often and may produce a lot of garbage to collect. Can call getter in if-expression directly. 
        if (val == dist) return false;
        this.distance[dest] = dist;
        return true;
    }

	/**
	 * Sets the node's predecessor on a path from Dijkstra's starting node to given node. Used by Dijkstra class while the algorithm runs.
	 * 
	 * @param node	The node of which a predecessor gets set.
	 * @param predecessor The preceding node on a path to Dijkstra's start.
	 */
    public void setPredecessor(int node, int predecessor) {
    	this.predecessor[node] = predecessor;
    }
    
    /**
     * Returns the node's predecessor on a path from Dijkstra's starting node to given node.
     * The starting node is set when creating an instance of Dijkstra's algorithm for this graph.
     * If there is no path or if Dijkstra has not run yet, -1 will be returned!
     * 
     * 
     * @param node From this node, by going to the returned node and that one's predecessors, you can recursively find the ideal path to the starting node.
     * @return	preceding node on a path to start, or -1
     */
    public int getPredecessor(int node) {
    	return this.predecessor[node];
    }
    
    /**
     * Returns list of all nodes with their offsets
     *
     * @return offset array
     */
    int[] getNodeList() {
        return this.offset;
    }
}
