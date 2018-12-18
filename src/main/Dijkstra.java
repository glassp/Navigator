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
        this.startTiming();
        
        NodeHeap heap = new NodeHeap(graph, startNode);
        
        //TODO: clean up comments and possible debug sysouts
        
        /* For reference: in the graph,
         * edges[n]	 contains destination of edge # n
         * weight[n] contains weight of edge # n
         * 
         * offset[n] is where the first edge for node n is stored
         * offset[n+1] -1 is the last one (can overflow! last edge is last entry. can be -1.)
         * 			is taken care off in new implementation of countOutgoingEdges
         *
         *  TODO: Might be better implementing the offset array as explained in specification
         *  TODO: Feel free to change it :)
         */


      
        int currentNode = heap.getAndRemoveNext();
        
        int count = 0; //TODO: remove debug counter
        
        while (currentNode >= 0) {
        	count++;
        	
        	int firstEdge = graph.getOffset(currentNode);
        	
        	int currentDestination;
        	double newDistance;
        	
        	if (count <= 40 || count > graph.getMaxEdgesCount() - 50) {
				debugPrint("Node: " + currentNode);
			}
        	
        	for (int i = firstEdge; i < firstEdge + graph.countOutgoingEdges(currentNode); i++) {
        		
        		if (graph.getWeight(i) >= 0) {
            		// graph.getWeight(i) can be -1 if edge doesn't exist. 
        			
        			currentDestination = graph.getDestination(i);
        			newDistance = graph.getDistance(currentNode) + graph.getWeight(i);
        			
        			if (count <= 40 || count > graph.getMaxEdgesCount() - 50) {
        				debugPrint("\tedge: to " + currentDestination + " (cost " + graph.getWeight(i) + ")");
        			}
        			
        			
        			if (graph.getDistance( currentDestination ) > newDistance) {
//        				TODO: perhaps exceptional case if node has already been seen? prob not since then it will have the lowest distance possible.
        				
        				if (count <= 40 || count > graph.getMaxEdgesCount() - 50) {
            				debugPrint("     decreasing and reheap (" + graph.getDistance( currentDestination ) + ">" + newDistance + ")");
            			}
						//found shorter path to this neighbour. Update distance, predecessor and heap.
        				heap.decreaseDistance(currentDestination, newDistance);
        				graph.setPredecessor(currentDestination, currentNode);
        				if (count <= 40 || count > graph.getMaxEdgesCount() - 50) {
            				debugPrint("       distance of " + currentDestination + " has been set to " + graph.getDistance(currentDestination));
            			}
        				
					}
        			if (count <= 40 || count > graph.getMaxEdgesCount() - 50) {
        				debugPrint("\n");//TODO: remove if blocks with extensive debug code
        			}
        			
				}

			}
        	
        	if (count <= 40 || count > graph.getMaxEdgesCount() - 50) {
				debugPrint("location of 3096359 is " + heap.getPositionOf(3096359)  + ". Determine next node now.");
			}
        	currentNode = heap.getAndRemoveNext();

        }


        //passes info to CLI
        this.stop();
        print("Dijkstra's algorithm completed in " + CLILogger.runtimeInSeconds(this.runtime) + " seconds.");
    }
    
    //TODO: assertTimeout: run under 20 seconds
}
