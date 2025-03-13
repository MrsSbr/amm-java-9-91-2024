package ru.vsu.amm.java.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public class DatabaseConfig {
    private static final String url = "jdbc:postgresql://localhost:5432/film";
    private static final String username = "film";
    private static final String password = "12345678";

    public static HikariDataSource getDataSource() {
        HikariConfig config = new HikariConfig();
        config.setDriverClassName("org.postgresql.Driver");
        config.setJdbcUrl(url);
        config.setUsername(username);
        config.setPassword(password);

        return new HikariDataSource(config);
    }
}
