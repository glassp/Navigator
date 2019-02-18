package server.util;

import main.Graph;

import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;
import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * class for compiling and loading at runtime (dynamic loading)
 * inspired by autoloading in php
 */
public class JavaClassLoader extends ClassLoader {

    /**
     * returns a object of given class
     *
     * @param fullClassName the qualified class name
     * @param webRoot       the path to webroot
     * @param graph         the graph
     * @return instance of class
     */
    public Object getInstance(String fullClassName, File webRoot, Graph graph) {

        String sourcePath = FileManager.getProjectRoot() + "src/" + FileManager.packageNameToPath(fullClassName) + ".java";
        String compilePath = FileManager.getProjectRoot() + "src/" + FileManager.packageNameToPath(fullClassName) + ".class";
        File sourceFile = new File(sourcePath);

        // Compile source file.
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        FileLogger.syslog("Compiling file at " + sourceFile.getPath());
        int res = compiler.run(null, null, null, sourceFile.getPath());

        if (res != 0)
            return null;
        // Load and instantiate compiled class.
        try {
            FileLogger.syslog("Try to load class " + fullClassName);
            ClassLoader classLoader = this.getClass().getClassLoader();
            Class<?> cls = Class.forName(fullClassName, true, classLoader);
            Constructor constructor = cls.getConstructor(webRoot.getClass(), Graph.class);
            return constructor.newInstance(webRoot, graph);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * invokes method on given class instance
     *
     * @param instance   the instance to invoke the method on
     * @param methodName the method name without ()
     * @param param      the parameter as Object e.g String
     * @return
     */
    public Object invokeMethod(Object instance, String methodName, Object param) {
        FileLogger.syslog("Try to load " + methodName + " on Object " + instance);
        Class<?> cls = instance.getClass();
        try {
            Method method = cls.getMethod(methodName, param.getClass());
            return method.invoke(instance, param);
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
            return null;
        }
    }
}
