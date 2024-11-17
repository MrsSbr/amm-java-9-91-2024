package ru.vsu.amm.java.util;

import ru.vsu.amm.java.annotation.Benchmarked;
import ru.vsu.amm.java.entity.Stat;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Benchmark {

    private static final Logger logger;
    private static final Map<Object, Stat> stats;

    static {
        logger = Logger.getLogger(Benchmark.class.getName());
        stats = new HashMap<>();
    }

    public static <T> T track(T object) {
        logger.log(
                Level.INFO,
                "Устанавливаем проксирование для объекта " + object
        );
        return setProxyOn(object);
    }

    public static Stat getStat(Object object) {
        return stats.get(object);
    }

    public static void resetStats() {
        stats.clear();
    }

    @SuppressWarnings("unchecked")
    private static <T> T setProxyOn(T object) {
        Class<?> clazz = object.getClass();
        return (T) Proxy.newProxyInstance(clazz.getClassLoader(), clazz.getInterfaces(), new BenchmarkInvocationHandler(object));
    }

    private record BenchmarkInvocationHandler(Object proxy) implements InvocationHandler {

        @Override
        public Object invoke(Object object, Method method, Object[] args) {
            Object resultObj = null;
            if (method.getAnnotation(Benchmarked.class) != null) {
                try {
                    long startTime = System.currentTimeMillis();
                    resultObj = method.invoke(proxy, args);
                    logger.log(
                            Level.INFO,
                            "Вызвали метод \"" + method.getName() + "\""
                    );
                    long stopTime = System.currentTimeMillis();
                    long elapsedTime = stopTime - startTime;
                    stats.computeIfAbsent(object, key -> new Stat());
                    stats.get(object).insertStat(method, elapsedTime);
                    logger.log(
                            Level.INFO,
                            "Записали время выполнения метода \""
                                    + method.getName() + "\": " + elapsedTime + "мс"
                    );
                } catch (Exception e) {
                    logger.log(
                            Level.SEVERE,
                            "Не удалось вызвать метод",
                            e
                    );
                }
            } else {
                try {
                    resultObj = method.invoke(proxy, args);
                    logger.log(
                            Level.INFO,
                            "Вызвали метод \"" + method.getName() + "\""
                    );
                } catch (Exception e) {
                    logger.log(
                            Level.SEVERE,
                            "Не удалось вызвать метод",
                            e
                    );
                }
            }
            return resultObj;
        }
    }
}
