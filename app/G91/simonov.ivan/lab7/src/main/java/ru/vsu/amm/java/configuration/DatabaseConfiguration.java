package ru.vsu.amm.java.configuration;

import org.postgresql.ds.PGSimpleDataSource;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class DatabaseConfiguration {

    public static DataSource getDataSource() {

        Properties properties = new Properties();

        try (InputStream input = DatabaseConfiguration.class.getClassLoader()
                .getResourceAsStream("dp.properties")) {

            properties.load(input);

        } catch (IOException e) {

            throw new RuntimeException(e.getMessage());

        }

        PGSimpleDataSource dataSource = new PGSimpleDataSource();

        dataSource.setUrl(properties.getProperty("db.url"));
        dataSource.setUser(properties.getProperty("db.username"));
        dataSource.setPassword(properties.getProperty("db.password"));

        return dataSource;
    }
}
