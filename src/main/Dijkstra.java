package main;

/**
 * 
 * Dijkstra class that can run Dijkstra's algorithm on a given graph.
 *
 */
public class Dijkstra extends CLILogger {
    /**
     * The time when the algorithm started running. Will be set when using the start() method.
     */
	public long startTime;
	
	/**
	 * The graph upon which this Dijkstra instance works
	 */
    private Graph graph;
    
    /**
     * From this node to every node of the graph, the shortest distance is searched in the algorithm.  
     */
    private int startNode;

    /**
     * Create instance of Dijkstra's algorithm. When using start() method, it will find the shortest distances
     * from a given start node to all nodes in the given graph object.
     * 
     * The distances will be saved in the graph through the setDistance method.
     *  
     * @param graph	The graph to operate upon
     * @param start	Shortest distance from this one to every node of the graph will be found.  
     */
    public Dijkstra(Graph graph, int start) {
        this.graph = graph;
        this.startNode = start;
        
        graph.setDistance(start, 0.0);
    }


    
    /**
     * Starts Dijkstra's algorithm.
     * In the graph given to this instance upon construction, it will overwrite the distance properties with
     * the shortest distances to the starting node.
     * 
     * Saves the preceding node on a path from start to node n in the graph's array predecessor[]. 
     * 
     */
    public void start() {
    	print("Dijkstra's algorithm running...");
        this.startTiming();
        
        NodeHeap heap = new NodeHeap(graph, startNode);
        
        int currentNode = heap.getAndRemoveNext();        
        		
        
        while (currentNode >= 0) {
        	
        	int firstEdge = graph.getOffset(currentNode);
        	
        	int currentDestination;
        	double newDistance;
        	
        	for (int i = firstEdge; i < firstEdge + graph.countOutgoingEdges(currentNode); i++) {
        	
        		
        			currentDestination = graph.getDestination(i);
        			newDistance = graph.getDistance(currentNode) + graph.getWeight(i);
                	
        			if (graph.getDistance( currentDestination ) > newDistance) {
            			
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
