package ru.vsu.amm.java.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;

public class DatabaseConfig {
    private static final Logger logger = LoggerFactory.getLogger(DatabaseConfig.class);
    private static final Properties properties = new Properties();

    public static HikariDataSource getDataSource() {
        logger.info("Initializing HikariDataSource");

        HikariConfig config = new HikariConfig();
        config.setDriverClassName(DBProperties.DB_DRIVER);
        config.setJdbcUrl(DBProperties.DB_URL);
        config.setUsername(DBProperties.DB_USER);
        config.setPassword(DBProperties.DB_PASSWORD);

        logger.debug("Database configuration: URL={}, User={}", DBProperties.DB_URL, DBProperties.DB_USER);

        HikariDataSource dataSource = new HikariDataSource(config);
        logger.info("HikariDataSource initialized successfully");
        return dataSource;
    }
}
