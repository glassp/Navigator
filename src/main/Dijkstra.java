package main;

public class Dijkstra extends CLILogger {
    public long startTime;
    private Graph graph;
    private int startNode;

    public Dijkstra(Graph graph, int start) {
        this.graph = graph;
        this.startNode = start;
    }

    public void init() {
        int[] nodeList = this.graph.getNodeList();
        //distance is by default set to Double.POSITIVE_INFINITY
        this.graph.setDistance(this.startNode, 0);
    }

    
    /**
     * starts Dijkstra's algorithm.
     * In the graph given to this instance upon construction, it will overwrite the distance properties with
     * the shortest distances to the starting node.
     * 
     * Saves the preceding node on a path from start to node n in the graph's array predecessor[]. 
     * 
     */
    public void start() {
        this.startTime = System.currentTimeMillis();
        
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
        
        
        int currentNode = heap.getAndRemoveNext();
        
        
        int offset[] = graph.getNodeList();	// maybe not ideal, but shouldn be a problem

        
        while (currentNode >= 0) {
        	
            //update all neighbours
        	
        	int firstEdge = offset[currentNode];
        	
        	for (int i = firstEdge; i < firstEdge + graph.countOutgoingEdges(currentNode); i++) {
				
        		graph.getWeight(i); //can be -1 if edge doesn't exist, so maybe throw exeption instead
        		
			}
        		
        		
        		
        	
        	
        	
        }
        
        
        // In Heap: n�chstes Elem. mit Priorit�t (niedrigste Kosten. Am anfang: Startknoten, der dann entfernt wird, dann niedrigster anliegender etc.)
        
        // Nachbarkosten aktualisieren, falls billiger
        // daf�r bisherige + edge vergleichen mit bisherigen Zielkosten (in Graph?)

        // Kosten vom startknoten zu knoten x kann über setDistance und getDistance verwaltet werden startknoten selbständig mit setDistance(destination, distance) auf 0 setzen
        // setDistance gibt auch aus ob ein wert geändert wurde
        
        // entfernen aus Heap, wenn selbst betrachtet wurde.
        
        
        long timeTaken = System.currentTimeMillis() - startTime;
        System.out.println("Dijkstra's algorithm completed in " + timeTaken/1000 + " seconds.");
    }
    //TODO: implement algorithm
    //TODO: assertTimeout: run under 20 seconds
}
