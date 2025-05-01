package ru.vsu.amm.java.configuration;

import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

@Getter
public class DatabaseProperties {
    private static final Logger logger = LoggerFactory.getLogger(DatabaseProperties.class);

    private final String url;
    private final String username;
    private final String password;
    private final String initScriptPath;

    public DatabaseProperties(String resourceName) {
        Properties properties = new Properties();
        try (InputStream input = DatabaseProperties.class.getClassLoader()
                .getResourceAsStream(resourceName)) {
            if (input == null) {
                logger.error("Файл конфигурации {} не найден", resourceName);
                throw new IllegalArgumentException("Файл конфигурации " + resourceName + " не найден");
            }

            properties.load(input);
            logger.debug("Настройки базы данных успешно загружены из {}", resourceName);

        } catch (IOException e) {
            logger.error("Ошибка при загрузке настроек базы данных из {}", resourceName, e);
            throw new RuntimeException("Не удалось загрузить настройки базы данных", e);
        }

        this.url = properties.getProperty("db.url");
        this.username = properties.getProperty("db.username");
        this.password = properties.getProperty("db.password");
        this.initScriptPath = properties.getProperty("db.initScriptPath");

        logger.debug("Инициализированы настройки БД: URL={}, пользователь={}, скрипт инициализации={}",
                url, username, initScriptPath);
    }
}