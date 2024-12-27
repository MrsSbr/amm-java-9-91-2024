package ru.vsu.amm.java;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class InterfaceExtractor {
    public static List<Class<?>> extractInterfaces(Class<?> clazz) {
        List<Class<?>> interfaces = new ArrayList<>();
        if (clazz.isAnnotationPresent(ExtractInterface.class)) {
            interfaces.addAll(Arrays.asList(clazz.getInterfaces()));
        }
        return interfaces;
    }

    public static Map<Class<?>, List<Class<?>>> getInterfaces(String packageName) {
        Map<Class<?>, List<Class<?>>> interfaces = new HashMap<>();
        Set<Class<?>> classes = AnnotationScanner.getClasses(packageName);

        for (Class<?> aClass : classes) {
            interfaces.put(aClass, extractInterfaces(aClass));
        }
        return interfaces;
    }
}
