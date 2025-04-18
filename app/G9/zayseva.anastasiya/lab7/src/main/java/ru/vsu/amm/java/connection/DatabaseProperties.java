package ru.vsu.amm.java.connection;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DatabaseProperties {
    private static final Logger logger = Logger.getLogger(DatabaseProperties.class.getName());
    private static final String PROPERTIES_FILE = "education_db.properties";

    private final String url;
    private final String username;
    private final String password;
    private final String driver;
    private final int poolSize;
    private final int maxLifetime;
    private final int connectionTimeout;

    public DatabaseProperties() {
        Properties props = new Properties();
        try (InputStream input = getClass().getClassLoader().getResourceAsStream(PROPERTIES_FILE)) {
            if (input == null) {
                logger.log(Level.SEVERE, "Database properties file not found: " + PROPERTIES_FILE);
                throw new RuntimeException("Database configuration file not found");
            }
            props.load(input);

            this.url = props.getProperty("db.url");
            this.username = props.getProperty("db.username");
            this.password = props.getProperty("db.password");
            this.driver = props.getProperty("db.driver");
            this.poolSize = Integer.parseInt(props.getProperty("db.pool.size", "10"));
            this.maxLifetime = Integer.parseInt(props.getProperty("db.pool.max_lifetime", "1800000"));
            this.connectionTimeout = Integer.parseInt(props.getProperty("db.pool.connection_timeout", "30000"));

            validateProperties();

        } catch (IOException | NumberFormatException e) {
            logger.log(Level.SEVERE, "Error loading database properties", e);
            throw new RuntimeException("Failed to load database properties", e);
        }
    }

    private void validateProperties() {
        if (url == null || username == null || password == null || driver == null) {
            throw new RuntimeException("Required database properties are missing");
        }
    }

    // Getters
    public String getUrl() { return url; }
    public String getUsername() { return username; }
    public String getPassword() { return password; }
    public String getDriver() { return driver; }
    public int getPoolSize() { return poolSize; }
    public int getMaxLifetime() { return maxLifetime; }
    public int getConnectionTimeout() { return connectionTimeout; }
}