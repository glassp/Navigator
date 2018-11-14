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

    @Override
    public void printStackTrace() {
        super.printStackTrace();
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }
}
