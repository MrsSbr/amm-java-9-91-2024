package ru.vsu.amm.java.DB_config;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Connection;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.util.Properties;
import java.io.InputStream;


public final class DatabaseConnection {
    private static final org.slf4j.Logger log = LoggerFactory.getLogger(DatabaseConnection.class);


    private static final HikariDataSource dataSource;
    private static final String CONFIG_FILE = "db.properties";

    static {
        HikariConfig config = new HikariConfig();
       Properties prop = new Properties();
        try (InputStream input = DatabaseConnection.class.getClassLoader()
                .getResourceAsStream(CONFIG_FILE))  {

            prop.load(input);

            log.debug("loaded database configuration from {}", CONFIG_FILE);

            config.setJdbcUrl(prop.getProperty("db.url"));
            config.setUsername(prop.getProperty("db.username"));
            config.setPassword(prop.getProperty("db.password"));

            config.setMaximumPoolSize(10);
            config.setConnectionTimeout(30000);
            config.setLeakDetectionThreshold(60000);
            config.setMinimumIdle(5);
            config.setIdleTimeout(600000);
            config.setMaxLifetime(18000000);

            log.debug("HikariCP configuration: {}", config);

        } catch (IOException e) {
            log.error("failed to load database configuration", e);
            throw new RuntimeException("failed to load database configuration", e);
        }

        dataSource = new HikariDataSource(config);
        log.info("database connection pool initialized successfully");

    }

    public static Connection getConnection() throws SQLException {
        log.debug("acquiring database connection...");
        try {
            Connection conn = dataSource.getConnection();
            log.debug("connection acquired successfully");
            return conn;
        } catch (SQLException e) {
            log.error("failed to acquire database connection", e);
            throw e;
        }

    }

    public static HikariDataSource getDataSource() {
        return dataSource;
    }

    public static void shutdown() {
        if (dataSource != null) {
            dataSource.close();
            log.info("database connection pool shutdown complete");
        }
    }
}
