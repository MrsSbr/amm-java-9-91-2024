package ru.vsu.amm.java.database;

import java.util.Properties;

public class DatabaseConfigImpl implements DatabaseConfig {
    private Properties properties;

    public DatabaseConfigImpl(Properties properties) {
        this.properties = properties;
    }

    @Override
    public String getJdbcUrl() {
        return properties.getProperty("db.jdbcUrl");
    }

    @Override
    public String getUser() {
        return properties.getProperty("db.user");
    }

    @Override
    public String getPassword() {
        return properties.getProperty("db.password");
    }

    @Override
    public boolean getSsl() {
        return Boolean.parseBoolean(properties.getProperty("db.ssl"));
    }
}
