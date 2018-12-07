package main;

public class CLI {
    //TODO
    Graph graph;
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
        //TODO: input handler

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

    }

    public void graphImportDialog() {
        print("Import Graph from .fmi File");
        print();
        print("Please provide path to .fmi File");
        print("E.g. /home/<USER>/downloads/<FILENAME>.fmi");
        sol("$");
        //TODO: input handler
        IOHandler ioHandler = new IOHandler();
        //graph = ioHandler.importGraph();
    }

    public void runDijkstraDialog() {
        print("Input starting node");
        sol("$");
        //TODO input handler
        //TODO: call graph.runDijkstra();

    }

    public void runQueryDialog() {
        print("Input path to Query File");
        sol("$");
        //TODO input handler
        //TODO: call graph.runQuery()
    }

    public void runDiffDialog() {
        //TODO
    }

    public void header(String version) {
        print("Dijkstra Navigator [Version " + version + "]");
        print("(c) Pascal Glaesser, Stephan Schroth. Licenced GNU Lesser GPL v3.0");
        print("Find this on Github: https://github.com/otakupasi/Navigator");
        print("Accessibility may be restricted while in development");
        print();
    }
}
