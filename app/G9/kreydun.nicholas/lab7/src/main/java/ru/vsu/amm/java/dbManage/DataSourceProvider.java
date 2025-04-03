package ru.vsu.amm.java.dbManage;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import javax.sql.DataSource;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import static ru.vsu.amm.java.services.Logg.logger;

public class DataSourceProvider {

    private static DataSource dataSource;

    public static DataSource getDataSource() {
        logger.info("Loading DataSource");
        if (dataSource == null) {
            HikariConfig config = new HikariConfig();
            loadProperties(config);
            dataSource = new HikariDataSource(config);

            logger.info("DataSource loaded");
        }
        return dataSource;
    }

    private static void loadProperties(HikariConfig config) {
        Properties properties = new Properties();

        try (FileInputStream input = new FileInputStream("path/to/db.properties")) {
            properties.load(input);
            config.setJdbcUrl(properties.getProperty("jdbc.url"));
            config.setUsername(properties.getProperty("jdbc.username"));
            config.setPassword(properties.getProperty("jdbc.password"));
            config.setDriverClassName(properties.getProperty("jdbc.driverClassName"));
            config.setMaximumPoolSize(Integer.parseInt(properties.getProperty("jdbc.maximumPoolSize")));
            config.setConnectionTimeout(Long.parseLong(properties.getProperty("jdbc.connectionTimeout")));
            config.setIdleTimeout(Long.parseLong(properties.getProperty("jdbc.idleTimeout")));
            config.setMaxLifetime(Long.parseLong(properties.getProperty("jdbc.maxLifetime")));

            logger.info("Loaded properties");
        } catch (IOException e) {
            logger.warning("Failed to load properties" + e.getMessage());
        }
    }

    public static void closeDataSource() {
        if (dataSource instanceof HikariDataSource) {
            ((HikariDataSource) dataSource).close();

            logger.info("Close HikariDataSource");
        }
    }
}