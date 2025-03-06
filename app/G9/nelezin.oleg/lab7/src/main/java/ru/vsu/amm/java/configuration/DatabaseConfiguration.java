package ru.vsu.amm.java.configuration;

import org.postgresql.ds.PGSimpleDataSource;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class DatabaseConfiguration {

    public static DataSource getDataSource() {
        PGSimpleDataSource pgSimpleDataSource = new PGSimpleDataSource();
        Properties prop = new Properties();
        try (InputStream input = DatabaseConfiguration.class.getClassLoader()
                .getResourceAsStream("db.properties")) {
            prop.load(input);
        } catch (IOException e) {
            System.out.println(e.getMessage()); // TO DO log
        }
        pgSimpleDataSource.setUrl(prop.getProperty("db.url"));
        pgSimpleDataSource.setUser(prop.getProperty("db.username"));
        pgSimpleDataSource.setPassword(prop.getProperty("db.password"));
        return pgSimpleDataSource;
    }
}
