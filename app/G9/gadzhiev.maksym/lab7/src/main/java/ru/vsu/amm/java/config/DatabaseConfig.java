package ru.vsu.amm.java.config;

import org.postgresql.ds.PGSimpleDataSource;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class DatabaseConfig {
    public static DataSource getDataSource() {
        PGSimpleDataSource pgSimpleDataSource = new PGSimpleDataSource();
        Properties properties = new Properties();
        try (InputStream input = DatabaseConfig.class.getClassLoader()
                .getResourceAsStream("db.properties")) {
            properties.load(input);
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
        pgSimpleDataSource.setUrl(properties.getProperty("db.url"));
        pgSimpleDataSource.setUser(properties.getProperty("db.username"));
        pgSimpleDataSource.setPassword(properties.getProperty("db.password"));
        return pgSimpleDataSource;
    }
}
