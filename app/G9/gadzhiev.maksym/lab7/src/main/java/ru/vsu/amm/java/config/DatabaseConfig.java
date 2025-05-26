package ru.vsu.amm.java.config;

import org.postgresql.ds.PGSimpleDataSource;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class DatabaseConfig {

    public static DataSource getDataSource() {
        Properties properties = loadProperties();
        PGSimpleDataSource pgSimpleDataSource = new PGSimpleDataSource();
        pgSimpleDataSource.setUrl(properties.getProperty("db.url"));
        pgSimpleDataSource.setUser(properties.getProperty("db.username"));
        pgSimpleDataSource.setPassword(properties.getProperty("db.password"));
        return pgSimpleDataSource;
    }

    private static Properties loadProperties() {
        Properties properties = new Properties();
        try(InputStream input = DatabaseConfig.class.getClassLoader().getResourceAsStream("db.properties")) {
            properties.load(input);
        } catch (IOException e) {
            throw new RuntimeException("Error database properties");
        }
        return properties;
    }
}
