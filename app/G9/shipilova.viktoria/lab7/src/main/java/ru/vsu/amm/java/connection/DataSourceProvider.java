package ru.vsu.amm.java.connection;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import javax.sql.DataSource;

import java.io.IOException;
import java.io.InputStream;

import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DataSourceProvider {
    private static final Logger logger = Logger.getLogger(DataSourceProvider.class.getName());

    private static DataSource dataSource;

    private static void loadProperties(HikariConfig config) {
        Properties props = new Properties();

        try (InputStream input = DataSourceProvider.class.getClassLoader()
                .getResourceAsStream("db.properties")){
            props.load(input);
            config.setJdbcUrl(props.getProperty("db.url"));
            config.setUsername(props.getProperty("db.username"));
            config.setPassword(props.getProperty("db.password"));
            config.setDriverClassName(props.getProperty("db.driver-class-name"));
            config.setPoolName(props.getProperty("db.pool-name"));
            config.setAutoCommit(Boolean.parseBoolean(props.getProperty("db.auto-commit")));
            config.setConnectionTimeout(Integer.parseInt(props.getProperty("db.connection-timeout")));
            config.setIdleTimeout(Integer.parseInt(props.getProperty("db.idle-timeout")));
            config.setMaxLifetime(Integer.parseInt(props.getProperty("db.max-lifetime")));
            config.setMinimumIdle(Integer.parseInt(props.getProperty("db.min-idle")));
            config.setMaximumPoolSize(Integer.parseInt(props.getProperty("db.maximum-pool-size")));

            logger.log(Level.INFO, "Loaded properties from " + props.getProperty("db.url"));
        } catch (IOException ex) {
            logger.log(Level.SEVERE, "Failed to load properties from " + props.getProperty("db.url"), ex);
        }
    }

    public static DataSource getDataSource() {
        if (dataSource == null) {
            HikariConfig config = new HikariConfig();
            loadProperties(config);
            dataSource = new HikariDataSource(config);

            logger.log(Level.INFO, "Loaded properties from " + config.getJdbcUrl());
        }
        return dataSource;
    }
}
