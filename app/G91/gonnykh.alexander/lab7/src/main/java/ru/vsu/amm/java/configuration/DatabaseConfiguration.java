package ru.vsu.amm.java.configuration;

import lombok.extern.slf4j.Slf4j;
import org.postgresql.ds.PGSimpleDataSource;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

@Slf4j
public class DatabaseConfiguration {
    public static DataSource getMainDataSource() {
        return getDataSource(true);
    }

    public static DataSource getTestDataSource() {
        return getDataSource(false);
    }

    public static DataSource getDataSource(boolean testFlag) {
        Properties prop = new Properties();
        try (InputStream input = DatabaseConfiguration.class.getClassLoader()
                .getResourceAsStream(testFlag ? "database.properties" : "testdb.properties")) {
            prop.load(input);
        } catch (IOException e) {
            log.error(e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
        PGSimpleDataSource dataSource = new PGSimpleDataSource();
        dataSource.setUrl(prop.getProperty("db.url"));
        dataSource.setUser(prop.getProperty("db.user"));
        dataSource.setPassword(prop.getProperty("db.password"));
        log.info("DataSource собран!");
        return dataSource;
    }
}
