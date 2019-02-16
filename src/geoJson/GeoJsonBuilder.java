package geoJson;

import main.Graph;

public class GeoJsonBuilder {
    
	StringBuilder constructGeo;
	Graph graph;
	int destination;
	
	public GeoJsonBuilder(Graph graph, int destination) {
		this.graph = graph;
		this.destination = destination;
		
		constructGeo = new StringBuilder(graph.getNodesCount()*20);
		constructGeo.append(
				"\"type\": \"Feature\",\r\n" + 
				"\"geometry\": {\r\n" + 
				"    \"type\": \"LineString\",\r\n" + 
				"    \"coordinates\": [\r\n"
				);
	}
	
	public String createGeoPath(int destination) {
    	int temp = destination;
    	
    	constructGeo.append("[" + graph.getLongitude(temp) + ", " + graph.getLatitude(temp) + "]");
    	temp = graph.getPredecessor(temp);
    	   	
    	while(temp != graph.getStartingNode()) {
    		constructGeo.append(", [" + graph.getLongitude(temp) + ", " + graph.getLatitude(temp) + "]");
    		temp = graph.getPredecessor(temp);
    	}
    	
    	appendFileEnd();
    
    	// could enhance later with dotted lines from start/dest node coordinates
    	// to free selection start/dest coordinates given on call
    	
    	
    	return constructGeo.toString();
    }
	
	private void appendFileEnd() {
		constructGeo.append(
				"\n]\r\n" + 
				"}");
	}
	
}

