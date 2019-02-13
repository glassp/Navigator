package server.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;

public class FileLogger {
    private static File logfile;

    /**
     * changes file to log into
     *
     * @param pLogfile logfile
     */
    public static void setLogfile(File pLogfile) {
        logfile = pLogfile;
    }

    /**
     * accesses a file or a directory
     *
     * @param file instance to be accessed
     * @param ip   client ip
     */
    public static void access(String file, String ip) {
        write("[200] [" + ip.replace("/", "") + "] " + file);
    }

    /**
     * log access error
     *
     * @param code HTTP Status-Code
     * @param file instance to be accessed
     * @param ip   client ip
     */
    public static void error(int code, String file, String ip) {
        write("[" + code + "] [" + ip.replace("/", "") + "] " + file);
    }

    /**
     * log internal server errors
     *
     * @param message message
     */
    public static void exception(String message) {
        write("[EXC] " + message);
    }

    /**
     * log system events
     *
     * @param message message
     */
    public static void syslog(String message) {
        write("[SYS] " + message);
    }

    /**
     * output message and write message into logfile
     *
     * @param message Inhalt der Meldung
     */
    private static void write(String message) {
        String out = "[" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(System.currentTimeMillis()) + "] " + message;

        System.out.println(out);

        try {
            PrintWriter printWriter = new PrintWriter(new FileOutputStream(logfile, true));
            printWriter.append(out).append("\r\n");
            printWriter.close();
        } catch (IOException e) {
            //if logging failes it will be ignored
        }
    }
}
