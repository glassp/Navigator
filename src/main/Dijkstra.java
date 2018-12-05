package main;

public class Dijkstra {
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
         */
        
        
        int currentNode = heap.getAndRemoveNext();
        graph.getOffset(currentNode);
        
        //alle anliegende updaten, falls geringer.
        
        
        // In Heap: nächstes Elem. mit Priorität (niedrigste Kosten. Am anfang: Startknoten, der dann entfernt wird, dann niedrigster anliegender etc.)
        
        // Nachbarkosten aktualisieren, falls billiger
        // dafür bisherige + edge vergleichen mit bisherigen Zielkosten (in Graph?)
        
        // entfernen aus Heap, wenn selbst betrachtet wurde.
        
        
        long timeTaken = System.currentTimeMillis() - startTime;
        System.out.println("Dijkstra's algorithm completed in " + timeTaken/1000 + " seconds.");
    }
    //TODO: implement algorithm
    //TODO: assertTimeout: run under 20 seconds
}
