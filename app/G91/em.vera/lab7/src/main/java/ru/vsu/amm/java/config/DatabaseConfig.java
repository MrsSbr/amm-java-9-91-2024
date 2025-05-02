package ru.vsu.amm.java.config;

import lombok.extern.slf4j.Slf4j;
import org.postgresql.ds.PGSimpleDataSource;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

@Slf4j
public class DatabaseConfig {
    public static DataSource getDataSource() {
        Properties prop = new Properties();
        try (InputStream input = DatabaseConfig.class.getClassLoader()
                .getResourceAsStream("db.properties")) {
            prop.load(input);
        } catch (IOException e) {
            log.error(e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
        PGSimpleDataSource dataSource = new PGSimpleDataSource();
        dataSource.setUrl(prop.getProperty("db.url"));
        dataSource.setUser(prop.getProperty("db.user"));
        dataSource.setPassword(prop.getProperty("db.password"));
        return dataSource;
    }
}
