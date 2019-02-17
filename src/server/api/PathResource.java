package server.api;

import geoJson.GeoJsonBuilder;
import main.Graph;
import server.util.FileLogger;
import server.util.FileManager;

import java.io.File;

public class PathResource extends ApiResource {
    private Graph graph;

    public PathResource(File webRoot, Graph graph) {
        super(webRoot);
        this.graph = graph;
    }

    @Override
    public String run(String arg) {
        FileLogger.syslog("PathResource did run");
        try {
            String[] args = arg.split("&");
            if (args.length < 4)
                return null;
            else {
                double startLat = Double.parseDouble(args[0]);
                double startLong = Double.parseDouble(args[1]);
                double destLat = Double.parseDouble(args[2]);
                double destLong = Double.parseDouble(args[3]);

                int start = graph.getNode(startLat, startLong);
                int dest = graph.getNode(destLat, destLong);

                if (start < 0 || dest < 0)
                    return null;

                graph.runQuery(start, dest);

                int temp = dest;
                GeoJsonBuilder builder = new GeoJsonBuilder();

                while (temp != graph.getStartingNode()) {
                    builder.addGeo(graph.getLatitude(temp), graph.getLongitude(temp));
                    temp = graph.getPredecessor(temp);
                }

                String json = builder.build();

                String fname = graph.hashCode() + "-" + start + "-" + dest + ".json";

                File file = new File(webRoot + fname);

                FileManager.file_put_contents(file, json);

                return "./" + fname;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
