package ru.vsu.amm.java;

import org.reflections.Reflections;
import org.reflections.scanners.Scanners;

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
        Reflections reflections = new Reflections(packageName, Scanners.TypesAnnotated);
        Set<Class<?>> classes = reflections.getTypesAnnotatedWith(ExtractInterface.class);
        for (Class<?> aClass : classes) {
            interfaces.put(aClass, extractInterfaces(aClass));
        }
        return interfaces;
    }
}
