package ru.vsu.amm.java.service;

import ru.vsu.amm.java.annotation.Config;
import ru.vsu.amm.java.annotation.Property;

import java.io.IOException;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Proxy;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class ConfigService {
    private static final Logger logger = Logger.getLogger(ConfigService.class.getName());

    private ConfigService() {
    }

    public static <T> T load(Class<T> someClass) {
        logger.log(Level.INFO, "Устанавливаем проксирование для " + someClass.getName());

        if (!someClass.isInterface()) {
            var e = new IllegalArgumentException(someClass.getName() + " (Не является интерфейсом)");
            logger.log(Level.SEVERE, "Аргумент не является интерфейсом", e);
            throw e;
        }
        logger.log(Level.INFO, "Аргумент является интерфейсом");

        if (!someClass.isAnnotationPresent(Config.class)) {
            var e = new IllegalArgumentException(someClass.getName() + " (Интерфейс должен быть аннотирован @Config)");
            logger.log(Level.SEVERE, "Интерфейс не аннотирован @Config", e);
            throw e;
        }
        logger.log(Level.INFO, "Интерфейс аннотирован @Config");

        var config = someClass.getAnnotation(Config.class);
        var path = config.path();
        logger.log(Level.INFO, "Получен путь к файлу со свойствами");

        var properties = new Properties();
        try {
            properties.load(Files.newBufferedReader(Paths.get(path)));
            logger.log(Level.INFO, "Свойства загружены из файла");
        } catch (IOException ioe) {
            var e = new IllegalArgumentException(path + " (Ошибка при чтении файла со свойствами)", ioe);
            logger.log(Level.SEVERE, "Не удалось загрузить свойства из файла", e);
            throw e;
        }

        InvocationHandler handler = (proxy, method, args) -> {
            logger.log(Level.INFO, "Вызван метод " + method.getName());

            if (!method.isAnnotationPresent(Property.class)) {
                var e = new IllegalArgumentException(method.getName() + " (Метод должен быть аннотирован @Property)");
                logger.log(Level.INFO, "Метод не аннотирован @Property", e);
                throw e;
            }
            logger.log(Level.INFO, "Метод аннотирован @Property");

            var propertyAnnotation = method.getAnnotation(Property.class);
            var key = propertyAnnotation.value();
            logger.log(Level.INFO, "Получен ключ");

            var value = properties.getProperty(key);
            if (value == null) {
                var e = new IllegalArgumentException(key + " (Не удалось найти свойство в файле)");
                logger.log(Level.SEVERE, "Свойство не найдено", e);
                throw e;
            }
            logger.log(Level.INFO, "Получено значение");

            var returnType = method.getReturnType();
            logger.log(Level.INFO, "Получен возвращаемый тип");

            if (returnType == String.class) {
                logger.log(Level.INFO, "Приведение к возвращаемому типу не требуется");

                logger.log(Level.INFO, "Возврат значения");
                return value;
            }

            try {
                logger.log(Level.INFO, "Требуется приведение к возвращаемому типу");

                var valueOf = returnType.getMethod("valueOf", String.class);
                logger.log(Level.INFO, "Получен метод приведения");

                var res = valueOf.invoke(null, value);
                logger.log(Level.INFO, "Значение приведено к возвращаемому типу");

                logger.log(Level.INFO, "Возврат значения");
                return res;
            } catch (NoSuchMethodException nsme) {
                var e = new IllegalArgumentException(returnType.getName() + " (Тип не поддерживается)", nsme);
                logger.log(Level.SEVERE, "Тип возвращаемого методом значения не поддерживается", e);
                throw e;
            } catch (InvocationTargetException ite) {
                var e = ite.getCause();
                logger.log(Level.SEVERE, "Не удалось привести значение к возвращаемому типу", e);
                throw e;
            }
        };

        logger.log(Level.INFO, "Возврат прокси-объекта");
        //noinspection unchecked
        return (T) Proxy.newProxyInstance(someClass.getClassLoader(), new Class<?>[]{someClass}, handler);
    }
}
