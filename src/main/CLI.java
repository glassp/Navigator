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
     * prints a empty line
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
     * prints msg in a indented line
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
     * prints a the main menu dialog in the CLI
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
     * prints list of avaiable commands
     */
    public void navigatorCommands() {
        print("0");
        solPrint("import Graph from .fmi file");
        print("1");
        solPrint("run Dijkstra");
        print("2");
        solPrint("import Query from .que file");
        print("3");
        solPrint("Run Difference Analysis form .sol file");
        print("v");
        solPrint("Toogle verbose printing");
        print("d");
        solPrint("Toogle debug printing");
        print("exit");
        solPrint("Exits the current Menu.");
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
     * prints run dijkstra dialog
     */
    public void runDijkstraDialog() {
        print("Input starting node as Integer. Integers smaller than 0 equal to command 'exit'.");
        print("Non-Integer inputs are not allowed");
        sol("$");
        int start;
        if (scanner.hasNextInt()) {
            start = this.scanner.nextInt();
        } else {
            print("Invalid value. Try again");
            print();
            runDijkstraDialog();
            return;
        }
        if (start < 0) {
            mainMenu();
            return;
        }
        this.graph.runDijkstra(start);
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
}
