package server.api;

import server.util.FileLogger;

import java.io.File;

/**
 * Abstraction of a Resource
 * Should be used as a concept for writing backend bridge classes (those extending this class)
 * to be called by ApiHandler instead of the .api file
 */
public abstract class ApiResource {
    File webRoot;

    /**
     * Constructor
     *
     * @param webRoot path to the webRoot
     */
    ApiResource(File webRoot) {
        this.webRoot = webRoot;
    }

    /**
     * main actions to be performed
     *
     * @param args A StringArray serialized as a single String
     * @return path to the file where the server should load its data from instead of the .api file (forwarding)
     */
    public abstract String run(String args);

    /**
     * starts the main action of this Resource which is to be implemented within run(...)
     * It is preferable to call start rather than run as start catches all Exceptions
     *
     * @param arg A StringArray serialized as a single String
     * @return path to the file where the server should load its data from instead of the .api file (forwarding)
     */
    public String start(String arg) {
        try {
            return run(arg);
        } catch (Exception e) {
            FileLogger.syslog("Could not run Request. The following Exception occurred" + e.getMessage());
            return null;
        }
    }
}
