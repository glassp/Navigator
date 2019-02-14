package server.main;

import server.api.ApiHandler;
import server.util.FileLogger;
import server.util.FileManager;
import server.util.ServerHelper;

import java.io.*;
import java.net.Socket;
import java.net.SocketException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

public class ServerThread extends Thread {
    private boolean allowDirectoryListing;
    private Socket socket;
    private File webRoot;

    /**
     * Constructor
     *
     * @param socket                the socket
     * @param webRoot               path to webRoot dir
     * @param allowDirectoryListing if fileListing should be allowed
     */
    ServerThread(Socket socket, File webRoot, boolean allowDirectoryListing) {
        this.socket = socket;
        this.webRoot = webRoot;
        this.allowDirectoryListing = allowDirectoryListing;
    }

    /**
     * Core program handling incoming requests and sending responses
     */
    public void run() {
        //initializing streams
        final BufferedReader in;
        final BufferedOutputStream out;
        try {
            in = new BufferedReader(new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
            out = new BufferedOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            FileLogger.exception(e.getMessage());
            return;
        }

        //should prevent transfer of large files and provide very very very basic protection against DOS attacks targeting server overloading
        try {
            //socket timeout 30s
            //should change this to 10 - 15s if used in production mode.
            socket.setSoTimeout(30000);
        } catch (SocketException e) {
            FileLogger.exception(e.getMessage());
        }

        // Read request line per line
        String line;
        ArrayList<String> request = new ArrayList<>();
        try {
            while ((line = in.readLine()) != null && line.length() != 0 && !line.equals("")) {
                request.add(line);
            }
        } catch (IOException e) {
            // Request could not be read (correctly)
            sendError(out, 400, "Bad Request");
            FileLogger.exception(e.getMessage());
            return;
        }

        // Handle Empty request.
        if (request.isEmpty()) {
            //Normally 400 should be send but empty request would have been catched in earlier try-catch => error in code => use 500
            //sendError(out, 400, "Bad Request");
            sendError(out, 500, "Internal Server Error");
            FileLogger.exception("The request was empty");
            return;
        }

        // check if protocol is of version 1.0 or 1.1
        if (!request.get(0).endsWith(" HTTP/1.0") && !request.get(0).endsWith(" HTTP/1.1")) {
            sendError(out, 400, "Bad Request");
            FileLogger.error(400, "Bad Request: " + request.get(0), socket.getInetAddress().toString());
            return;
        }

        // check which method was used
        boolean isPostRequest = false;
        if (!request.get(0).startsWith("GET ")) {
            if (request.get(0).startsWith("POST ")) {
                isPostRequest = true;
            } else {
                // method is neither POST nor GET
                sendError(out, 501, "Not Implemented");
                FileLogger.error(501, "Not Implemented: " + request.get(0), socket.getInetAddress().toString());
                return;
            }
        }

        // Where to access
        String wantedFile;
        String path;
        File file;
        String param = "";

        // check filename
        wantedFile = request.get(0).substring(4, request.get(0).length() - 9);
        if (isPostRequest) wantedFile = request.get(0).substring(5, request.get(0).length() - 9);

        // remove GET-parameters from filename
        if (!isPostRequest && request.get(0).contains("?")) {
            param = wantedFile.substring(wantedFile.lastIndexOf("?"));
            path = wantedFile.substring(0, wantedFile.indexOf("?"));
        } else {
            path = wantedFile;
        }

        // check path of file
        try {
            file = new File(webRoot, URLDecoder.decode(path, StandardCharsets.UTF_8)).getCanonicalFile();
        } catch (IOException e) {
            FileLogger.exception(e.getMessage());
            return;
        }

        // check if dir should be accessed and redirect to index.html
        if (file.isDirectory() && !allowDirectoryListing) {
            File indexFile = new File(file, "index.html");
            if (indexFile.exists() && !indexFile.isDirectory()) {
                file = indexFile;

                // append "/index.html" to URL and move GET-parameters after it
                if (wantedFile.contains("?")) {
                    wantedFile = wantedFile.substring(0, wantedFile.indexOf("?")) + "/index.html" + param;
                }
            }
        }

        //check if access outside of root
        if (!file.toString().startsWith(ServerHelper.getCanonicalWebRoot(webRoot))) {

            // access denied because of 403

            sendError(out, 403, "Forbidden");
            FileLogger.error(403, wantedFile, socket.getInetAddress().toString());
            return;
        } else if (!file.exists()) {

            // file does not exist 404

            sendError(out, 404, "Not Found");
            FileLogger.error(404, wantedFile, socket.getInetAddress().toString());
            return;
        } else if (file.isDirectory()) {

            // access to dir within webRoot => check for dirListing

            if (!allowDirectoryListing) {
                // access denied because of 403
                sendError(out, 403, "Forbidden");
                FileLogger.error(403, wantedFile, socket.getInetAddress().toString());
                return;

            }

            // replace "%20" spaces with real spaces
            path = path.replace("%20", " ");

            File[] files = file.listFiles();

            // if file empty/ not found send 404
            if (files != null) {
                if (files.length == 0) {
                    sendError(out, 404, "Not Found");
                    FileLogger.error(404, wantedFile, socket.getInetAddress().toString());
                    return;
                }
            } else {
                // access to protected OS dir causes files to be null => access denied
                sendError(out, 403, "Forbidden");
                FileLogger.error(403, wantedFile, socket.getInetAddress().toString());
                return;
            }

            // Sort alphabetically first dir then files
            Arrays.sort(files, (file1, file2) -> {
                if (file1.isDirectory() && !file2.isDirectory()) {
                    return -1;
                } else if (!file1.isDirectory() && file2.isDirectory()) {
                    return 1;
                } else {
                    return file1.toString().compareToIgnoreCase(file2.toString());
                }
            });

            // prepare output in table
            String content = "<table><tr><th></th><th>Name</th><th>Last modified</th><th>Size</th></tr>";

            // while inside subdir of webRoot add entry "parent dir"
            if (!path.equals("/")) {
                String parentDirectory = path.substring(0, path.length() - 1);
                int lastSlash = parentDirectory.lastIndexOf("/");
                if (lastSlash > 1) {
                    parentDirectory = parentDirectory.substring(0, lastSlash);
                } else {
                    parentDirectory = "/";
                }

                content += "<tr><td class=\"center\"><div class=\"back\">&nbsp;</div></td>" +
                        "<td><a href=\"" + parentDirectory.replace(" ", "%20") + "\">Parent Directory</a></td>" +
                        "<td></td>" +
                        "<td></td></tr>";
            }

            if (path.equals("/")) path = "";

            // foreach file: add to output
            StringBuilder contentBuilder = new StringBuilder(content);
            for (File myFile : files) {
                // get meta-data
                String filename = myFile.getName();
                String img;
                String fileSize = FileManager.getReadableFileSize(myFile.length());

                //hide files and dirs that start with "." from listing.
                if (!filename.startsWith(".")) {
                    if (myFile.isDirectory()) {
                        img = "<div class=\"folder\">&nbsp;</div>";
                        fileSize = "";
                    } else {
                        img = "<div class=\"file\">&nbsp;</div>";
                    }
                    // build table entry
                    contentBuilder.append("<tr><td class=\"center\">").append(img).append("</td>").append("<td><a href=\"").append(path.replace(" ", "%20")).append("/").append(filename.replace(" ", "%20")).append("\">").append(filename).append("</a></td>").append("<td>").append(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(myFile.lastModified())).append("</td>").append("<td class=\"center\">").append(fileSize).append("</td></tr>");

                }

            }
            content = contentBuilder.toString();

            if (path.equals("")) path = "/";

            // close table and finish output
            content += "</table>";
            String output = WebResource.getDirectoryTemplate("Index of " + path, content);

            if (!wantedFile.endsWith("/")) wantedFile += "/";

            // send header
            sendHeader(out, 200, "OK", "text/html", -1, System.currentTimeMillis());
            FileLogger.access(wantedFile, socket.getInetAddress().toString());
            try {
                out.write(output.getBytes());
            } catch (IOException e) {
                FileLogger.exception(e.getMessage());
            }
        } else {
            // access to file within webDir

            ApiHandler apiHandler = new ApiHandler(webRoot);
            if (apiHandler.canHandle(file)) {
                String tmp = apiHandler.handle(file, param);

                FileLogger.syslog("tmp: " + tmp);

                if (tmp != null && !tmp.equals("&nbsp;")) {
                    File tmpFile = null;
                    try {
                        tmpFile = new File(webRoot, URLDecoder.decode(tmp, StandardCharsets.UTF_8)).getCanonicalFile();
                        file = tmpFile;
                    } catch (IOException e) {
                        //Ignore
                    }
                }
            }

            // initialize InputStream
            InputStream reader = null;
            try {
                reader = new BufferedInputStream(new FileInputStream(file));
            } catch (FileNotFoundException e) {
                FileLogger.exception(e.getMessage());
            }

            // check if file was suddenly deleted
            if (reader == null) {
                sendError(out, 404, "Not Found");
                FileLogger.error(404, wantedFile, socket.getInetAddress().toString());
                return;
            }

            // if contentType not predefined send as download stream
            String contentType = FileManager.getContentType(file);
            if (contentType == null) {
                //contentType = "application/octet-stream";
                contentType = "text/plain";
            }

            // send data and content
            sendHeader(out, 200, "OK", contentType, file.length(), file.lastModified());
            FileLogger.access(wantedFile, socket.getInetAddress().toString());
            try {
                byte[] buffer = new byte[4096];
                int bytesRead;
                while ((bytesRead = reader.read(buffer)) != -1) {
                    out.write(buffer, 0, bytesRead);
                }
                reader.close();
            } catch (NullPointerException | IOException e) {
                FileLogger.exception(e.getMessage());
            }
        }

        // Clean up
        try {
            out.flush();
            out.close();
        } catch (IOException e) {
            FileLogger.exception(e.getMessage());
        }
    }

    /**
     * Sends a Error Landing Page
     *
     * @param out     used output stream
     * @param code    HTTP Status-Code (e.g. 500)
     * @param message HTTP Status-Message of HTTP Status-Code (e.g. Internal Server Error)
     */
    private void sendError(BufferedOutputStream out, int code, String message) {
        // Prepare output
        String output = WebResource.getErrorTemplate("Error " + code + ": " + message);

        // send header
        sendHeader(out, code, message, "text/html", output.length(), System.currentTimeMillis());

        try {
            // send response then cleanup
            out.write(output.getBytes());
            out.flush();
            out.close();

            // close socket. No keep-alive.
            socket.close();
        } catch (IOException e) {
            FileLogger.exception(e.getMessage());
        }
    }

    /**
     * Sends a HTTP 1.1 header
     *
     * @param out           used output stream
     * @param code          HTTP Status-Code (e.g. 200)
     * @param codeMessage   HTTP Status-Message of HTTP Status-Code (e.g. Ok)
     * @param contentType   Content-Type Header
     * @param contentLength Content-Length Header
     * @param lastModified  Last modified Meta-Data (used for caching)
     */
    private void sendHeader(BufferedOutputStream out, int code, String codeMessage, String contentType, long contentLength, long lastModified) {
        try {
            out.write(("HTTP/1.1 " + code + " " + codeMessage + "\r\n" +
                    "Date: " + new Date().toString() + "\r\n" +
                    "Server: HTTP-Server\r\n" +
                    "Content-Type: " + contentType + "; charset=utf-8\r\n" +
                    ((contentLength != -1) ? "Content-Length: " + contentLength + "\r\n" : "") +
                    "Last-modified: " + new Date(lastModified).toString() + "\r\n" +
                    "\r\n").getBytes());
        } catch (IOException e) {
            FileLogger.exception(e.getMessage());
        }
    }
}
