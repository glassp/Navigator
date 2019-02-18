package server.api;

import main.Graph;
import server.util.FileManager;
import server.util.JavaClassLoader;

import java.io.File;

public class ApiHandler {
    private File webRoot;
    private Graph graph;

    public ApiHandler(File webRoot, Graph graph) {
        this.webRoot = webRoot;
        this.graph = graph;
    }

    public boolean canHandle(File file) {
        return isApiFile(file);
    }

    private boolean isApiFile(File file) {
        return FileManager.getFileExtension(file).equals(".api");
    }

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

}
