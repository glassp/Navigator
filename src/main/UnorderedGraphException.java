package main;

/**
 * Exception class for detecting unordered graphs
 */
public class UnorderedGraphException extends IllegalStateException {
    /**
     * Constructor
     *
     * @param msg a message
     */
    public UnorderedGraphException(String msg) {
        super(msg);
    }

}
