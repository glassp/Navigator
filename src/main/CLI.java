package main;

public class CLI {
    //TODO
    public static void print(String msg) {
        System.out.println(msg);
    }

    public static void print(String msg, boolean printLevel) {
        if (printLevel)
            print(msg);
    }
}
