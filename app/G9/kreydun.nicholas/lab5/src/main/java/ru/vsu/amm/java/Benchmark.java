package ru.vsu.amm.java;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;

public class Benchmark {

    private static final Map<Object, Stat> stats = new HashMap<>();

    public static <T> T track(T target) {
        return (T) Proxy.newProxyInstance(
                target.getClass().getClassLoader(),
                target.getClass().getInterfaces(),
                new BenchmarkHandler<>(target)
        );
    }

    public static Stat getStat(Object trackedObject) {
        return stats.get(trackedObject);
    }

    private static class BenchmarkHandler<T> implements InvocationHandler {
        private final T target;
        private final Map<Method, Long> methodTimes = new HashMap<>();

        public BenchmarkHandler(T target) {
            this.target = target;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            long startTime = System.nanoTime();
            Object result = method.invoke(target, args);
            long endTime = System.nanoTime();

            if (method.isAnnotationPresent(Benchmarked.class)) {
                methodTimes.put(method, endTime - startTime);
            }
            return result;
        }

        public Stat getStat() {
            return new Stat(methodTimes);
        }
    }