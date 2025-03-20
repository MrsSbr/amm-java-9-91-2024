package ru.vsu.amm.java.Util;

import ru.vsu.amm.java.Model.DTO.PropertiesDTO;

import java.io.InputStream;
import java.util.Properties;

public class PropertiesInitializer {

    public static PropertiesDTO loadProperties() {

        Properties properties = new Properties();

        try (InputStream input = PropertiesInitializer.class.getClassLoader().getResourceAsStream("database.properties")) {

            if (input == null) {
                throw new RuntimeException("Unable to find database.properties");
            }

            properties.load(input);

            return new PropertiesDTO(
                    properties.getProperty("db.url"),
                    properties.getProperty("db.username"),
                    properties.getProperty("db.password")
            );

        } catch (Exception e) {
            throw new RuntimeException("Failed to load database properties", e);
        }
    }
}
