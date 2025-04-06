package ru.vsu.amm.java.connection;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import javax.sql.DataSource;

import java.util.logging.Level;
import java.util.logging.Logger;

public class DataSourceProvider {
    private static final Logger logger = Logger.getLogger(DataSourceProvider.class.getName());

    private static DataSource dataSource;
    private static DataSource testDataSource;

    private static void loadProperties(HikariConfig config) {
        DatabaseProperties props = new DatabaseProperties();
        config.setJdbcUrl(props.getUrl());
        config.setUsername(props.getUsername());
        config.setPassword(props.getPassword());
        config.setDriverClassName(props.getDriverClassName());
        config.setPoolName(props.getPoolName());
        config.setAutoCommit(props.isAutoCommit());
        config.setConnectionTimeout(props.getConnectionTimeout());
        config.setIdleTimeout(props.getIdleTimeout());
        config.setMaxLifetime(props.getMaxLifetime());
        config.setMinimumIdle(props.getMinIdle());
        config.setMaximumPoolSize(props.getMaximumPoolSize());
    }

    public static DataSource getDataSource() {
        if (testDataSource != null) {
            return testDataSource; // Используем тестовую базу
        }
        if (dataSource == null) {
            HikariConfig config = new HikariConfig();
            loadProperties(config);
            dataSource = new HikariDataSource(config);

            logger.log(Level.INFO, "Loaded properties from " + config.getJdbcUrl());
        }
        return dataSource;
    }

    public static void setTestDataSource(DataSource testDataSource) {
        DataSourceProvider.testDataSource = testDataSource;
        logger.log(Level.INFO, "Тестовый DataSource успешно установлен");
    }

    /**
     * Сбрасывает тестовый DataSource, возвращаясь к основному.
     */
    public static void resetTestDataSource() {
        testDataSource = null;
        logger.log(Level.INFO, "Тестовый DataSource сброшен");
    }
}
