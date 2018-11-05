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
        //TODO: implement foreach
        //foreach ($nodeList as $node) {
        //   this.graph.setDistance(node, Double.POSITIVE_INFINITY);
        //}
        this.graph.setDistance(this.startNode, 0);
    }

    public void start() {
        this.startTime = System.currentTimeMillis();
    }
    //TODO: run(Node start):void
    //calls Graph->setDistance(Node start, Node dest) thus no need to return anything
    //TODO: assertTimeout: run under 20 seconds
}
