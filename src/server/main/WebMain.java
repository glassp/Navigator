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
        } catch (NullPointerException | FileNotFoundException e) {
            FileLogger.syslog("The file was not found. Cannot start Server");
            System.exit(1);
            return;
        } catch (IllegalArgumentException e) {
            FileLogger.syslog("Cannot start Server.\r\n" + e.getMessage());
            System.exit(1);
            return;
        }
        new Server(initPort(args), new File(initWebRoot(args)), true, new File("log.txt"), graph);
    }

    /**
     * @param args the arguments of the terminal
     * @return args[0]
     * @throws IllegalArgumentException if args[0] is not set
     */
    private static String initFmiPath(String[] args) {
        try {
            return args[0];
        } catch (IndexOutOfBoundsException e) {
            throw new IllegalArgumentException("Must supply one argument");
        }
    }

    /**
     * @param args the arguments of the terminal
     * @return args[2] or 8080
     */
    private static int initPort(String[] args) {
        try {
            return Integer.parseInt(args[2]);
        } catch (IndexOutOfBoundsException e) {
            return 8080;
        }
    }

    /**
     * @param args the arguments of the terminal
     * @return args[1] or "www/"
     */
    private static String initWebRoot(String[] args) {
        try {
            return args[1];
        } catch (IndexOutOfBoundsException e) {
            return FileManager.getProjectRoot() + "www/";
        }
    }


}
