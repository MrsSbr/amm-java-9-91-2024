package ru.vsu.amm.java.context;

import org.reflections.Reflections;
import ru.vsu.amm.java.Main;
import ru.vsu.amm.java.annotations.Autowired;
import ru.vsu.amm.java.annotations.Component;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Context {

    private static final Logger logger = Logger.getLogger(Context.class.getName());
    private final Map<Class<?>, Object> context = new HashMap<>();

    public Context(String packagePath) {
        initializeContext(packagePath);
    }

    private void initializeContext(String packagePath) {
        Reflections reflections = new Reflections(packagePath);
        Set<Class<?>> componentClasses = reflections.getTypesAnnotatedWith(Component.class);
        try {
            for (Class<?> componentClass : componentClasses) {
                Object componentInstance = componentClass.getDeclaredConstructor().newInstance();
                context.put(componentClass, componentInstance);
                injectDependencies(componentInstance);
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, Arrays.toString(e.getStackTrace()));
            System.out.println("Error in Context.initializeContext method");
        }
    }

    private void injectDependencies(Object componentInstance) {
        Field[] fields = componentInstance.getClass().getDeclaredFields();
        try {
            for (Field field : fields) {
                if (field.isAnnotationPresent(Autowired.class)) {
                    field.setAccessible(true);
                    field.set(componentInstance, get(field.getType()));
                }
            }
        } catch (IllegalAccessException e) {
            logger.log(Level.SEVERE, Arrays.toString(e.getStackTrace()));
            System.out.println("Error in Context.injectDependencies method");
        }
    }

    public <T> T get(Class<T> classType) {
        return (T) context.get(classType);
    }
}
