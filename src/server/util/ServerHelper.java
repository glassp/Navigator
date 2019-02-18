package server.util;

import java.io.File;
import java.io.IOException;
import java.net.Inet4Address;
import java.net.UnknownHostException;

/**
 * helper for the server
 */
public class ServerHelper {

    /**
     * Returns ip of server in local network
     *
     * @return ip address of server
     */
    public static String getServerIp() {
        try {
            return Inet4Address.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            FileLogger.exception(e.getMessage());
            return "127.0.0.1";
        }
    }

    /**
     * returns absolute path to webroot
     *
     * @return path
     */
    public static String getCanonicalWebRoot(File webRoot) {
        String canonicalWebRoot = "";
        try {
            canonicalWebRoot = webRoot.getCanonicalPath();
        } catch (IOException e) {
            FileLogger.exception(e.getMessage());
        }
        return canonicalWebRoot;
    }
}
