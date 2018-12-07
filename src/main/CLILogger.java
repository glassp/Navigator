package main;

public abstract class CLILogger {
    /**
     * the starttime for time tracking
     */
    public double startTime;
    /**
     * the runtime for a certain task
     */
    public double runtime;

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
    private boolean verbose;

    /**
     * display debug level
     */
    private boolean debug;

    /**
     * display info level
     */
    private boolean info;

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
     * changes info to a certain setting
     *
     * @param info the setting
     */
    public void setInfo(boolean info) {
        this.info = info;
    }

    /**
     * sets info to true
     */
    public void enableInfo() {
        setInfo(true);
    }

    /**
     * sets info to false
     */
    public void disableInfo() {
        setInfo(false);
    }

    /**
     * toogles info setting
     */
    public void toogleInfo() {
        if (this.info) {
            disableInfo();
        } else
            enableInfo();
    }

    /**
     * changes all settings to a certain setting
     *
     * @param option the setting
     */
    public void setAll(boolean option) {
        setDebug(option);
        setInfo(option);
        setVerbose(option);
    }

    /**
     * sets all settings to true
     */
    public void enableAll() {
        enableDebug();
        enableInfo();
        enableVerbose();
    }

    /**
     * sets all settings to false
     */
    public void disableAll() {
        disableDebug();
        disableInfo();
        disableVerbose();
    }

    /**
     * toogles all settings
     */
    public void toogleAll() {
        toogleDebug();
        toogleInfo();
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
            if (level.equalsIgnoreCase("info"))
                CLI.print(output, this.info);
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
     * Usage: only use to inform about changes to values or objects
     *
     * @param msg the message
     */
    void infoPrint(String msg) {
        verbosePrint(msg, "Info");
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
