package ru.vsu.amm.java.configuration;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;


public class DatabaseConfig {
    public static HikariDataSource getDataSource() {
        HikariConfig config = new HikariConfig();
        config.setDriverClassName(DatabaseProperties.DB_DRIVER);
        config.setJdbcUrl(DatabaseProperties.DB_URL);
        config.setUsername(DatabaseProperties.DB_USER);
        config.setPassword(DatabaseProperties.DB_PASSWORD);

        return new HikariDataSource(config);
    }
}
