package main;

public class Node implements Comparable<Node>{
	int nodeID;
	Graph graph;
	
	public Node(int nodeID, Graph graph) {
		this.nodeID = nodeID;
		this.graph = graph;
	}

	public double getDistance(){
		return graph.getDistance(nodeID);
	}
	public void setDistance(double newDistance) {
		graph.setDistance(nodeID, newDistance);
	}
	public int getID(){
		return nodeID;
	}
	
	@Override
	public int compareTo(Node o) {
		int compare;
		if (o.getDistance() == this.getDistance()) {
			compare = 0;
		}
		else if (o.getDistance() > this.getDistance()) {
			compare = -1;
		}
		else {
			compare = 1;
		}
		
		return compare;
	}
	
	

}
