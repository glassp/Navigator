package main;

import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * The Class for the User Interface
 * @version 1.0
 */
public class CLI {
    /**
     * The attached Graph
     */
    Graph graph;
    /**
     * The Scanner used for getting input
     */
    Scanner scanner = new Scanner(System.in);
    /**
     * The output Path
     */
    private String outPath;

    //Getter and Setter

    /**
     * Prints empty line in Terminal
     */
    public static void print() {
        System.out.println();
    }

    //Print methods

    /**
     * Prints a message and starts a new line afterwards
     *
     * @param msg the message
     */
    public static void print(String msg) {
        System.out.println(msg);
    }

    /**
     * Prints a message if the printLevel is set to true
     * @param msg the message
     * @param printLevel if msg should be printed
     */
    public static void print(String msg, boolean printLevel) {
        if (printLevel)
            print(msg);
    }

    /**
     * Adds some space to the start of a line.
     * Used for styling the output.
     */
    public static void sol() {
        sol("   ");
    }

    /**
     * Prints a message and adds some space afterwards.
     * Used for styling the output and marking input lines
     *
     * @param msg the message
     */
    public static void sol(String msg) {
        System.out.print(msg + " ");
    }

    /**
     * Prints the message with some space before it.
     * @see CLI#sol(String)
     * @see CLI#print(String)
     * @param msg the message
     */
    public static void solPrint(String msg) {
        sol();
        print(msg);
    }

    /**
     * Setter for outPath
     *
     * @param path the path for the .out file
     */
    public void setOutPath(String path) {
        this.outPath = path;
    }

    //Dialog methods + main handler

    /**
     * Automatically imports Graph, runs Query and compares it to the Solution
     * This is the main handler method for a Terminal running script
     * Started via run.sh arg1 arg2 arg3
     *
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
     * Prints the main menu in the terminal
     * This method is the main handler method for all of CLI
     * Started via makefile.sh
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
                this.graph.toggleVerbose();
                mainMenu();
                return;
            case "d":
                this.graph.toggleDebug();
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
     * Prints the dialog for importing a Graph
     * Also imports Graph from File and attaches it to CLI
     * @see IOHandler#importGraph(String)
     * @see CLI#hasValidGraphPath(String)
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
     * Prints the dialog for running a single Query after Dijkstra invocation
     * Also runs Dijkstra and the Query
     * @see Graph#runDijkstra(int)
     * @see Graph#runQuery(int, int)
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
     * Prints the dialog for running multiple Queries after Dijkstra invocation
     * Also runs Dijkstra and the Queries
     * @see IOHandler#runQuery(String, Graph)
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
     * Prints the dialog for comparing the calculated Distances to the Solution of .sol
     * Also runs a Difference Analysis between .out and .sol Files
     * @see IOHandler#diff(String, String)
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

    //Other methods

    /**
     * Prints the header for CLI
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
     * Checks if the Path of output points to a valid .out file.
     * @return true if .out file found
     * @see CLI#hasValidFileEnding(String, String)
     */
    public final boolean hasValidOutputPath() {
        return hasValidFileEnding(this.outPath, ".out");
    }

    /**
     * Checks if the provided Path points to a valid .que file.
     * @return true if .que file found
     * @see CLI#hasValidFileEnding(String, String)
     */
    public final boolean hasValidQueryPath(String path) {
        return hasValidFileEnding(path, ".que");
    }

    /**
     * Checks if the provided Path points to a valid .sol file.
     * @return true if .sol file found
     * @see CLI#hasValidFileEnding(String, String)
     */
    public final boolean hasValidSolutionPath(String path) {
        return hasValidFileEnding(path, ".sol");
    }

    /**
     * Checks if the provided Path points to a valid .fmi file.
     * @return true if .fmi file found
     * @see CLI#hasValidFileEnding(String, String)
     */
    public final boolean hasValidGraphPath(String path) {
        return hasValidFileEnding(path, ".fmi");
    }

    /**
     * Checks if the given file Path ends with given file extension.
     * @return true if file has given extension
     */
    private boolean hasValidFileEnding(String file, String ending) {
        return file.endsWith(ending);
    }

    /**
     * Checks if a Graph was imported
     *
     * @return if CLI has a Graph attached
     * @see CLI#graphImportDialog()
     */
    public boolean hasGraph() {
        return this.graph != null;
    }

    /**
     * Kills the Script with exit code 0
     */
    private void die() {
        this.scanner.close();
        print("Script terminated.");
        System.exit(0);
    }

    /**
     * Prints a list of all available commands
     *
     * @see CLI#mainMenu()
     * @see CLI#graphImportDialog()
     * @see CLI#runQueryDialog()
     * @see CLI#runDiffDialog()
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
}
