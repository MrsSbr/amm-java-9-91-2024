package ru.vsu.amm.java.config;

import org.postgresql.ds.PGSimpleDataSource;

import javax.sql.DataSource;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class DatabaseConfig {
    private static final String PROPERTIES_FILE = "db.properties";
    private static final String URL_PROPERTY = "db.url";
    private static final String USERNAME_PROPERTY = "db.username";
    private static final String PASSWORD_PROPERTY = "db.password";

    public static DataSource getDataSource() {
        Properties properties = loadProperties();
        PGSimpleDataSource pgSimpleDataSource = new PGSimpleDataSource();
        pgSimpleDataSource.setUrl(properties.getProperty(URL_PROPERTY));
        pgSimpleDataSource.setUser(properties.getProperty(USERNAME_PROPERTY));
        pgSimpleDataSource.setPassword(properties.getProperty(PASSWORD_PROPERTY));
        return pgSimpleDataSource;
    }

    private static Properties loadProperties() {
        Properties properties = new Properties();
        try(InputStream input = DatabaseConfig.class.getClassLoader().getResourceAsStream(PROPERTIES_FILE)) {
            properties.load(input);
        } catch (IOException e) {
            throw new RuntimeException("Error database properties");
        }
        return properties;
    }
}
