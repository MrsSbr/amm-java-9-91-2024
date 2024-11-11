package ru.vsu.amm.java;

import org.reflections.Reflections;
import ru.vsu.amm.java.annotation.Component;
import ru.vsu.amm.java.annotation.service.ComponentScanningService;

import java.util.Set;

public class ComponentScanningServiceImpl implements ComponentScanningService {

    @Override
    public Set<Class<?>> scanProject() {
        Reflections reflections = new Reflections("ru.vsu.amm.java");
        Set<Class<?>> componentClasses = reflections.getTypesAnnotatedWith(Component.class);
        return componentClasses;
    }
}
