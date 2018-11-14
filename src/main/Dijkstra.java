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
        //distance is by default set to Double.POSITIV_INFINITY
        this.graph.setDistance(this.startNode, 0);
    }

    public void start() {
        this.startTime = System.currentTimeMillis();
        
        
        // In Heap: nächstes Elem. mit Priorität (niedrigste Kosten. Am anfang: Startknoten, der dann entfernt wird, dann niedrigster anliegender etc.)
        
        // Nachbarkosten aktualisieren, falls billiger
        // dafür bisherige + edge vergleichen mit bisherigen Zielkosten (in Graph?)
        
        // entfernen aus Heap, wenn selbst betrachtet wurde.
        
        
        
    }
    //TODO: implement algorithm
    //TODO: assertTimeout: run under 20 seconds
}
