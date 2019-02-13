package server.main;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * The entry point for Script
 */
public class WebMain {
    /**
     * The main method for WebApp mode
     *
     * @param args the arguments passed via terminal
     */
    public static void main(String[] args) {
        if (args.length == 0) {
            Path currentRelativePath = Paths.get("");
            String path = currentRelativePath.toAbsolutePath().toString();
            path = path.substring(0, path.indexOf("Navigator"));
            path += "Navigator/www/";
            args = new String[1];
            args[0] = path;
        }
        new Server(8080, new File(args[0]), true, new File("log.txt"));
        //TODO: run GeoJsonBuilder and store file as geo.json within webRoot (args[0])
    }
}
