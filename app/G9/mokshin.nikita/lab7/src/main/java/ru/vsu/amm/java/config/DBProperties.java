package ru.vsu.amm.java.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.vsu.amm.java.exception.PropertiesFileException;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class DBProperties {
    private static final Logger logger = LoggerFactory.getLogger(DBProperties.class);
    private static final Properties properties = new Properties();
    public static final String DB_URL;
    public static final String DB_USER;
    public static final String DB_PASSWORD;
    public static final String DB_DRIVER;

    static {
        String propFileName = "application.properties";
        String test = System.getProperty("test");
        if(test != null && test.equals("true")) {
            propFileName = "test.properties";
        }
        try (InputStream input = DatabaseConfig.class.getClassLoader().getResourceAsStream(propFileName)) {
            properties.load(input);
            logger.info("Configuration file loaded successfully");
        } catch (IOException e) {
            logger.error("Error accessing the configuration file", e);
            throw new PropertiesFileException("Error accessing the configuration file");
        }

        DB_URL = properties.getProperty("db.url");
        DB_USER = properties.getProperty("db.username");
        DB_PASSWORD = properties.getProperty("db.password");
        DB_DRIVER = properties.getProperty("db.driver");
    }
}
