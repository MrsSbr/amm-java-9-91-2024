package ru.vsu.amm.java;

import java.io.File;
import java.net.URL;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Logger;

public class AnnotationScanner {
    private static final String PACKAGE_NOT_FOUND = "Package not found: ";
    private static final String INVALID_PACKAGE = "Invalid package: ";
    private static final String EXPANSION = ".class";
    private static final Logger logger = Logger.getLogger(AnnotationScanner.class.getName());

    public static Set<Class<?>> getClasses(String packageName) {
        Set<Class<?>> classes = new HashSet<>();

        try {
            File[] files = getFiles(packageName);
            if (files != null) {
                for (File file : files) {
                    if (file.isFile() && file.getName().endsWith(EXPANSION)) {
                        String className = packageName
                                + '.' + file.getName().replace(EXPANSION, "");

                        Class<?> clazz = Class.forName(className);

                        if (clazz.isAnnotationPresent(ExtractInterface.class)) {
                            classes.add(clazz);
                        }
                    }
                }
            }
        } catch (IllegalArgumentException | ClassNotFoundException e) {
            logger.severe(e.getMessage());
        }
        return classes;
    }

    private static File[] getFiles(String packageName) {
        String packageDirName = packageName.replace('.', '/');
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        URL resource = classLoader.getResource(packageDirName);
        if (resource == null) {
            throw new IllegalArgumentException(PACKAGE_NOT_FOUND + packageName);
        }

        File directory = new File(resource.getFile());
        if (!directory.exists() || !directory.isDirectory()) {
            throw new IllegalArgumentException(INVALID_PACKAGE + packageName);
        }

        return directory.listFiles();
    }
}
