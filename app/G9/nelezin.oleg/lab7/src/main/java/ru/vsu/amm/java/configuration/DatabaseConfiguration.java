package ru.vsu.amm.java.configuration;

import org.postgresql.ds.PGSimpleDataSource;
import ru.vsu.amm.java.exception.PropertiesFileException;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Logger;

public class DatabaseConfiguration {

    private static final String FILENAME_FOR_SERVICE = "db.properties";

    private static final String FILENAME_FOR_TEST = "test.properties";

    private static final Logger log = Logger.getLogger(DatabaseConfiguration.class.getName());

    public static DataSource getDataSourceForService() {
        return getDataSource(FILENAME_FOR_SERVICE);
    }

    public static DataSource getDataSourceForTest() {
        return getDataSource(FILENAME_FOR_TEST);
    }

    private static DataSource getDataSource(String fileName) {
        PGSimpleDataSource pgSimpleDataSource = new PGSimpleDataSource();
        Properties prop = new Properties();
        try (InputStream input = DatabaseConfiguration.class.getClassLoader()
                .getResourceAsStream(fileName)) {
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
