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
        try {
            String[] args = arg.split("&");
            FileLogger.syslog("" + args.length);
            FileLogger.syslog(args[0]);
            if (args.length < 2)
                return null;
            else {
                return webRoot.getPath() + "ok.html";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
