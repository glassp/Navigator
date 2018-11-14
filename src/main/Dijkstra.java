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
        
        
        // In Heap: n�chstes Elem. mit Priorit�t (niedrigste Kosten. Am anfang: Startknoten, der dann entfernt wird, dann niedrigster anliegender etc.)
        
        // Nachbarkosten aktualisieren, falls billiger
        // daf�r bisherige + edge vergleichen mit bisherigen Zielkosten (in Graph?)

        // Kosten vom startknoten zu knoten x kann über setDistance und getDistance verwaltet werden startknoten selbständig mit setDistance(destination, distance) auf 0 setzen
        // setDistance gibt auch aus ob ein wert geändert wurde
        
        // entfernen aus Heap, wenn selbst betrachtet wurde.
        
        
        
    }
    //TODO: implement algorithm
    //TODO: assertTimeout: run under 20 seconds
}
