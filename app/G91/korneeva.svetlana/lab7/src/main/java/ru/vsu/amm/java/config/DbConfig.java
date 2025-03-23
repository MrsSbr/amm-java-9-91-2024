package ru.vsu.amm.java.config;

import org.postgresql.ds.PGSimpleDataSource;
import ru.vsu.amm.java.exception.DatabaseException;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;


public class DbConfig {
    public static DataSource getDataSource() {
        PGSimpleDataSource pgSimpleDataSource = new PGSimpleDataSource();
        Properties prop = new Properties();
        try (InputStream input = DbConfig.class.getClassLoader()
                .getResourceAsStream("application.properties")) {
            prop.load(input);
        } catch (IOException e) {
            throw new DatabaseException("Ошибка с конфигурационным файлом");
        }
        pgSimpleDataSource.setUrl(prop.getProperty("db.url"));
        pgSimpleDataSource.setUser(prop.getProperty("db.username"));
        pgSimpleDataSource.setPassword(prop.getProperty("db.password"));
        return pgSimpleDataSource;
    }
}
