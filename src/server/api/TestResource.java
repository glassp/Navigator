package server.api;

import main.Graph;
import server.util.FileLogger;

import java.io.File;

public class TestResource extends ApiResource {
    public TestResource(File webRoot, Graph graph) {
        super(webRoot);
        //ignore Graph in this class
    }

    @Override
    public String run(String arg) {
        FileLogger.syslog("TestResource did run");
        return "./.ok.html";
    }
}
