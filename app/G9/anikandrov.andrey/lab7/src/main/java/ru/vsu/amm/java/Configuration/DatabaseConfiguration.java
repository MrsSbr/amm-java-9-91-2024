package ru.vsu.amm.java.Configuration;

import org.postgresql.ds.PGSimpleDataSource;

import javax.sql.DataSource;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Properties;

public class DatabaseConfiguration {

    public static DataSource getDataSource() {
        PGSimpleDataSource pgSimpleDataSource = new PGSimpleDataSource();
        Properties prop = new Properties();

        try (InputStream input = new FileInputStream("C:\\Users\\andrey\\IdeaProjects\\amm-java-9-91-2024-6\\app\\G9\\anikandrov.andrey\\lab7\\src\\main\\resources\\application.properties")) {
            prop.load(input);
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
        pgSimpleDataSource.setUrl(prop.getProperty("db.url"));
        pgSimpleDataSource.setUser(prop.getProperty("db.username"));
        pgSimpleDataSource.setPassword(prop.getProperty("db.password"));
        return pgSimpleDataSource;
    }
}