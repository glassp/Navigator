package server.util;

import java.io.File;
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
    private static String getFileExtension(File file) {
        String filename = file.getName();
        int pos = filename.lastIndexOf(".");
        if (pos >= 0) return filename.substring(pos).toLowerCase();
        return "";
    }
}
