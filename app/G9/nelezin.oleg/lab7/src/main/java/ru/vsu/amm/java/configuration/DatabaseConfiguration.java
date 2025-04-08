package ru.vsu.amm.java.configuration;

import org.postgresql.ds.PGSimpleDataSource;
import ru.vsu.amm.java.exception.PropertiesFileException;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Logger;

public class DatabaseConfiguration {

    private static final Logger log = Logger.getLogger(DatabaseConfiguration.class.getName());

    public static DataSource getDataSource() {
        PGSimpleDataSource pgSimpleDataSource = new PGSimpleDataSource();
        Properties prop = new Properties();
        try (InputStream input = DatabaseConfiguration.class.getClassLoader()
                .getResourceAsStream("db.properties")) {
            prop.load(input);
        } catch (IOException e) {
            throw new PropertiesFileException("Ошибка доступа к файлу конфигурации");
        }
        pgSimpleDataSource.setUrl(prop.getProperty("db.url"));
        pgSimpleDataSource.setUser(prop.getProperty("db.username"));
        pgSimpleDataSource.setPassword(prop.getProperty("db.password"));
        log.info("DataSource is configured");
        return pgSimpleDataSource;
    }
}
