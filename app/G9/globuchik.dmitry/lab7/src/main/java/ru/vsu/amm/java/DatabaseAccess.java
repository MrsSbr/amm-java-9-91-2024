package ru.vsu.amm.java;

import org.postgresql.ds.PGSimpleDataSource;

import javax.sql.DataSource;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DatabaseAccess {
    private static final Logger LOGGER = Logger.getLogger(DatabaseAccess.class.getName());

    public static DataSource getDataSource() throws IOException {
        LOGGER.log(Level.INFO, "Attempting to get datasource");
        PGSimpleDataSource dataSource = new PGSimpleDataSource();
        Properties properties = new Properties();
        try (InputStream inputStream = DatabaseAccess.class.getResourceAsStream("/db.properties")) {
            properties.load(inputStream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        dataSource.setUrl(properties.getProperty("db.url"));
        dataSource.setUser(properties.getProperty("db.username"));
        dataSource.setPassword(properties.getProperty("db.password"));
        LOGGER.log(Level.INFO, "Successfully get datasource");
        return dataSource;
    }
}
