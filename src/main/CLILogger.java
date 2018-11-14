package main;

public abstract class CLILogger {
    /**
     * setting for verbose output
     */
    private boolean verbose;

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
     * prints a message if verbose is enabled
     *
     * @param msg the message
     */
    private void verbosePrint(String msg) {
        if (this.verbose)
            CLI.print(msg);
    }

    /**
     * prints a formated message if verbose is enabled
     *
     * @param msg   the message
     * @param level the logging level
     */
    private void verbosePrint(String msg, String level) {
        if (this.verbose) {
            String output = "[" + level + "] " + msg;
            CLI.print(output);
        }

    }

    /**
     * prints a formated message if verbose is enabled
     *
     * @param msg the message
     */
    private void debugPrint(String msg) {
        verbosePrint(msg, "Debugging");
    }

    /**
     * prints a formated message if verbose is enabled
     *
     * @param msg the message
     */
    private void infoPrint(String msg) {
        verbosePrint(msg, "Info");
    }

    /**
     * prints a formated message if verbose is enabled
     *
     * @param msg the message
     */
    private void warningPrint(String msg) {
        verbosePrint(msg, "Warning");
    }

    /**
     * prints a message
     *
     * @param msg the message
     */
    private void print(String msg) {
        CLI.print(msg);
    }
}
