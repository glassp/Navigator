package server.api.main;

import server.api.util.JavaClassLoader;
import server.util.FileManager;

import java.io.File;

public class ApiHandler {
    public boolean canHandle(File file) {
        return isJava(file) && extendsApiResource(file);
    }

    private boolean isJava(File file) {
        return FileManager.getFileExtension(file).equals("java");
    }

    private boolean extendsApiResource(File file) {
        //TODO: read file line by line and search for class declaration then check if extends ApiResource is true
        return true;
    }

    public String handle(File file, String... params) {

        String className = FileManager.getFileName(file);

        JavaClassLoader javaClassLoader = new JavaClassLoader();
        Object o = javaClassLoader.invokeClassMethod(className, "start", params);
        if (o == null) {
            return null;
        }
        return o.toString();
    }

}
