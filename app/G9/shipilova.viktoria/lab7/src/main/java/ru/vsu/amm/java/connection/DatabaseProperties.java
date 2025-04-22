package ru.vsu.amm.java.connection;

import java.io.IOException;
import java.io.InputStream;

import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DatabaseProperties {
    private String url;
    private String username;
    private String password;
    private String driverClassName;
    private String poolName;
    private boolean autoCommit;
    private int connectionTimeout;
    private int idleTimeout;
    private int maxLifetime;
    private int minIdle;
    private int maximumPoolSize;

    private static final Logger log = Logger.getLogger(DatabaseProperties.class.getName());

    public DatabaseProperties() {
        Properties props = new Properties();

        try (InputStream input = getClass().getClassLoader().getResourceAsStream("db.properties")) {
            if (input == null) {
                log.log(Level.SEVERE, "Database properties file not found");
            }
            props.load(input);

            this.url = props.getProperty("db.url");
            this.username = props.getProperty("db.username");
            this.password = props.getProperty("db.password");
            this.driverClassName = props.getProperty("db.driver-class-name");
            this.poolName = props.getProperty("db.pool-name");
            this.autoCommit = Boolean.parseBoolean(props.getProperty("db.auto-commit"));
            this.connectionTimeout = Integer.parseInt(props.getProperty("db.connection-timeout"));
            this.idleTimeout = Integer.parseInt(props.getProperty("db.idle-timeout"));
            this.maxLifetime = Integer.parseInt(props.getProperty("db.max-lifetime"));
            this.minIdle = Integer.parseInt(props.getProperty("db.min-idle"));
            this.maximumPoolSize = Integer.parseInt(props.getProperty("db.maximum-pool-size"));
        } catch (IOException e) {
            log.log(Level.SEVERE, "Unable to read properties file", e);
        }
    }

    public String getUrl() {
        return url;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getDriverClassName() {
        return driverClassName;
    }

    public String getPoolName() {
        return poolName;
    }

    public boolean isAutoCommit() {
        return autoCommit;
    }

    public int getConnectionTimeout() {
        return connectionTimeout;
    }

    public int getIdleTimeout() {
        return idleTimeout;
    }

    public int getMaxLifetime() {
        return maxLifetime;
    }

    public int getMinIdle() {
        return minIdle;
    }

    public int getMaximumPoolSize() {
        return maximumPoolSize;
    }
}
