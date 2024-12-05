package ru.vsu.amm.java.scanner;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ClassScanner {

    private static final Logger LOGGER = Logger.getLogger(ClassScanner.class.getName());
    private final String path;

    public ClassScanner(String path) {
        this.path = path;
    }

    public List<Class<?>> scan(String packageName) {
        String packagePath = path + "/" + packageName.replace('.', '/');
        File directory = new File(packagePath);
        List<Class<?>> classes = new ArrayList<>();
        if (directory.exists() && directory.isDirectory()) {
            File[] files = directory.listFiles();
            if (files != null) {
                for (File file : files) {
                    if (file.isDirectory()) {
                        classes.addAll(scan(packageName + "." + file.getName()));
                    } else if (file.getName().endsWith(".java")) {
                        String className = packageName + "." + file.getName().replace(".java", "");
                        try {
                            classes.add(Class.forName(className));
                        } catch (ClassNotFoundException e) {
                            System.out.println("Not found class by name: " + className);
                            LOGGER.log(Level.SEVERE, Arrays.toString(e.getStackTrace()));
                        }
                    }
                }
            }
        }
        return classes;
    }
}
