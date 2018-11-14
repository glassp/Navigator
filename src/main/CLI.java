package main;

public class CLI {
    //TODO
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


    public static void mainMenu() {
        print("Please select your action. To print a help menu enter h or help");
        sol("$");
        //TODO: input handler

    }

    public static void mainHelpMenu() {
        print("h");
        solPrint("Opens this help menu");
        print("help");
        solPrint("Opens this help menu");
        navigatorCommands();
        print("exit");
        solPrint("exiting this script");
        print("die");
        solPrint("exiting this script");
    }

    public static void navigatorCommands() {
        print("0");
        solPrint("import Graph from .fmi file");
        print("1");
        solPrint("manually create Graph");
        print("2");
        solPrint("run Dijkstra");
        print("3");
        solPrint("import Query from .que file");
        print("4");
        solPrint("manual query");
        print("5");
        solPrint("Run Difference Analysis form .sol file");
        print("6");
        solPrint("Run manual Difference Analysis");
    }

    public static void graphImportDialog() {
        print("Import Graph from .fmi File");
        print();
        print("Please provide path to .fmi File");
        print("E.g. /home/<USER>/downloads/<FILENAME>.fmi");
        sol("$");
        //TODO: input handler
    }

    public static void header(String version) {
        print("Dijkstra Navigator [Version " + version + "]");
        print("(c) Pascal Glaesser, Stephan Schroth. Licenced GNU Lesser GPL v3.0");
        print("Find this on Github: https://github.com/otakupasi/Navigator");
        print("Accessibility may be restricted while in development");
        print();
    }
}
