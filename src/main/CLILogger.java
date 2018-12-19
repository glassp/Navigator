package main;


/**
 * abstract class for logging
 */
public abstract class CLILogger {
    /**
     * the starttime for time tracking
     */
    public double startTime;
    /**
     * the runtime for a certain task
     */
    public double runtime;

    public static double runtimeInSeconds(double runtime) {
        return (runtime / 1000);
    }

    public static double runtimeInMinutes(double runtime) {
        return (double) Math.round((runtimeInSeconds(runtime) / 60) * 1000d) / 1000d;
    }


    /**
     * starts the timer
     */
    public void startTiming() {
        //reset runtime
        this.runtime = 0;
        this.startTime = System.currentTimeMillis();
    }

    /**
     * stops the times and calculates runtime
     */
    public void stop() {
        this.runtime = System.currentTimeMillis() - startTime;
    }

    /**
     * setting for verbose output
     */
    boolean verbose;

    /**
     * display debug level
     */
    boolean debug;


    /**
     * changes verbose to a certain setting
     *
     * @param verbose the setting
     */
    public void setVerbose(boolean verbose) {
        this.verbose = verbose;
    }

    /**
     * sets verbose to true
     */
    public void enableVerbose() {
        setVerbose(true);
    }

    /**
     * sets verbose to false
     */
    public void disableVerbose() {
        setVerbose(false);
    }

    /**
     * toogles verbose setting
     */
    public void toogleVerbose() {
        if (this.verbose) {
            disableVerbose();
        } else
            enableVerbose();
    }

    /**
     * changes debug to a certain setting
     *
     * @param debug the setting
     */
    public void setDebug(boolean debug) {
        this.debug = debug;
    }

    /**
     * sets debug to true
     */
    public void enableDebug() {
        setDebug(true);
    }

    /**
     * sets debug to false
     */
    public void disableDebug() {
        setDebug(false);
    }

    /**
     * toogles debug setting
     */
    public void toogleDebug() {
        if (this.debug) {
            disableDebug();
        } else
            enableDebug();
    }



    /**
     * changes all settings to a certain setting
     *
     * @param option the setting
     */
    public void setAll(boolean option) {
        setDebug(option);
        setVerbose(option);
    }

    /**
     * sets all settings to true
     */
    public void enableAll() {
        enableDebug();
        enableVerbose();
    }

    /**
     * sets all settings to false
     */
    public void disableAll() {
        disableDebug();
        disableVerbose();
    }

    /**
     * toogles all settings
     */
    public void toogleAll() {
        toogleDebug();
        toogleVerbose();
    }

    /**
     * prints a message if verbose is enabled
     *
     * @param msg the message
     */
    void verbosePrint(String msg) {
        if (this.verbose)
            CLI.print(msg);
    }

    /**
     * prints a formated message if verbose is enabled
     *
     * @param msg   the message
     * @param level the logging level
     */
    void verbosePrint(String msg, String level) {
        if (this.verbose) {
            String output = "[" + level + "] " + msg;
            if (level.equalsIgnoreCase("debug"))
                CLI.print(output, this.debug);
            if (level.equalsIgnoreCase("debugging"))
                CLI.print(output, this.debug);
            else
                CLI.print(output);
        }

    }

    /**
     * prints a formated message if verbose is enabled
     * Usage: only use to see execution order and exit point
     *
     * @param msg the message
     */
    void debugPrint(String msg) {
        verbosePrint(msg, "Debugging");
    }

    /**
     * prints a formated message if verbose is enabled
     *
     * @param msg the message
     */
    void warningPrint(String msg) {
        verbosePrint(msg, "Warning");
    }

    /**
     * prints a message
     *
     * @param msg the message
     */
    void print(String msg) {
        CLI.print(msg);
    }

}
