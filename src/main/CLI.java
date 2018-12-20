package main;

import java.util.Scanner;

/**
 * The Class for the User Interface
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
        IOHandler ioHandler = new IOHandler();
        this.graph = ioHandler.importGraph(path);
        mainMenu();
    }

    /**
     * prints run Dijkstra dialog
     * @TODO cleanup
     */
    public void runDijkstraDialog() {
        print("Input starting node as Integer. Integers smaller than 0 equal to command 'exit'.");
        print("Non-Integer inputs are not allowed");
        sol("$");
        int start;
        if (scanner.hasNextInt()) {
            start = this.scanner.nextInt();
        } else {
            print("Invalid value.");
            start = -1;
//            print(); //Restart didn't work since input is sometimes still immediately acquired from first time, resulting in exception, so I disabled it for now.
//            runDijkstraDialog();
//            return;
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
            print("Invalid value.");
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
        IOHandler ioHandler = new IOHandler();
        outPath = ioHandler.runQuery(path, this.graph);
        mainMenu();
    }

    /**
     * prints run Diff dialog
     */
    public void runDiffDialog() {
    	
        if (outPath == null) {
        	// No Query with output has been run yet this session. Set own outPath for diff.
			print("Please input path to a previous output file. (usually [this program's path]\\out\\[numbers].out)");
			sol("$");
			
			String temp = scanner.next();
	        switch (temp) {
	            case "die":
	                die();
	                return;
	            case "exit":
	                mainMenu();
	                return;
	            default:
	                break;
	        }
	        outPath = temp;
		}
        else {
        	print();
        	print("Comparing this sessions most recent query from " + outPath);
        }
        
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
        IOHandler ioHandler = new IOHandler();
        ioHandler.diff(path, outPath);
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
     * automatically calls methods to import graph run query and check for differences when invoced via terminal
     * @param fmiPath path to Graph file
     * @param quePath path to Query file
     * @param solPath path to Solution file
     */
    public void fullRun(String fmiPath, String quePath, String solPath) {
        header("0.1 - dev");
        IOHandler ioHandler = new IOHandler();
        graph = ioHandler.importGraph(fmiPath);
        String filename = ioHandler.runQuery(quePath, graph);
        ioHandler.diff(solPath, filename);

    }
    /**
     * Setter for outPath 
     * @param path
     */
    public void setOutPath(String path) {
    	this.outPath = path;
    }
    
}
