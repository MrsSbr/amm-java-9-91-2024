package ru.vsu.amm.java.DBConnection;

import javax.sql.DataSource;

import org.postgresql.ds.PGSimpleDataSource;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class DBConfiguration {
    public static DataSource getDataSource(){
        return getDataSource("db.url");
    }

    public static DataSource getTestDataSource(){
        return getDataSource("db.test");
    }

    private static DataSource getDataSource(String url) {
        PGSimpleDataSource pgSimpleDataSource = new PGSimpleDataSource();
        Properties properties = new Properties();
        try (InputStream is = DBConfiguration.class.getClassLoader()
                .getResourceAsStream("db.properties")) {
            properties.load(is);
        } catch (IOException e) {
            throw new RuntimeException("Ошибка получения конфигурации:(");
        }
        pgSimpleDataSource.setUrl(properties.getProperty(url));
        pgSimpleDataSource.setUser(properties.getProperty("db.user"));
        pgSimpleDataSource.setPassword(properties.getProperty("db.password"));
        return pgSimpleDataSource;
    }
}
