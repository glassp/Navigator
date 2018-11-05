package main;

import java.util.Arrays;

public class Graph {
    private int[] edges;
    private int[] offset;
    private int lastIndex;
    private double[] weight;
    private double[] latitude;
    private double[] longitude;
    private double[] distance;

    public Graph(int nodes, int edges) {
        this.edges = new int[edges];
        this.weight = new double[edges];
        this.offset = new int[nodes];
        this.latitude = new double[nodes];
        this.longitude = new double[nodes];
        this.distance = new double[nodes];
        this.lastIndex = 0;
        //initializes all values in offset with -1
        Arrays.fill(offset, -1);

    }

    void addEdge(int start, int dest, int weight) {
        //if Node is has offset
        if (this.hasOutgoingEdges(start)) {
            if (this.hasOutgoingEdges(start + 1)) {
                //CASE: BETWEEN TWO NODES
                int index = this.getOffset(start + 1);
                //move edges right by 1

                if (this.edges.length - index >= 0)
                    System.arraycopy(this.edges, index, this.edges, index + 1, this.edges.length - index);
                //increase offset by 1
                for (int i = start + 1; i < this.offset.length; i++) {
                    this.setOffset(i, this.getOffset(i) + 1);
                }
                //add the edge
                this.edges[index] = dest;
                this.weight[this.getEdge(start, dest)] = weight;
            } else {
                //CASE: NO NODE WITH HIGHER INDEX
                this.edges[this.lastIndex] = dest;
                this.lastIndex++;
                this.weight[this.getEdge(start, dest)] = weight;
            }
        } else {
            //CASE: NO OFFSET YET
            this.setOffset(start, this.edges.length);
            this.edges[lastIndex] = dest;
            this.lastIndex++;
            if (this.getEdge(start, dest) != -1)
                this.weight[this.getEdge(start, dest)] = weight;
        }
    }


    boolean hasOutgoingEdges(int node) {
        return this.offset[node] != -1;
    }


    int getOffset(int node) {
        if (!this.hasOutgoingEdges(node)) return -1;
        return this.offset[node];
    }


    void setOffset(int node, int offset) {
        this.offset[node] = offset;
    }


    int getEdge(int start, int dest) {
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


    int countOutgoingEdges(int node) {
        if (!this.hasOutgoingEdges(node)) return 0;
        if (this.getOffset(node + 1) == -1)
            return this.edges.length - this.getOffset(node);
        else
            return this.getOffset(node + 1) - this.getOffset(node);
    }


    double getWeight(int start, int dest) {
        if (!this.hasOutgoingEdges(start)) return -1;
        int edge = this.getEdge(start, dest);
        if (edge < 0) return -1;
        return this.weight[edge];
    }


    double getDistance(int dest) {
        return this.distance[dest];
    }


    boolean setDistance(int dest, double dist) {
        double val = this.getDistance(dest);
        if (val == dist) return false;
        this.distance[dest] = dist;
        return true;
    }

    int[] getNodeList() {
        return this.offset;
    }

    //TODO: removeEdge(Node 1, Node 2):void
}
