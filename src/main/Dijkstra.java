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

    
//    public void init() {
//        int[] nodeList = this.graph.getNodeList();
//        //distance is by default set to Double.POSITIVE_INFINITY
//        this.graph.setDistance(this.startNode, 0);
//    }

    
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
        
        /* For reference: in the graph,
         * edges[n]	 contains destination of edge # n
         * weight[n] contains weight of edge # n
         * 
         * offset[n] is where the first edge for node n is stored
         * offset[n+1] -1 is the last one (can overflow! last edge is last entry. can be -1.)
         * 			is taken care off in new implementation of countOutgoingEdges
         * 
         * TODO: Might be better implementing the offset array as explained in specification
         */


        //int[] offset = graph.getNodeList();    // maybe not ideal, but shouldn't be a problem

      
        int currentNode = heap.getAndRemoveNext();
        
        while (currentNode >= 0) {
        	int firstEdge = graph.getOffset(currentNode);
        	
        	int currentDestination;
        	double newDistance;
        	
        	for (int i = firstEdge; i < firstEdge + graph.countOutgoingEdges(currentNode); i++) {
        		System.out.print("looking at edge " +i + " from " + currentNode +" to " +graph.getDestination(i) ); //TODO: remove DEBUG info
        		
        		if (graph.getWeight(i) >= 0) {
            		// graph.getWeight(i) can be -1 if edge doesn't exist. Maybe throw exception?
        			
        			System.out.println(" which has a weight of " + graph.getWeight(i));	//TODO: remove DEBUG info
        			
        			currentDestination = graph.getDestination(i);
        			newDistance = graph.getDistance(currentNode) + graph.getWeight(i);
        			
        			System.out.print("\t\t Is " + graph.getDistance( currentDestination ) + " > " + newDistance + "? (current > newly found distance)"); //TODO: remove DEBUG info
        			if (graph.getDistance( currentDestination ) > newDistance) {
        				//TODO: do we need an exceptional case if node has already been seen? prob not since then it will have the lowest distcnace possible.
        				
						//found shorter path to this neighbour. Update distance, predecessor and heap.
        				heap.decreaseDistance(currentDestination, newDistance);
        				graph.setPredecessor(currentDestination, currentNode);
        				
        				System.out.print(" Yes! ... updated dist. of " + currentDestination + " to "+ newDistance + " (there: it's saved as " + graph.getDistance(currentDestination) + ")");
					} 
        			
				}
        		
        		System.out.println(); //new line. TODO: remove DEBUG info
			}
        		
        	
        	currentNode = heap.getAndRemoveNext();
        }


        //passes info to CLI
        print("Dijkstra's algorithm completed in " + CLILogger.runtimeInSeconds(this.runtime) + " seconds.");
    }
    
    //TODO: assertTimeout: run under 20 seconds
}
