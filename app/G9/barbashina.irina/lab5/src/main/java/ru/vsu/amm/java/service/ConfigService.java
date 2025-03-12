package ru.vsu.amm.java.service;

import ru.vsu.amm.java.annotation.Config;
import ru.vsu.amm.java.database.DatabaseConfig;
import ru.vsu.amm.java.database.DatabaseConfigImpl;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.io.InputStream;

public class ConfigService {
    public static <T> T load(Class<T> configInterface) {
        Config configAnnotation =  configInterface.getAnnotation(Config.class);
        if(configAnnotation == null) {
            throw new IllegalArgumentException("Interface " + configInterface.getName()
                    + " is not annotated with @Config");
        }

        String path = configAnnotation.path();
        System.out.println(path);
        Properties properties = loadProperties(path);

        if(configInterface.equals(DatabaseConfig.class)) {
            return (T) new DatabaseConfigImpl(properties);
        }

        throw new IllegalArgumentException("Unsupported config interface: " + configInterface.getName());
    }

    private static Properties loadProperties(String path) {
        Properties properties = new Properties();
        try (InputStream input = ConfigService.class.getClassLoader().getResourceAsStream(path)){
            if(input == null) {
                throw new IllegalArgumentException("Unable to find " + path);
            }
            properties.load(input);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return properties;
    }
}
