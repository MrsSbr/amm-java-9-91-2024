package ru.vsu.amm.java.Config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.slf4j.Slf4j;
import org.postgresql.ds.PGSimpleDataSource;
import ru.vsu.amm.java.Model.DTO.PropertiesDTO;
import ru.vsu.amm.java.Util.PropertiesInitializer;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

@Slf4j
public class DatabaseConfig {

    private DatabaseConfig() {
    }

    public static DataSource getDataSource() {
        return buildDataSource("database.properties");
    }

    public static DataSource getTestDataSource() {
        return buildDataSource("test-database.properties");
    }

    private static DataSource buildDataSource(String fileName) {
        Properties properties = new Properties();
        try (InputStream inputStream = DatabaseConfig.class.getClassLoader()
                .getResourceAsStream(fileName)) {
            properties.load(inputStream);
        } catch (IOException | NullPointerException e) {
            log.error("Ошибка при загрузке конфигурации БД из {}", fileName);
            throw new RuntimeException("Не удалось загрузить настройки БД: " + fileName);
        }

        PGSimpleDataSource dataSource = new PGSimpleDataSource();
        dataSource.setUrl(properties.getProperty("db.url"));
        dataSource.setUser(properties.getProperty("db.username"));
        dataSource.setPassword(properties.getProperty("db.password"));
        log.info("DataSource [{}] собран успешно.", fileName);
        return dataSource;
    }

}
