package ru.vsu.amm.java.configuration;

import lombok.Getter;
import org.postgresql.ds.PGSimpleDataSource;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DatabaseConfiguration {
    private static final Logger logger = Logger.getLogger(DatabaseConfiguration.class.getName());

    @Getter
    private static final DataSource dataSource;

    static {
        PGSimpleDataSource pgSimpleDataSource = new PGSimpleDataSource();
        Properties prop = new Properties();
        try (InputStream input = DatabaseConfiguration.class.getClassLoader().getResourceAsStream("db.properties")) {
            prop.load(input);
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Не удалось загрузить properties для доступа к базе данных", e);
        }
        pgSimpleDataSource.setUrl(prop.getProperty("db.url"));
        pgSimpleDataSource.setUser(prop.getProperty("db.username"));
        pgSimpleDataSource.setPassword(prop.getProperty("db.password"));
        dataSource = pgSimpleDataSource;
    }
}