package main;

/**
 * entry point for class
 */
public class Main {
    /**
     * the main method
     *
     * @param args the arguments
     */
    public static void main(String[] args) {
        //TODO: add switch for cli and bench
        CLI cli = new CLI();
        cli.header("0.1 - dev build");
        cli.mainMenu();
    }
}
