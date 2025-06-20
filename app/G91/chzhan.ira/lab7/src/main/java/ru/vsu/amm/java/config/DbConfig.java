package ru.vsu.amm.java.config;

import org.postgresql.ds.PGSimpleDataSource;
import ru.vsu.amm.java.exception.DbException;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class DbConfig {

    private static final String FILENAME_FOR_SERVICE = "application.properties";

    private static final String FILENAME_FOR_TEST = "test.properties";

    public static DataSource getDataSourceForService() {
        return getDataSource(FILENAME_FOR_SERVICE);
    }

    public static DataSource getDataSourceForTest() {
        return getDataSource(FILENAME_FOR_TEST);
    }

    private static DataSource getDataSource(String fileName) {
        PGSimpleDataSource pgSimpleDataSource = new PGSimpleDataSource();
        Properties prop = new Properties();
        try (InputStream input = DbConfig.class.getClassLoader()
                .getResourceAsStream(fileName)) {
            prop.load(input);
        } catch (IOException e) {
            throw new DbException("Ошибка доступа к файлу конфигурации");
        }
        pgSimpleDataSource.setUrl(prop.getProperty("db.url"));
        pgSimpleDataSource.setUser(prop.getProperty("db.username"));
        pgSimpleDataSource.setPassword(prop.getProperty("db.password"));
        return pgSimpleDataSource;
    }
}