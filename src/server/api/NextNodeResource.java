package server.api;

import main.Graph;
import server.util.FileLogger;
import server.util.FileManager;

import java.io.File;

public class NextNodeResource extends ApiResource {
    private Graph graph;

    public NextNodeResource(File webRoot, Graph graph) {
        super(webRoot);
        this.graph = graph;
    }

    @Override
    public String run(String arg) {
        FileLogger.syslog("NextNodeResource did run");
        try {
            String[] args = arg.split("&");
            if (args.length < 2) {
                FileLogger.exception("Too few Arguments supplied.");
                return null;
            } else {
                double lat = Double.parseDouble(args[0].split("=")[1]);
                double lng = Double.parseDouble(args[1].split("=")[1]);

                int node = graph.getClosestNode(lat, lng);
                double nLat = graph.getLatitude(node);
                double nLong = graph.getLongitude(node);

                String str = node + ":" + nLat + ":" + nLong;


                String fname = graph.hashCode() + "-nextNode.txt";

                File file = new File(webRoot + "/.build/" + fname);

                FileManager.file_put_contents(file, str);

                return "/.build/" + fname;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
