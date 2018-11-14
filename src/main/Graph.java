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
     * Constructor
     *
     * @param nodes number of nodes that are used in the graph
     * @param edges number of edges that are used in the graph
     */
    public Graph(int nodes, int edges) {
        this.edges = new int[edges];
        this.weight = new double[edges];
        this.offset = new int[nodes];
        this.latitude = new double[nodes];
        this.longitude = new double[nodes];
        this.distance = new double[nodes];
        this.current = 0;
        //initializes all values in offset with -1
        Arrays.fill(offset, -1);
        Arrays.fill(distance, Double.POSITIVE_INFINITY);

    }

    /**
     * sets the latitude of a node
     *
     * @param node     the node
     * @param latitude the latitude
     */
    void setLatitude(int node, double latitude) {
        this.latitude[node] = latitude;
    }

    /**
     * sets the longitude
     *
     * @param node      the node
     * @param longitude the longitude
     */
    void setLongitude(int node, double longitude) {
        this.longitude[node] = longitude;
    }

    /**
     * sets both geo information at once
     *
     * @param node      the node
     * @param latitude  the latitude
     * @param longitude the longitude
     */
    void setGeo(int node, double latitude, double longitude) {
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
        return this.latitude[node];
    }

    /**
     * returns the longitude
     *
     * @param node the node
     * @return the longitude
     */
    double getLongitude(int node) {
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

    /**
     * increases the length of a double array
     * <p>
     * If array is a known array to this class e.g latitude it will automatically be replaced
     *
     * @param arr the double array to be increased
     * @return the increased array
     */
    private double[] increase(double[] arr) {
        double[] array = new double[arr.length + 2];
        //copies array into other array
        System.arraycopy(arr, 0, array, 0, arr.length);
        if (arr == this.weight) this.weight = array;
        else if (arr == this.distance) this.distance = array;
        else if (arr == this.latitude) this.latitude = array;
        else if (arr == this.longitude) this.longitude = array;
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
        int[] array = new int[arr.length + 2];
        System.arraycopy(arr, 0, array, 0, arr.length);
        if (arr == this.edges) this.edges = array;
        else if (arr == this.offset) this.offset = array;
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
                addEdge(start, dest, weight);
            } catch (UnorderedGraphException e) {
                insertEdge(start, dest, weight, true);
            }
        } else {
            int index = getOffset(start + 1);
            if (this.edges.length <= current + 1) increase(this.edges);
            if (this.weight.length <= current + 1) increase(this.weight);
            //move edges top Right by 1
            if (this.current + 1 - index >= 0)
                System.arraycopy(this.edges, index, this.edges, index + 1, this.current + 1 - index);
            //move weight top Right by 1
            if (this.current + 1 - index >= 0)
                System.arraycopy(this.weight, index, this.weight, index + 1, this.current + 1 - index);
            edges[index] = dest;
            this.weight[index] = weight;
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
        if (!this.hasOutgoingEdges(node)) return -1;
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
        double val = this.getDistance(dest);
        if (val == dist) return false;
        this.distance[dest] = dist;
        return true;
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
