package server.api;

import main.Graph;
import server.util.FileManager;
import server.util.JavaClassLoader;

import java.io.File;

/**
 * Class that Handles calls to .api files where the file name is the qualified name of that class
 * e.g. package: com.example, class: Example, filename+fileext: com.example.Example.api
 */
public class ApiHandler {

    private File webRoot;
    private Graph graph;

    /**
     * Constructor
     *
     * @param webRoot path to the webRoot dir
     * @param graph   the graph
     */
    public ApiHandler(File webRoot, Graph graph) {
        this.webRoot = webRoot;
        this.graph = graph;
    }

    /**
     * checks if file is a API file
     *
     * @param file The file
     * @return true if ApiHandler can handle request to this file and forward it to the backend
     */
    public boolean canHandle(File file) {
        return isApiFile(file);
    }

    /**
     * compiles and loads the file at runtime then executes method start with parameter param
     * @param file the file
     * @param param the parameter
     * @return path to the file where the server should load its data from instead of the .api file (forwarding)
     */
    public String handle(File file, String param) {
        String className = FileManager.getFileName(file);
        if (className.startsWith("."))
            className = className.substring(1);
        JavaClassLoader classLoader = new JavaClassLoader();
        Object instance = classLoader.getInstance(className, webRoot, graph);

        if (instance == null)
            return "";

        Object obj = classLoader.invokeMethod(instance, "start", param);
        try {
            return (String) obj;
        } catch (ClassCastException e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * checks if file has  file extension .api
     *
     * @param file the file to check
     * @return if file ext is .api
     */
    private boolean isApiFile(File file) {
        return FileManager.getFileExtension(file).equals(".api");
    }

}
