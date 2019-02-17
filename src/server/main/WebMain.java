package server.main;

import main.Graph;
import main.IOHandler;
import server.util.FileLogger;
import server.util.FileManager;

import java.io.File;
import java.io.FileNotFoundException;

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
        Graph graph;
        try {
            graph = new IOHandler().importGraph(initFmiPath(args));
        } catch (FileNotFoundException e) {
            FileLogger.syslog("The file was not found. Cannot start Server");
            return;
        }
        new Server(initPort(args), new File(initWebRoot(args)), true, new File("log.txt"), graph);
    }


    private static String initWebRoot(String[] args) {
        try {
            return args[1];
        } catch (IndexOutOfBoundsException e) {
            return FileManager.getProjectRoot() + "www/";
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
