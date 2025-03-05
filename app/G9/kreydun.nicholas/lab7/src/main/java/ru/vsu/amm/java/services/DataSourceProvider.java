package ru.vsu.amm.java.services;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import javax.sql.DataSource;

public class DataSourceProvider {

    private static DataSource dataSource;

    public static DataSource getDataSource() {
        if (dataSource == null) {
            HikariConfig config = new HikariConfig();
            config.setJdbcUrl("jdbc:postgresql://localhost:5434/SocialNetwork_");
            config.setUsername("postgres");
            config.setPassword("12345");
            config.setDriverClassName("org.postgresql.Driver");
            config.setMaximumPoolSize(10);

            //to do убрать магические числа в константы
            config.setConnectionTimeout(30000); // 30 секунд
            config.setIdleTimeout(600000); // 10 минут
            config.setMaxLifetime(1800000); // 30 минут

            dataSource = new HikariDataSource(config);
        }
        return dataSource;
    }

    public static void closeDataSource() {
        if (dataSource instanceof HikariDataSource) {
            ((HikariDataSource) dataSource).close();
        }
    }
}
