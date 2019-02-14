package server.api;

import server.util.FileLogger;

import java.io.File;

public class TestResource extends ApiResource {

    public TestResource(File webRoot) {
        super(webRoot);
    }

    @Override
    public String run(String arg) {
        FileLogger.syslog("TestResource did run");
        return "./.ok.html";
    }
}
