package server.api;

import geoJson.GeoJsonBuilder;
import main.Graph;
import server.util.FileLogger;
import server.util.FileManager;

import java.io.File;

public class PointsResource extends ApiResource {

    private Graph graph;

    public PointsResource(File webRoot, Graph graph) {
        super(webRoot);
        this.graph = graph;
    }

    @Override
    public String run(String args) {

        FileLogger.syslog("PointsResource did run");
        try {
            double[] latitudes = graph.getLatitude();
            double[] longitudes = graph.getLongitude();

            String json = GeoJsonBuilder.run(latitudes, longitudes, GeoJsonBuilder.POINT);

            String fname = graph.hashCode() + "-points.json";

            File file = new File(webRoot + "/.build/" + fname);

            FileManager.file_put_contents(file, json);

            return "/.build/" + fname;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}


