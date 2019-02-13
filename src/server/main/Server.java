package server.main;

import server.util.FileLogger;
import server.util.ServerHelper;

import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;

public class Server {

    /**
     * Constructor
     *
     * @param port                  the port
     * @param webRoot               the root dir for the web
     * @param allowDirectoryListing if directoryListing should be enabled
     * @param logfile               the path to the Logfile
     */
    public Server(int port, final File webRoot, final boolean allowDirectoryListing, File logfile) {
        FileLogger.setLogfile(logfile);

        FileLogger.syslog("Starting server...");


        //initialize variables
        String lineOne;
        String lineUrl = "### http://" + ServerHelper.getServerIp() + ":" + port + " ###";
        String lineTitle;

        //initialize lineOne with StringBuilder + for-loop
        StringBuilder lineOneBuilder = new StringBuilder();
        for (int i = 0; i < lineUrl.length(); i++) lineOneBuilder.append("#");
        lineOne = lineOneBuilder.toString();

        //initialize lineTitlw with StringBuilder + for-loop
        StringBuilder lineTitleBuilder = new StringBuilder("### HTTP-Server");
        for (int i = 0; i < lineUrl.length() - 18; i++) lineTitleBuilder.append(" ");
        lineTitle = lineTitleBuilder.toString();

        lineTitle += "###";


        //Output information on console
        System.out.println(lineOne);
        System.out.println(lineTitle);
        System.out.println(lineUrl);
        System.out.println(lineOne);


        //Check for webRoot dir or create it
        if (!webRoot.exists() && !webRoot.mkdir()) {
            // Neither does dir exist nor could it be created
            FileLogger.exception("Could not generate output directory");
            FileLogger.exception("Terminating...");
            System.exit(1);
        }

        // Create ServerSocket at given port
        ServerSocket socket = null;
        try {
            socket = new ServerSocket(port);
        } catch (IOException | IllegalArgumentException e) {
            // Port is already used => terminate
            FileLogger.exception(e.getMessage());
            FileLogger.exception("Terminating...");
            System.exit(1);
        }

        // thread awaits incoming connection and 'redirects' it to ServerThread instance
        final ServerSocket finalSocket = socket;
        Thread connectionListener = new Thread(() -> {
            while (true) {
                try {
                    ServerThread thread = new ServerThread(finalSocket.accept(), webRoot, allowDirectoryListing);
                    thread.start();
                } catch (IOException e) {
                    FileLogger.exception(e.getMessage());
                    FileLogger.exception("Terminating...");
                    System.exit(1);
                }
            }
        });
        connectionListener.start();
    }
}
