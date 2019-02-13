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

        new Server(initPort(args), new File(initWebRoot(args)), true, new File("log.txt"));
        //TODO: run GeoJsonBuilder and store file as geo.json within webRoot (args[0])
        //TODO: use initFmiPath(args) to get path to .fmi file as provided via terminal
    }


    private static String initWebRoot(String[] args) {
        try {
            return args[1];
        } catch (IndexOutOfBoundsException e) {
            Path currentRelativePath = Paths.get("");
            String path = currentRelativePath.toAbsolutePath().toString();
            path = path.substring(0, path.indexOf("Navigator"));
            path += "Navigator/www/";
            return path;
        }
    }

    private static String initFmiPath(String[] args) {
        try {
            return args[0];
        } catch (IndexOutOfBoundsException e) {
            throw new IllegalArgumentException("Must supply one argument");
        }
    }

    private static int initPort(String[] args) {
        try {
            return Integer.parseInt(args[2]);
        } catch (IndexOutOfBoundsException e) {
            return 8080;
        }
    }

}
