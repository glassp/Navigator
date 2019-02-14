package server.api;

import java.io.File;

public abstract class ApiResource {
    protected File webRoot;

    public ApiResource(File webRoot) {
        this.webRoot = webRoot;
    }


    public String start(String arg) {
        try {
            return run(arg);
        } catch (Exception e) {
            return "Could not run Request. The following Exception occurred" + e.getMessage();
        }
    }

    public abstract String run(String args);
}
