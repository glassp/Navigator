package main;

import java.util.Scanner;
public class CLI {
    Graph graph;
    Scanner scanner = new Scanner(System.in);
    //print functions
    public static void print() {
        System.out.println();
    }
    public static void print(String msg) {
        System.out.println(msg);
    }
    public static void print(String msg, boolean printLevel) {
        if (printLevel)
            print(msg);
    }

    public static void sol() {
        sol("   ");
    }

    public static void sol(String msg) {
        System.out.print(msg + " ");
    }

    public static void solPrint(String msg) {
        sol();
        print(msg);
    }
    //menu functions

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
                break;
            case "d":
                this.graph.toogleDebug();
                break;
            case "i":
                this.graph.toogleInfo();
                break;
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

    private void die() {
        this.scanner.close();
        print("Script terminated.");
        System.exit(0);
    }

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
        print("i");
        solPrint("Toogle info printing");
        print("exit");
        solPrint("Exits the current Menu.");
        solPrint("When in main menu this will have the same result as 'die'");
        print("die");
        solPrint("Terminates the script");
    }

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
    }

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

    }

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
        ioHandler.runQuery(path, this.graph);
    }

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
        ioHandler.diff(path, ioHandler.pathToBin() + "out/");
    }

    public void header(String version) {
        print("Dijkstra Navigator [Version " + version + "]");
        print("(c) Pascal Glaesser, Stephan Schroth. Licenced GNU Lesser GPL v3.0");
        print("Find this on Github: https://github.com/otakupasi/Navigator");
        print("Accessibility may be restricted while in development");
        print();
    }
}
