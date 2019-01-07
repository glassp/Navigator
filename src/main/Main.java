package main;

/**
 * The entry point for Script
 */
public class Main {
    /**
     * The main method
     *
     * @param args the arguments passed via terminal
     */
    public static void main(String[] args) {
        CLI cli = new CLI();
        if (args.length == 3) {
            cli.fullRun(args[0], args[1], args[2]);
        } else {
            cli.header("1.0 - beta version");
            cli.mainMenu();
        }
    }
}
