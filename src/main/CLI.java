package main;

import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * The Class for the User Interface
 * @version 1.0
 */
public class CLI {
    Graph graph;
    private String outPath;
    Scanner scanner = new Scanner(System.in);
    //print functions

    /**
     * prints an empty line
     */
    public static void print() {
        System.out.println();
    }

    /**
     * prints msg in a new line
     *
     * @param msg the message
     */
    public static void print(String msg) {
        System.out.println(msg);
    }

    /**
     * prints msg if printlevel is true
     * @param msg the message
     * @param printLevel if msg should be printed
     */
    public static void print(String msg, boolean printLevel) {
        if (printLevel)
            print(msg);
    }

    /**
     * adds a tab on the start of a line
     */
    public static void sol() {
        sol("   ");
    }

    public static void sol(String msg) {
        System.out.print(msg + " ");
    }

    /**
     * prints msg in an indented line
     * @param msg the message
     */
    public static void solPrint(String msg) {
        sol();
        print(msg);
    }

    /**
     * checks if a Graph was imported
     * @return if CLI has a Graph
     */
    public boolean hasGraph() {
        return this.graph != null;
    }
    //menu functions

    /**
     * prints the main menu dialog in the CLI
     */
    public void mainMenu() {
        print("Please select your action.");
        navigatorCommands();
        sol("$");
        String action = this.scanner.next();
        switch (action) {
            case "0":
                graphImportDialog();
                return;
            case "1":
                runDijkstraDialog();
                return;
            case "2":
                runQueryDialog();
                return;
            case "3":
                runDiffDialog();
                return;
            case "v":
                this.graph.toogleVerbose();
                mainMenu();
                return;
            case "d":
                this.graph.toogleDebug();
                mainMenu();
                return;
            case "exit":
                die();
                break;
            case "die":
                die();
                break;
            default:
                print("Invalid option. Try again or terminate script with 'die'");
                mainMenu();
        }

    }

    /**
     * kills the script
     */
    private void die() {
        this.scanner.close();
        print("Script terminated.");
        System.exit(0);
    }

    /**
     * prints list of available commands
     */
    public void navigatorCommands() {
        print("0");
        solPrint("Import Graph from .fmi file");
        print("1");
        solPrint("Get Distance between 2 Nodes");
        print("2");
        solPrint("Import Query from .que file");
        print("3");
        solPrint("Run Difference Analysis with .sol file");
        //print("v");
        //solPrint("Toggle verbose printing");
        //print("d");
        //solPrint("Toggle debug printing");
        print("exit");
        solPrint("Exits the current menu.");
        solPrint("When in main menu this will have the same result as 'die'");
        print("die");
        solPrint("Terminates the script");
    }

    /**
     * prints import graph dialog
     */
    public void graphImportDialog() {
        print("Import Graph from .fmi File");
        print();
        print("Please provide path to .fmi File");
        print("E.g. /home/<USER>/downloads/<FILENAME>.fmi");
        sol("$");
        String path = this.scanner.next();
        switch (path) {
            case "exit":
                print();
                mainMenu();
                return;
            case "die":
                die();
                break;
            default:
                break;
        }
        if (!hasValidGraphPath(path)) {
            print("This does not seem to be a .fmi file. Try again");
            print();
            path = null;
            graphImportDialog();
        }
        IOHandler ioHandler = new IOHandler();
        try {
            this.graph = ioHandler.importGraph(path);
        } catch (FileNotFoundException e) {
            print("O.o There is no such File. Please help me find it.");
            print();
            graphImportDialog();
        } catch (Exception e) {
            ioHandler.exceptionPrint(e);
            mainMenu();
            return;
        }

        mainMenu();
    }

    /**
     * prints run Dijkstra dialog
     */
    public void runDijkstraDialog() {
        if (!hasGraph()) {
            print("Please import Graph first.");
            print();
            mainMenu();
        }
        print("Input starting node as Integer. Integers smaller than 0 equal to command 'exit'.");
        print("Non-Integer inputs are not allowed");
        sol("$");
        int start;
        if (scanner.hasNextInt()) {
            start = this.scanner.nextInt();
        } else {
            print("Invalid value.");
            print();
            start = -1;
        }
        if (start < 0) {
            mainMenu();
            return;
        }
        
        print("Input target node in the same way.");
        sol("$");
        int target;
        if (scanner.hasNextInt()) {
            target = this.scanner.nextInt();
        } else {
            print("Invalid value. ");
            print();
            target = -1;
        }
        if (target < 0) {
            mainMenu();
            return;
        }

        
        
        this.graph.runDijkstra(start);
        print("");
        print("Distance between " + start + " and " + target + " is " + graph.getDistance(target) + ".");
//      print("Recursive distance according to path is " + graph.getDistanceViaPredecessors(target)); //Method is currently not supported.
        print("");
        
        mainMenu();
    }
    

    /**
     * prints run query dialog
     */
    public void runQueryDialog() {
        if (!hasGraph()) {
            print("Please import Graph first.");
            print();
            mainMenu();
        }
        print("Input path to .que File");
        sol("$");
        String path = this.scanner.next();
        switch (path) {
            case "exit":
                mainMenu();
                return;
            case "die":
                die();
                return;
            default:
                break;
        }
        if (!hasValidQueryPath(path)) {
            print("This does not seem to be a .que file. Try again");
            print();
            path = null;
            runQueryDialog();
        }
        IOHandler ioHandler = new IOHandler();
        try {
            outPath = ioHandler.runQuery(path, this.graph);
        } catch (FileNotFoundException e) {
            print("404 File not found. Do you know where else it could be?");
            print();
            runQueryDialog();
            return;
        } catch (Exception e) {
            ioHandler.exceptionPrint(e);
            mainMenu();
            return;
        }

        mainMenu();
    }

    /**
     * prints run Diff dialog
     */
    public void runDiffDialog() {
        if (!hasGraph()) {
            print("Please import Graph first.");
            print();
            mainMenu();
        }
        if (!hasValidOutputPath()) {
            print("Please run a query from option 1 or 2 first.");
            print();
            mainMenu();
        }
        //This code is obsolete as there can never be any .out files when running script because bin/out will be created when invoking make.sh or run.sh and previous
        //within any of bins subdirs will be deleted to ensure there can be no compilation errors.
//        if (outPath == null) {
//        	// No Query with output has been run yet this session. Set own outPath for diff.
//            //
//			print("Please input path to a previous output file.
//			(usually Windows: [this program's path]\\bin\\out\\[GraphHash].out)
//	Linux ./bin/out/[GraphHash].out		");
//			sol("$");
//			String temp = scanner.next();
//	        switch (temp) {
//	            case "exit":
//	                mainMenu();
//	                return;
//                case "die":
//                    die();
//                    return;
//	            default:
//	                break;
//	        }
//	        outPath = temp;
//		}
//        else {
        	print();
        	print("Comparing this sessions most recent query from " + outPath);
        //}
        
        print("Input path to .sol File");
        sol("$");
        String path = scanner.next();
        switch (path) {
            case "die":
                die();
                return;
            case "exit":
                mainMenu();
                return;
            default:
                break;
        }
        if (!hasValidSolutionPath(path)) {
            print("This does not seem to be a .fmisol file. Try again");
            print();
            path = null;
            runDiffDialog();
        }
        IOHandler ioHandler = new IOHandler();
        try {
            ioHandler.diff(path, outPath);
        } catch (FileNotFoundException e) {
            print("This file seems to be missing. Any guesses where else it could be?");
            print();
            runDiffDialog();
        } catch (Exception e) {
            ioHandler.exceptionPrint(e);
            mainMenu();
            return;
        }
        mainMenu();
    }

    /**
     * prints header for CLI
     * @param version the version to be displayed
     */
    public void header(String version) {
        print("Dijkstra Navigator [Version " + version + "]");
        print("(c) Pascal Glaesser, Stephan Schroth. Licenced GNU Lesser GPL v3.0");
        print("Find this on Github: https://github.com/otakupasi/Navigator");
        print("Accessibility may be restricted while in development");
        print();
    }

    /**
     * automatically calls methods to import graph run query and check for differences when invoked via terminal
     * @param fmiPath path to Graph file
     * @param quePath path to Query file
     * @param solPath path to Solution file
     */
    public void fullRun(String fmiPath, String quePath, String solPath) {
        header("0.1 - dev");
        IOHandler ioHandler = new IOHandler();
        try {
            graph = ioHandler.importGraph(fmiPath);
            String filename = ioHandler.runQuery(quePath, graph);
            ioHandler.diff(solPath, filename);
        } catch (FileNotFoundException e) {
            print();
            print("Please check the Paths you provided");
            print("arg1 should be a Path to a existing .fmi file");
            print("arg2 should be a Path to a existing .que file");
            print("arg3 should be a Path to a existing .sol file");
            die();
        } catch (Exception e) {
            ioHandler.exceptionPrint(e);
            print();
            print("verbose and debug prints are only usable from the CLI with invocation via makefile.sh");
            die();
        }

    }
    /**
     * Setter for outPath 
     * @param path the path for the .out file
     */
    public void setOutPath(String path) {
    	this.outPath = path;
    }

    public final boolean hasValidOutputPath() {
        return hasValidFileEnding(this.outPath, ".out");
    }

    public final boolean hasValidQueryPath(String path) {
        return hasValidFileEnding(path, ".que");
    }

    public final boolean hasValidSolutionPath(String path) {
        return hasValidFileEnding(path, ".sol");
    }

    public final boolean hasValidGraphPath(String path) {
        return hasValidFileEnding(path, ".fmi");
    }

    private boolean hasValidFileEnding(String file, String ending) {
        return file.endsWith(ending);
    }
    
}
