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
        CLI cli = new CLI();
        if (args.length == 3) {
            cli.fullRun(args[0], args[1], args[2]);
        } else {
            cli.header("0.1 - dev build");
            cli.mainMenu();
        }
    }
}
