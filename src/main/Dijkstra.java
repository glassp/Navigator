package main;

/**
 * Dijkstra class that can run Dijkstra's algorithm on a given graph.
 * <p>
 * Before running, all distances in graph should be set to Double.INFINITY except for starting node with distance 0.
 */
public class Dijkstra extends CLILogger {
    /**
     * The graph upon which this Dijkstra instance works
     *
     * @see Graph
     */
    private Graph graph;

    /**
     * From this node to every node of the graph, the shortest distance is searched in the algorithm.
     */
    private int startNode;
    
    private NodeHeap heap;

    /**
     * Create instance of Dijkstra's algorithm. When using start() method, it will find the shortest distances
     * from a given start node to all nodes in the given graph object.
     * <p>
     * The distances will be saved in the graph through the setDistance method.
     *
     * @param graph The graph to operate upon
     * @param start Shortest distance from this one to every node of the graph will be found.
     * @param heap a NodeHeap object that uses the same graph, can later be reset from within other classes
     * @see Graph#setDistance(int, double)
     */
    public Dijkstra(Graph graph, int start, NodeHeap heap) {
        this.graph = graph;
        this.startNode = start;
        this.heap = heap;
        
        graph.setDistance(start, 0.0);
    }


    /**
     * Starts Dijkstra's algorithm.
     * In the graph given to this instance upon construction, it will overwrite the distance properties with
     * the shortest distances to the starting node.
     * <p>
     * Saves the preceding node on a path from start to node n in the graph's array predecessor[].
     *
     * @see NodeHeap
     */
    public void start() {
        print("Dijkstra's algorithm running...");
        this.startTiming();

        //Starting node now gets updated in runDijkstra-Method in graph class, where heap is created
//        NodeHeap heap = new NodeHeap(graph, startNode);

        int currentNode = heap.getAndRemoveNext();

        int firstEdge;
        
        int currentDestination;
        double newDistance;
        

        while (currentNode >= 0) {

//            int firstEdge = graph.getOffset(currentNode);
        	firstEdge = graph.getOffset(currentNode);

//            int currentDestination;
//            double newDistance;

            for (int i = firstEdge; i < firstEdge + graph.countOutgoingEdges(currentNode); i++) {


                currentDestination = graph.getDestination(i);
                newDistance = graph.getDistance(currentNode) + graph.getWeight(i);
                
                if (graph.getDistance(currentDestination) == Double.POSITIVE_INFINITY) {
                	heap.addNode(currentDestination);
				}

                if (graph.getDistance(currentDestination) > newDistance) {

                    //found shorter path to this neighbour. Update distance, predecessor and heap.
                    heap.decreaseDistance(currentDestination, newDistance);
                    graph.setPredecessor(currentDestination, currentNode);
                    

                }


            }

            currentNode = heap.getAndRemoveNext();

        }


        //passes info to CLI
        this.stop();
        print("Dijkstra's algorithm completed in " + CLILogger.runtimeInSeconds(this.runtime) + " seconds.");

    }

    //TODO: assertTimeout: run under 20 seconds on i5 Haswell processor
}
