package ru.vsu.amm.java;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class Context {

    private Map<Class<?>, Object> beans = new HashMap<>();

    public Context() {
        init(SecondService.class, FirstService.class);
    }

    private void init(Class<?>... clazz) {
        for (Class<?> cl : clazz) {
            try {
                Object instance = cl.getDeclaredConstructor().newInstance();
                beans.put(cl, instance);
                initAutowiredFields(instance);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void initAutowiredFields(Object instance) {
        Field[] fields = instance.getClass().getDeclaredFields();
        for (Field field : fields) {
            if (field.isAnnotationPresent(Autowired.class)) {
                try {
                    field.setAccessible(true);
                    field.set(instance, getBean(field.getType()));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public <T> T getBean(Class<T> tClass) {
        return (T) beans.get(tClass);
    }
}
