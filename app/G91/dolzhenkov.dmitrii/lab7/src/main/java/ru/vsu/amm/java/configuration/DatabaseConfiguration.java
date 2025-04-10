package ru.vsu.amm.java.configuration;

import lombok.extern.slf4j.Slf4j;
import org.postgresql.ds.PGSimpleDataSource;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

@Slf4j
public class DatabaseConfiguration {
    public static DataSource getDataSource() {
        Properties properties = new Properties();
        try (InputStream inputStream = DatabaseConfiguration.class.getClassLoader()
                .getResourceAsStream("database.properties")) {
            properties.load(inputStream);
        } catch (IOException e) {
            log.error(e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
        PGSimpleDataSource dataSource = new PGSimpleDataSource();
        dataSource.setUrl(properties.getProperty("db.url"));
        dataSource.setUser(properties.getProperty("db.user"));
        dataSource.setPassword(properties.getProperty("db.password"));
        log.info("DataSource собран успешно.");
        return dataSource;
    }
}
