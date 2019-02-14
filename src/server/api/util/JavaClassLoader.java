package server.api.util;

import server.util.FileLogger;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

public class JavaClassLoader extends ClassLoader {

    public Object invokeClassMethod(String classBinName, String methodName, String... params) {

        try {

            // Create a new JavaClassLoader
            ClassLoader classLoader = this.getClass().getClassLoader();

            // Load the target class using its binary name
            Class loadClass = classLoader.loadClass(classBinName);

            FileLogger.syslog("Loaded class: " + classBinName);

            // Create a new instance from the loaded class
            Constructor constructor = loadClass.getConstructor();
            Object instance = constructor.newInstance();

            // Getting the target method from the loaded class and invoke it using its name
            Class<?> arrayClass = String[].class;
            Method method = loadClass.getMethod(methodName, arrayClass);
            FileLogger.syslog("Invoce method: " + methodName);
            return method.invoke(instance, params);


        } catch (Exception e) {
            FileLogger.exception(e.getMessage());
            return null;
        }

    }
}