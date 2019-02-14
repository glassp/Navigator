package server.util;

import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;
import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class JavaClassLoader extends ClassLoader {
    public Object getInstance(String fullClassName, File webRoot) {

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
            Constructor constructor = cls.getConstructor(webRoot.getClass());
            return constructor.newInstance(webRoot);
        } catch (Exception e) {
            return null;
        }
    }

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
