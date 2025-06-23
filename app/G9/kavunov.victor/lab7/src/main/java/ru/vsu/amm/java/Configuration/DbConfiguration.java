package ru.vsu.amm.java.Configuration;

import org.postgresql.ds.PGSimpleDataSource;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class DbConfiguration {
    public static DataSource getDataSource() {
        PGSimpleDataSource pgSimpleDataSource = new PGSimpleDataSource();
        Properties props = new Properties();
        try (InputStream input = DbConfiguration.class.getClassLoader().getResourceAsStream("db.properties")) {
            props.load(input);
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
        pgSimpleDataSource.setUrl(props.getProperty("db.url"));
        pgSimpleDataSource.setUser(props.getProperty("db.username"));
        pgSimpleDataSource.setPassword(props.getProperty("db.password"));
        return pgSimpleDataSource;
    }

    public static DataSource getTestDataSource() {
        PGSimpleDataSource pgSimpleDataSource = new PGSimpleDataSource();
        Properties props = new Properties();
        try (InputStream input = DbConfiguration.class.getClassLoader().getResourceAsStream("test.properties")) {
            props.load(input);
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
        pgSimpleDataSource.setUrl(props.getProperty("db.url"));
        pgSimpleDataSource.setUser(props.getProperty("db.username"));
        pgSimpleDataSource.setPassword(props.getProperty("db.password"));
        return pgSimpleDataSource;
    }
}
