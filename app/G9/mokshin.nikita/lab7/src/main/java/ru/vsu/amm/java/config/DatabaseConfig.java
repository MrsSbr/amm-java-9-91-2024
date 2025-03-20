package ru.vsu.amm.java.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.vsu.amm.java.exception.PropertiesFileException;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class DatabaseConfig {
    private static final Logger logger = LoggerFactory.getLogger(DatabaseConfig.class);
    private static final Properties properties = new Properties();

    static {
        try (InputStream input = DatabaseConfig.class.getClassLoader().getResourceAsStream("application.properties")) {
            properties.load(input);
            logger.info("Configuration file loaded successfully");
        } catch (IOException e) {
            logger.error("Error accessing the configuration file", e);
            throw new PropertiesFileException("Error accessing the configuration file");
        }
    }

    public static HikariDataSource getDataSource() {
        logger.info("Initializing HikariDataSource");

        HikariConfig config = new HikariConfig();
        config.setDriverClassName(properties.getProperty("db.driver"));
        config.setJdbcUrl(properties.getProperty("db.url"));
        config.setUsername(properties.getProperty("db.username"));
        config.setPassword(properties.getProperty("db.password"));

        logger.debug("Database configuration: URL={}, User={}", properties.getProperty("db.url"), properties.getProperty("db.username"));

        HikariDataSource dataSource = new HikariDataSource(config);
        logger.info("HikariDataSource initialized successfully");
        return dataSource;
    }
}
