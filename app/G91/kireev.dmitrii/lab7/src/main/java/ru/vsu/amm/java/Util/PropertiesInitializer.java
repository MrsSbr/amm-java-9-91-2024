package ru.vsu.amm.java.Util;

import ru.vsu.amm.java.Exception.ConfigurationException;
import ru.vsu.amm.java.Model.DTO.PropertiesDTO;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesInitializer {

    public static PropertiesDTO loadProperties() {

        Properties properties = new Properties();

        try (InputStream input = PropertiesInitializer.class.getClassLoader().getResourceAsStream("database.properties")) {

            properties.load(input);

            return new PropertiesDTO(
                    properties.getProperty("db.url"),
                    properties.getProperty("db.username"),
                    properties.getProperty("db.password")
            );

        } catch (IOException e) {
            throw new ConfigurationException("Failed to load database properties");
        }
    }
}
