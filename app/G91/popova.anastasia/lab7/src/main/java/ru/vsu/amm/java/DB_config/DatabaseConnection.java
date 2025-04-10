package ru.vsu.amm.java.DB_config;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Connection;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import javax.sql.DataSource;
import java.util.Properties;
import java.io.InputStream;


public final class DatabaseConnection {

    private static final HikariDataSource dataSource;

    static {
        HikariConfig config = new HikariConfig();
       Properties prop = new Properties();
        try (InputStream input = DatabaseConnection.class.getClassLoader()
                .getResourceAsStream("db.properties")) {

            prop.load(input);

            config.setJdbcUrl(prop.getProperty("db.url"));
            config.setUsername(prop.getProperty("db.username"));
            config.setPassword(prop.getProperty("db.password"));

            config.setMaximumPoolSize(10);
            config.setConnectionTimeout(30000);
            config.setLeakDetectionThreshold(60000);
            config.setMinimumIdle(5);
            config.setIdleTimeout(600000);
            config.setMaxLifetime(18000000);



        } catch (IOException e) {
            //throw new SQLException("failed to load database configuration", e);
        }

        dataSource = new HikariDataSource(config);

    }

    public static Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

    public static void shutdown() {
        if (dataSource != null) {
            dataSource.close();
        }
    }
}
