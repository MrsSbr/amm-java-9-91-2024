package ru.vsu.amm.java.configuration;

import lombok.Getter;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

@Getter
public class DatabaseProperties {
    private final String url;
    private final String username;
    private final String password;
    private final String initScriptPath;

    public DatabaseProperties(String resourceName) {
        Properties properties = new Properties();
        try (InputStream input = DatabaseProperties.class.getClassLoader()
                .getResourceAsStream(resourceName)) {
            properties.load(input);
        } catch (IOException e) {
            // TODO: add logging
        }
        this.url = properties.getProperty("db.url");
        this.username = properties.getProperty("db.username");
        this.password = properties.getProperty("db.password");
        this.initScriptPath = properties.getProperty("db.initScriptPath");
    }
}
