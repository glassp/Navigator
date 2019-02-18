package server.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.util.HashMap;

public class FileManager {
    private static HashMap<String, String> mimeMap = new HashMap<>();
    private static boolean mimeTypesLoaded = false;

    /**
     * Initialize Hashmap with basic mime types that could be used with the server
     *
     * @return a Hashmap of mime-types
     */
    private static HashMap<String, String> getMimeMap() {
        if (mimeTypesLoaded) return mimeMap;

        mimeMap.put(".html", "text/html");
        mimeMap.put(".css", "text/css");
        mimeMap.put(".js", "text/js");

        mimeMap.put(".json", "text/json");
        mimeMap.put(".api", "test/html");
        mimeMap.put(".txt", "text/plain");
        mimeMap.put(".log", "text/plain");
        mimeMap.put(".java", "text/plain");

        mimeTypesLoaded = true;
        return mimeMap;
    }

    /**
     * Convertes filesize from bit to byte, kilobyte etc.
     *
     * @param size filesize (in bit)
     * @return filesize as String (e.g. "16KB")
     */
    public static String getReadableFileSize(long size) {
        if (size <= 0) return "0";

        final String[] units = new String[]{"B", "KB", "MB", "GB", "TB", "PB", "EB"};
        int digitGroups = (int) (Math.log10(size) / Math.log10(1024));
        return new DecimalFormat("#,##0.#").format(size / Math.pow(1024, digitGroups)) + " " + units[digitGroups];
    }

    /**
     * Returns Content-Type of file
     *
     * @param file the file in question
     * @return Content-Type of file
     */
    public static String getContentType(File file) {
        return getMimeMap().get(getFileExtension(file));
    }

    /**
     * Returns file extension
     *
     * @param file the file in question
     * @return file extension of file
     */
    public static String getFileExtension(File file) {
        String filename = file.getName();
        int pos = filename.lastIndexOf(".");
        if (pos >= 0) return filename.substring(pos).toLowerCase();
        return "";
    }

    public static String getFileName(File file) {
        String filename = file.getName();
        int pos = filename.lastIndexOf(".");
        if (pos >= 0) return filename.substring(0, pos);
        return "";
    }

    public static String getProjectRoot() {
        Path currentRelativePath = Paths.get("");
        String path = currentRelativePath.toAbsolutePath().toString();
        path = path.substring(0, path.indexOf("Navigator"));
        path += "Navigator/";
        return path;
    }

    public static String getFileDir(File file) {
        String path = file.getPath();
        int pos = path.lastIndexOf("/");
        if (pos >= 0) return path.substring(0, pos);
        return "";
    }

    public static String packageNameToPath(String packageName) {
        return packageName.replace(".", "/");
    }

    public static void file_put_contents(File path, String data) {
        path.delete();
        if (!path.exists()) {
            try {
                if (!path.createNewFile()) {
                    throw new IOException("Could not create File.");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            PrintWriter printWriter = new PrintWriter(new FileOutputStream(path));
            printWriter.flush();
            printWriter.append(data).append("\r\n");
            printWriter.close();
        } catch (IOException e) {
            //ignored
        }
    }
}
