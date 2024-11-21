package ru.vsu.amm.java.service.impl;

import org.reflections.Reflections;
import ru.vsu.amm.java.annotation.Component;
import ru.vsu.amm.java.service.ComponentScanningService;

import java.util.Set;

public class ComponentScanningServiceImpl implements ComponentScanningService {

    private static final String packagePath = "ru.vsu.amm.java";

    @Override
    public Set<Class<?>> scanProject() {
        Reflections reflections = new Reflections(packagePath);
        Set<Class<?>> componentClasses = reflections.getTypesAnnotatedWith(Component.class);
        return componentClasses;
    }
}
