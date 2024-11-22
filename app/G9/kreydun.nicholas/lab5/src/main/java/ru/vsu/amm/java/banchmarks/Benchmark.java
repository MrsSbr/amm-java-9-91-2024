package ru.vsu.amm.java.banchmarks;

import ru.vsu.amm.java.Annotation.Benchmarked;
import ru.vsu.amm.java.service.Logg;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;

public class Benchmark {

    // Статистика по методам
    private static final Map<String, Long> methodExecutionTimes = new HashMap<>();

    public static <T> T track(T service) throws Exception {
        short NUM_METHOD = 0;
        // Создаем класс обработчика с помощью рефлексии
        Class<?> handlerClass = Class.forName(BenchmarkHandler.class.getName());

        // Создаем конструктор обработчика, принимающий объект service
        Constructor<?> constructor = handlerClass.getConstructor(Object.class);

        // Создаем экземпляр обработчика с использованием рефлексии
        Object handler = constructor.newInstance(service);

        Logg.logger.fine("tracking with " + Benchmark.class.getDeclaredMethods()[NUM_METHOD].getName());
        // Создаем прокси-объект
        return (T) Proxy.newProxyInstance(
                service.getClass().getClassLoader(),
                service.getClass().getInterfaces(),
                (InvocationHandler) handler
        );
    }

    // Получение статистики по методу
    public static Stat getStat(Object service) {

        Logg.logger.fine("Return " + service.getClass().getName());
        return new Stat(methodExecutionTimes);
    }

    // Обработчик для методов, помеченных аннотацией @Benchmarked
    private static class BenchmarkHandler implements InvocationHandler {
        private final Object target;

        public BenchmarkHandler(Object target) {
            this.target = target;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            // Если метод помечен аннотацией @Benchmarked, измеряем его время
            if (method.isAnnotationPresent(Benchmarked.class)) {
                long start = System.nanoTime();
                Object result = method.invoke(target, args);
                long end = System.nanoTime();
                long duration = end - start;

                // Записываем время выполнения в статистику
                methodExecutionTimes.put(method.getName(), duration);

                Logg.logger.fine("Method " + method.getName() + " took " + duration + " nanoseconds");

                return result;
            } else {

                Logg.logger.fine("Working " + method.getName() + "without annotation");
                return method.invoke(target, args); // Для методов без аннотации — обычный вызов
            }
        }
    }
}