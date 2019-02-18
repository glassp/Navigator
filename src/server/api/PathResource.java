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
            if (args.length < 4) {

                FileLogger.exception("Too few Arguments supplied.");
                return null;
            } else {
                double startLat = Double.parseDouble(args[0].split("=")[1]);
                double startLong = Double.parseDouble(args[1].split("=")[1]);
                double destLat = Double.parseDouble(args[2].split("=")[1]);
                double destLong = Double.parseDouble(args[3].split("=")[1]);

                int start = graph.getNode(startLat, startLong);
                int dest = graph.getNode(destLat, destLong);

                if (start < 0 || dest < 0) {
                    start = graph.getClosestNode(startLat, startLong);
                    dest = graph.getClosestNode(destLat, destLong);
                }

                graph.runQuery(start, dest);

                //TODO: bugfix: get Path will output a empty geoJson
                int temp = dest;
                GeoJsonBuilder builder = new GeoJsonBuilder(GeoJsonBuilder.LINE_STRING);

                do {
                    builder.addGeo(graph.getLatitude(temp), graph.getLongitude(temp));
                    temp = graph.getPredecessor(temp);
                } while (temp != graph.getStartingNode() && temp >= 0);

                String json = builder.build();

                String fname = graph.hashCode() + "-" + start + "-" + dest + ".json";

                File file = new File(webRoot + "/.build/" + fname);

                FileManager.file_put_contents(file, json);

                return "/.build/" + fname;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
