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

    private static final Map<String, Long> methodExecutionTimes = new HashMap<>();

    public static <T> T track(T service) throws Exception {
        short numMethod = 0;

        Class<?> handlerClass = Class.forName(BenchmarkHandler.class.getName());

        Constructor<?> constructor = handlerClass.getConstructor(Object.class);

        Object handler = constructor.newInstance(service);

        Logg.logger.fine("tracking with " + Benchmark.class.getDeclaredMethods()[numMethod].getName());

        return (T) Proxy.newProxyInstance(
                service.getClass().getClassLoader(),
                service.getClass().getInterfaces(),
                (InvocationHandler) handler
        );
    }

    public static Stat getStat(Object service) {

        Logg.logger.fine("Return " + service.getClass().getName());
        return new Stat(methodExecutionTimes);
    }

    private static class BenchmarkHandler implements InvocationHandler {
        private final Object target;

        public BenchmarkHandler(Object target) {
            this.target = target;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

            if (method.isAnnotationPresent(Benchmarked.class)) {
                long start = System.nanoTime();
                Object result = method.invoke(target, args);
                long end = System.nanoTime();
                long duration = end - start;

                methodExecutionTimes.put(method.getName(), duration);

                Logg.logger.fine("Method " + method.getName() + " took " + duration + " nanoseconds");

                return result;
            } else {

                Logg.logger.fine("Working " + method.getName() + "without annotation");
                return method.invoke(target, args);
            }
        }
    }
}