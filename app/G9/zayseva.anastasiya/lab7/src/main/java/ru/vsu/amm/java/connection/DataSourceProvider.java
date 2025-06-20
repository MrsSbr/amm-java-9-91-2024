package ru.vsu.amm.java.connection;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import javax.sql.DataSource;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DataSourceProvider {
    private static final Logger logger = Logger.getLogger(DataSourceProvider.class.getName());
    private static volatile DataSource dataSource;

    private DataSourceProvider() {
        // Private constructor to prevent instantiation
    }

    public static DataSource getDataSource() {
        if (dataSource == null) {
            synchronized (DataSourceProvider.class) {
                if (dataSource == null) {
                    try {
                        DatabaseProperties props = new DatabaseProperties();
                        HikariConfig config = new HikariConfig();

                        // Basic connection settings
                        config.setJdbcUrl(props.getUrl());
                        config.setUsername(props.getUsername());
                        config.setPassword(props.getPassword());
                        config.setDriverClassName(props.getDriver());

                        // Connection pool tuning
                        config.setMaximumPoolSize(props.getPoolSize());
                        config.setMaxLifetime(props.getMaxLifetime());
                        config.setConnectionTimeout(props.getConnectionTimeout());
                        config.setIdleTimeout(600000); // 10 minutes
                        config.setPoolName("EducationSystemPool");

                        // Optimizations for education system
                        config.addDataSourceProperty("cachePrepStmts", "true");
                        config.addDataSourceProperty("prepStmtCacheSize", "250");
                        config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
                        dataSource = new HikariDataSource(config);
                        logger.log(Level.INFO, "Database connection pool initialized for Education System");

                    } catch (Exception e) {
                        logger.log(Level.SEVERE, "Failed to initialize connection pool", e);
                        throw new RuntimeException("Database initialization failed", e);
                    }
                }
            }
        }
        return dataSource;
    }

    public static void close() {
        if (dataSource != null && dataSource instanceof HikariDataSource) {
            ((HikariDataSource) dataSource).close();
            logger.log(Level.INFO, "Database connection pool closed");
            dataSource = null;
        }
    }
}