package main;

import java.util.Arrays;


/**
 * The Graph class
 */
public class Graph extends CLILogger {

    /**
     * stores the destination of a edge
     */
    private int[] edges;
    /**
     * stores the offset
     */
    private int[] offset;
    /**
     * the first unused index (of edges, it seems)
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

    public void runDijkstra(int start) {
        Dijkstra dijkstra = new Dijkstra(this, start);
        //TODO: run dijkstra
    }

    public void runQuery(int start, int dest) {

    }

    /**
     * sets the latitude of a node
     *
     * @param node     the node
     * @param latitude the latitude
     */
    void setLatitude(int node, double latitude) {
        this.latitude[node] = latitude;
        this.infoPrint("Latitude set");
    }

    /**
     * sets the longitude
     *
     * @param node      the node
     * @param longitude the longitude
     */
    void setLongitude(int node, double longitude) {
        this.longitude[node] = longitude;
        this.infoPrint("Longitude set");
    }

    /**
     * sets both geo information at once
     *
     * @param node      the node
     * @param latitude  the latitude
     * @param longitude the longitude
     */
    void setGeo(int node, double latitude, double longitude) {
        this.debugPrint("running set Geo");
        this.setLatitude(node, latitude);
        this.setLongitude(node, longitude);
    }

    /**
     * returns the latitude
     *
     * @param node the node
     * @return the latitude
     */
    double getLatitude(int node) {
        this.infoPrint("Latitude returning");
        return this.latitude[node];
    }

    /**
     * returns the longitude
     *
     * @param node the node
     * @return the longitude
     */
    double getLongitude(int node) {
        this.infoPrint("Longitude returning");
        return this.longitude[node];
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
        if (hasOutgoingEdges(start + 1)) {
            this.infoPrint("UnorderedGraphException thrown");
            throw new UnorderedGraphException("The next Node has already created Edges please ty to use insertEdge(...)");
        }
        if (this.current >= edges.length) increase(edges);
        if (!hasOutgoingEdges(start)) {
            this.setOffset(start, this.current);
        }
        this.infoPrint("setting edge");
        this.edges[this.current] = dest;
        this.infoPrint("setting weight");
        this.weight[getEdge(start, dest)] = weight;
        this.current++;
    }

    /**
     * increases the length of a double array
     * <p>
     * If array is a known array to this class e.g latitude it will automatically be replaced
     *
     * @param arr the double array to be increased
     * @return the increased array
     */
    private double[] increase(double[] arr) {
        this.debugPrint("need to increase array");
        double[] array = new double[arr.length + 2];
        //copies array into other array
        System.arraycopy(arr, 0, array, 0, arr.length);
        if (arr == this.weight) this.weight = array;
        else if (arr == this.distance) this.distance = array;
        else if (arr == this.latitude) this.latitude = array;
        else if (arr == this.longitude) this.longitude = array;
        this.debugPrint("array was increased");
        return array;

    }

    /**
     * increases the length of a int array
     * <p>
     * If array is a known array to this class e.g. edges it will automatically be replaced
     *
     * @param arr the int array to be increased
     * @return the increased array
     */
    private int[] increase(int[] arr) {
        this.debugPrint("need to increase array");
        int[] array = new int[arr.length + 2];
        System.arraycopy(arr, 0, array, 0, arr.length);
        if (arr == this.edges) this.edges = array;
        else if (arr == this.offset) this.offset = array;
        this.debugPrint("array was increased");
        return array;

    }

    /**
     * adds a new Edge to the Graph and moves indices if needed
     *
     * @param start  the start node
     * @param dest   the destination node
     * @param weight the weight
     * @param skip   if true skips the test and forces indices to be moved
     */
    public void insertEdge(int start, int dest, double weight, boolean skip) {

        if (getOffset(start + 1) == -1 || !skip) {
            try {
                this.verbosePrint("trying to add Edge");
                addEdge(start, dest, weight);
            } catch (UnorderedGraphException e) {
                this.debugPrint("Exception handled");
                this.infoPrint("inserting Edge");
                insertEdge(start, dest, weight, true);
            }
        } else {

            int index = getOffset(start + 1);
            if (this.edges.length <= current + 1) increase(this.edges);
            if (this.weight.length <= current + 1) increase(this.weight);

            this.infoPrint("moving edges to right by 1");
            if (this.current + 1 - index >= 0)
                System.arraycopy(this.edges, index, this.edges, index + 1, this.current + 1 - index);

            this.infoPrint("moving weight of edges to right by 1");
            if (this.current + 1 - index >= 0)
                System.arraycopy(this.weight, index, this.weight, index + 1, this.current + 1 - index);

            this.infoPrint("moving offsets");
            for (int i = start + 1; i < offset.length; i++) {
                if (getOffset(i) >= 0) setOffset(i, getOffset(i) + 1);
            }

            this.infoPrint("inserting new edge data");
            edges[index] = dest;
            this.weight[index] = weight;
            this.current++;

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
        this.debugPrint("checking outgoing edges");
        return this.offset[node] != -1;
    }


    /**
     * Returns the Offset of a node
     *
     * @param node the node
     * @return the offset or -1 if it does not exist
     */
    int getOffset(int node) {
        return this.offset[node];
    }


    /**
     * Sets the offset of a node
     *
     * @param node   the node
     * @param offset the offset
     */
    void setOffset(int node, int offset) {
        this.infoPrint("setting offset");
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
        this.debugPrint("running getEdge");
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
        this.verbosePrint("no Edge found. Exception may be thrown", "Warning");
        return -1;
    }


    /**
     * Returns the node which is nearest to [node]
     *
     * @param node the node
     * @return the next node or -1 if it does not exist
     */
    int nextNode(int node) {
        this.debugPrint("running nextNode");
        double minVal = Double.POSITIVE_INFINITY;
        int minIndex = -1;
        int offset = this.getOffset(node);
        if (offset == -1){
          this.debugPrint("no nextNode: offset invalid");
          return -1;
        }
        int elem = this.countOutgoingEdges(node);
        if (elem < 1){
          this.debugPrint("no nextNode: zero outgoing Edges");
          return -1;
        }
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
        
        // Has to iterate in O(n), but could be done in O(1) if offset was managed like in specifications, see TO.DO below.
   
    	int lastEdge = -1;
    	for (int i = node+1; i < this.current; i++) {
			lastEdge = offset[i];
			if (lastEdge != -1) break;
		}
    	
    	if (lastEdge == -1) {
			// no other node after it that has edges
    		return this.current - offset[node];
		}
    	
    	return lastEdge - offset[node];
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
     * Returns weight of an edge
     *
     * @param edge the edge
     * @return weight or -1 if it doesn't exist
     */
    double getWeight(int edge) {
    	if (edge >= this.current) {
			return -1;
		}
    	
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
        if (this.getDistance(dest) == dist) return false;
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
