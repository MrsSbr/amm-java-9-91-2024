package ru.vsu.amm.java.configuration;

import ru.vsu.amm.java.annotation.Autowired;
import ru.vsu.amm.java.service.impl.ComponentScanningServiceImpl;
import ru.vsu.amm.java.service.impl.FirstServiceImpl;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;

public class Context {

    private Map<Class<?>, Object> beans = new HashMap<>();

    private static final Logger log;

    static {
        log = Logger.getLogger(Context.class.getName());
    }

    public Context() {
        log.info("Call Context constructor");
        var componentScanningService = new ComponentScanningServiceImpl();
        log.info(componentScanningService.scanProject().toString());
        init(componentScanningService.scanProject());
    }

    private void init(Set<Class<?>> componentClasses) {
        log.info("Call init() from Context");
        for (Class<?> cl : componentClasses) {
            try {
                Object instance = cl.getDeclaredConstructor().newInstance();
                beans.put(cl, instance);
                initAutowiredFields(instance);
            } catch (Exception e) {
                log.info("Error in init() from Context");
                log.info(e.getMessage());
                e.printStackTrace();
            }
        }
    }

    private void initAutowiredFields(Object instance) {
        log.info("Call initAutoWiredFields() from Context");
        Field[] fields = instance.getClass().getDeclaredFields();
        for (Field field : fields) {
            if (field.isAnnotationPresent(Autowired.class)) {
                try {
                    field.setAccessible(true);
                    field.set(instance, getBean(field.getType()));
                } catch (Exception e) {
                    log.info("Error in initAutoWiredFields() from Context");
                    log.info(e.getMessage());
                    e.printStackTrace();
                }
            }
        }
    }

    public <T> T getBean(Class<T> tClass) {
        log.info("Call getBean() from Context");
        return (T) beans.get(tClass);
    }
}
