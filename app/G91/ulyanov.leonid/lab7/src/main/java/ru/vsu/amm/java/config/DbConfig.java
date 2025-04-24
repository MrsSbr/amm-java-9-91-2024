package ru.vsu.amm.java.config;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.postgresql.ds.PGSimpleDataSource;

public class DbConfig {
    public static DataSource getDataSource() {
        Properties props = new Properties();
        try (InputStream in = DbConfig.class.getClassLoader().getResourceAsStream("db.properties")) {
            props.load(in);
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }

        PGSimpleDataSource dataSource = new PGSimpleDataSource();

        dataSource.setUrl(props.getProperty("db.url"));
        dataSource.setUser(props.getProperty("db.username"));
        dataSource.setPassword(props.getProperty("db.password"));

        return dataSource;
    }
}
