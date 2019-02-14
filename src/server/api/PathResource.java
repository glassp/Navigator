package server.api;

import server.util.FileLogger;

import java.io.File;

public class PathResource extends ApiResource {
    public PathResource(File webRoot) {
        super(webRoot);
    }

    @Override
    public String run(String arg) {
        FileLogger.syslog("TestResource did run");
        try {
            String[] args = arg.split("&");
            if (args.length < 2)
                return null;
            else {
                //TODO: run Graph, build GeoJson with found path and output path to geojson file
                return "PATH_TO_GEO_JSON";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
