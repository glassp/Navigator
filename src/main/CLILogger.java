package main;


/**
 * Abstract Class for logging and calculation runtime
 */
public abstract class CLILogger {
    /**
     * the start time for time tracking
     */
    public double startTime;
    /**
     * the runtime for a certain task
     */
    public double runtime;
    /**
     * Setting for verbose output
     */
    boolean verbose;
    /**
     * Setting for debug output
     */
    boolean debug;

    /**
     * Converts the runtime to seconds
     *
     * @param runtime the runtime in ms
     * @return The runtime in seconds
     */
    public static double runtimeInSeconds(double runtime) {
        return (runtime / 1000);
    }

    /**
     * Converts the runtime to minutes
     * @param runtime the runtime in ms
     * @return The runtime in minutes
     */
    public static double runtimeInMinutes(double runtime) {
        return (double) Math.round((runtimeInSeconds(runtime) / 60) * 1000d) / 1000d;
    }

    /**
     * Starts the Timing
     */
    public void startTiming() {
        //reset runtime
        this.runtime = 0;
        this.startTime = System.currentTimeMillis();
    }

    /**
     * Stops the times and calculates runtime
     */
    public void stop() {
        this.runtime = System.currentTimeMillis() - startTime;
    }

    /**
     * Changes verbose to a certain setting
     *
     * @param verbose the setting
     */
    public void setVerbose(boolean verbose) {
        this.verbose = verbose;
    }

    /**
     * Sets verbose to true
     */
    public void enableVerbose() {
        setVerbose(true);
    }

    /**
     * Sets verbose to false
     */
    public void disableVerbose() {
        setVerbose(false);
    }

    /**
     * Toggles verbose setting
     */
    public void toggleVerbose() {
        if (this.verbose) {
            disableVerbose();
        } else
            enableVerbose();
    }

    /**
     * Changes debug to a certain setting
     *
     * @param debug the setting
     */
    public void setDebug(boolean debug) {
        this.debug = debug;
    }

    /**
     * Sets debug to true
     */
    public void enableDebug() {
        setDebug(true);
    }

    /**
     * Sets debug to false
     */
    public void disableDebug() {
        setDebug(false);
    }

    /**
     * Toggles debug setting
     */
    public void toggleDebug() {
        if (this.debug) {
            disableDebug();
        } else
            enableDebug();
    }


    /**
     * Changes all settings to a certain setting
     *
     * @param option the setting
     */
    public void setAll(boolean option) {
        setDebug(option);
        setVerbose(option);
    }

    /**
     * Sets all settings to true
     */
    public void enableAll() {
        enableDebug();
        enableVerbose();
    }

    /**
     * Sets all settings to false
     */
    public void disableAll() {
        disableDebug();
        disableVerbose();
    }

    /**
     * Toggles all settings
     */
    public void toggleAll() {
        toggleDebug();
        toggleVerbose();
    }

    /**
     * Prints a message if verbose is enabled
     *
     * @param msg the message
     * @see CLI#print(String)
     */
    void verbosePrint(String msg) {
        if (this.verbose)
            CLI.print(msg);
    }

    /**
     * Prints a formated message if verbose is enabled
     *
     * @param msg   the message
     * @param level the logging level
     * @see CLI#print(String, boolean)
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
     * Prints a formated message if verbose is enabled
     * Usage: only use to see execution order and exit point
     *
     * @param msg the message
     */
    void debugPrint(String msg) {
        verbosePrint(msg, "Debugging");
    }

    /**
     * Prints a formated message if verbose is enabled
     *
     * @param msg the message
     */
    void warningPrint(String msg) {
        verbosePrint(msg, "Warning");
    }

    /**
     * Prints a message
     *
     * @param msg the message
     * @see CLI#print(String)
     */
    void print(String msg) {
        CLI.print(msg);
    }

    /**
     * Prints a empty line
     * @see CLI#print()
     */
    void print() {
        CLI.print();
    }

}
